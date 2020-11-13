package com.simonic.ODP.Laporan.Masalah;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simonic.ODP.Laporan.Checkup.CheckupAdapter;
import com.simonic.ODP.Laporan.Checkup.Checkup_gs;
import com.simonic.ODP.Laporan.Checkup.Input_checkup;
import com.simonic.ODP.Laporan.Checkup.Lcheckup_gs;

import com.simonic.ODP.Laporan.Laporan_main;
import com.simonic.ODP.Laporan.Suhu.Input_suhu;
import com.simonic.ODP.R;

import java.util.ArrayList;

public class Laporan_masalah extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    MasalahAdapter adapter;
    NavigationView navigationView;
    FloatingActionButton fab,fab1,fab2,fab3;
    LinearLayout fab1l,fab2l,fab3l;
    View fabBGLayout;
    TextView id,random;
    String GetUserID;
    Masalah_gs mg;
    int i;
    boolean isFABOpen = false;
    private ArrayList<Masalah_gs> masalahlist = new ArrayList<>();
    private ShimmerRecyclerView Rv_reportm;
    private DatabaseReference reference;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth auth;
    TelephonyManager telephonyManager;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    ArrayList<String> categories = new ArrayList<>();
    long mid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_masalah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        Rv_reportm = findViewById(R.id.rv_reportm);
        id = findViewById(R.id.deviceid);
        auth = FirebaseAuth.getInstance();
        Rv_reportm.setHasFixedSize(true);
        //CheckupAdapter adapter = new CheckupAdapter(Laporan_masalah.this, reportlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Laporan_masalah.this);
        Rv_reportm.setLayoutManager(layoutManager);
        Rv_reportm.showShimmerAdapter();
        random = findViewById(R.id.test);
        mg = new Masalah_gs();
        getdevice();
        fab1l = findViewById(R.id.ln_fab1);
        fab2l = findViewById(R.id.ln_fab2);
        fab3l = findViewById(R.id.ln_fab3);
        fab = findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fabBGLayout = findViewById(R.id.fabBGLayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Laporan_masalah.this, Input_masalah.class);
                startActivity(intent);
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Laporan_masalah.this, Input_checkup.class);
                startActivity(intent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Laporan_masalah.this, Input_suhu.class);
                startActivity(intent);
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(0);

        getdata();
    }
    private void showFABMenu(){
        isFABOpen=true;
        fab1l.setVisibility(View.VISIBLE);
        fab2l.setVisibility(View.VISIBLE);
        fab3l.setVisibility(View.VISIBLE);
        //fabBGLayout.setVisibility(View.VISIBLE);
        fab1l.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2l.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3l.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);

        fab1l.animate().translationY(0);
        fab2l.animate().translationY(0);
        fab3l.animate().translationY(0);
        fab3l.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fab1l.setVisibility(View.GONE);
                    fab2l.setVisibility(View.GONE);
                    fab3l.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.chekup2:
                Intent intent = new Intent(Laporan_masalah.this, Laporan_main.class);
                Toast.makeText(this,"checkup",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;

            case R.id.masalah2:
                Toast.makeText(this,"masalah",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
private void getdata(){
    String idd = id.getText().toString();


    if (i < 0){
        showDialog();
    }else{
        for (i=0;i<50;i++) {
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Data ODP").child(idd).child("laporan_masalah").child(String.valueOf(i));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        mid = mid + 1;

                    } else {

                        mid = (snapshot.getChildrenCount());
                        Masalah_gs individu = snapshot.getValue(Masalah_gs.class);
                        masalahlist.add(individu);

                        adapter = new MasalahAdapter(Laporan_masalah.this, masalahlist);


                        Rv_reportm.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}
    public void getdevice(){
        telephonyManager  = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Laporan_masalah.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (!Laporan_masalah.this.shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Laporan_masalah.this);
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
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Laporan_masalah.this);
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

        id.setText(deviceId);
        if (null != telephonyManager) {
            deviceId = telephonyManager.getDeviceId();
            id.setText(deviceId);

        }
        if (null == deviceId || 0 == deviceId.length()) {
            deviceId = Settings.Secure.getString(Laporan_masalah.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            id.setText(deviceId);
        }
    }
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Laporan Kosong!");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah Anda Ingin Memasukkan Laporan?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Intent intent = new Intent(Laporan_masalah.this, Input_checkup.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        Laporan_masalah.this.finish();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

}


