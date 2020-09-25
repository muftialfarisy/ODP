package com.simonic.ODP.Laporan.Checkup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.simonic.ODP.Laporan.Laporan_main;
import com.simonic.ODP.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Input_checkup extends AppCompatActivity {
    TextView tgl,deviceidd;
    private ArrayList<Lcheckup_gs> reportlist2 = new ArrayList<>();
    EditText txtrs,txthaemoglobin,txtleukosit,txttrombosit,txtelektrolit,txtpuasa,txtsetelah,txtkolesterol,txtasamurat,txthati,txtginjal;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseAnalytics mFirebaseAnalytics;
    TelephonyManager telephonyManager;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_checkup);
        tgl = findViewById(R.id.txt_tgl);
        txtrs =findViewById(R.id.txt_rs);
        deviceidd = findViewById(R.id.device_idd);

        txthaemoglobin =findViewById(R.id.txt_hae);
        txtleukosit =findViewById(R.id.txt_leukosit);
        txttrombosit =findViewById(R.id.txt_trombosit);
        txtelektrolit =findViewById(R.id.txt_elektrolit);
        txtpuasa =findViewById(R.id.txt_kadar_puasa);
        txtsetelah =findViewById(R.id.txt_kadar_setelah);
        txtkolesterol =findViewById(R.id.txt_kolesterol);
        txtasamurat =findViewById(R.id.txt_asam);
        txtasamurat =findViewById(R.id.txt_asam);
        txthati =findViewById(R.id.txt_hati);
        txtginjal = findViewById(R.id.txt_ginjal);
        submit = findViewById(R.id.btn_submit_ck);
        getdevice();
        final String device = getIntent().getStringExtra("device");
        final Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int bul = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Input_checkup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
                        String sDate = dayofmonth + "," + month + "," + year;
                        tgl.setText(sDate);

                    }
                }, tahun, bulan, hari
                );
                datePickerDialog.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(Input_checkup.this);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final String rs = txtrs.getText().toString();
                final String haemoglobin = txthaemoglobin.getText().toString();
                final String leukosit = txtleukosit.getText().toString();
                final String trombosit = txttrombosit.getText().toString();
                final String elektrolit = txtelektrolit.getText().toString();
                final String kadar_puasa = txtpuasa.getText().toString();
                final String kadar_setelah = txtsetelah.getText().toString();
                final String kolesterol = txtkolesterol.getText().toString();
                final String asam_urat = txtasamurat.getText().toString();
                final String hati = txthati.getText().toString();
                final String ginjal = txtginjal.getText().toString();
                final String tgl2 = tgl.getText().toString();
                String idd = deviceidd.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReferenceFromUrl("https://simonicv2.firebaseio.com/Data ODP");
                DatabaseReference quizRef = ref.child("Data ODP").child(idd).child("laporan_checkup");
                String key = quizRef.push().getKey();
                //ref.child(device).child("namae").setValue("alfa");
                ref.child(idd).child("laporan_checkup").child(key).setValue(new Checkup_gs(rs,tgl2,haemoglobin,leukosit,trombosit,elektrolit,kadar_puasa,kadar_setelah,kolesterol,asam_urat,hati,
                                ginjal))
                        .addOnSuccessListener(Input_checkup.this, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                                reportlist2.add(new Lcheckup_gs(txtrs.getText().toString(),tgl.getText().toString(),haemoglobin,leukosit,trombosit,elektrolit,kadar_puasa,kadar_setelah,kolesterol,asam_urat,hati,
                                        ginjal));
                                //emptydata();
                                DynamicToast.makeSuccess(Input_checkup.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }
    public void getdevice(){
        telephonyManager  = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Input_checkup.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (!Input_checkup.this.shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Input_checkup.this);
                    builder.setMessage("Please grant Phoone access so this app can detect Device ID.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @TargetApi(23)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                                    PERMISSION_REQUEST_BACKGROUND_LOCATION);
                        }

                    });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Input_checkup.this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }


        }
        @SuppressLint("HardwareIds") String deviceId = telephonyManager.getDeviceId();

        deviceidd.setText(deviceId);
        if (null != telephonyManager) {
            deviceId = telephonyManager.getDeviceId();
            deviceidd.setText(deviceId);

        }
        if (null == deviceId || 0 == deviceId.length()) {
            deviceId = Settings.Secure.getString(Input_checkup.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            deviceidd.setText(deviceId);
        }
    }

}
