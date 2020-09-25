package com.simonic.ODP.Laporan.Masalah;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TimePicker;
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
import com.simonic.ODP.R;

import java.util.Calendar;

public class Input_masalah extends AppCompatActivity {
    TextView tgl,jam,deviceidd;
    EditText masalah;
    Button submit;
    TelephonyManager telephonyManager;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    FirebaseAuth mAuth;
    FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_masalah);
        tgl = findViewById(R.id.txt_tgl_masalah);
        jam = findViewById(R.id.txt_jam_masalah);
        submit = findViewById(R.id.btn_submit_masalah);
        deviceidd = findViewById(R.id.device_idd);
        masalah = findViewById(R.id.txt_masalah);
        final Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);
        getdevice();
        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Input_masalah.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
                        String sDate = dayofmonth + "/" + month + "/" + year;
                        tgl.setText(sDate);
                    }
                }, tahun, bulan, hari
                );
                datePickerDialog.show();
            }
        });
        jam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Input_masalah.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jam.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(Input_masalah.this);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String masalah2 = masalah.getText().toString();
                String tgl2 = tgl.getText().toString();
                String jam2 = jam.getText().toString();
                String idd = deviceidd.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReferenceFromUrl("https://simonicv2.firebaseio.com/Data ODP");
                //ref.child(device).child("namae").setValue("alfa");
                ref.child(idd).child("laporan_masalah").push()
                        .setValue(new Masalah_gs(masalah2,tgl2,jam2))
                        .addOnSuccessListener(Input_masalah.this, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                                //emptydata();
                                DynamicToast.makeSuccess(Input_masalah.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }
    public void getdevice(){
        telephonyManager  = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Input_masalah.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (!Input_masalah.this.shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Input_masalah.this);
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
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Input_masalah.this);
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
            deviceId = Settings.Secure.getString(Input_masalah.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            deviceidd.setText(deviceId);
        }
    }
}
