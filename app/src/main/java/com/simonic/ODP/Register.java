package com.simonic.ODP;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {
     FirebaseAuth mAuth;
     FirebaseAnalytics mFirebaseAnalytics;
    TelephonyManager telephonyManager;
    Button register,get_device;
    EditText txt_device,txt_uuid;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        register = findViewById(R.id.btn_register);
        txt_device = findViewById(R.id.txt_device);
        txt_uuid = findViewById(R.id.txt_uuid);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(Register.this);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference getReference;
                getReference = database.getReference();
                String device = txt_device.getText().toString();
                String uuid = txt_uuid.getText().toString();
                getReference.child("Data ODP").child(device)
                        .setValue(new Register_gs(device,uuid))
                        .addOnSuccessListener(Register.this, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                                emptydata();
                                DynamicToast.makeSuccess(Register.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
        get_device = findViewById(R.id.btn_get_device);
        get_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephonyManager  = (TelephonyManager) getSystemService(Context.
                        TELEPHONY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        if (!Register.this.shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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

                txt_device.setText(deviceId);
                if (null != telephonyManager) {
                    deviceId = telephonyManager.getDeviceId();
                    txt_device.setText(deviceId);
                }
                if (null == deviceId || 0 == deviceId.length()) {
                    deviceId = Settings.Secure.getString(Register.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    txt_device.setText(deviceId);
                }
            }
        });
    }
    private void emptydata(){
        txt_uuid.setText("");
        txt_device.setText("");
    }
    /*private void permission(){
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (!this.shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        String deviceId = telephonyManager.getDeviceId();
    }*/
}
