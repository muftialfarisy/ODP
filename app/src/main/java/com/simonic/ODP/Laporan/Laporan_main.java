package com.simonic.ODP.Laporan;

import android.animation.Animator;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.simonic.ODP.R;
//https://stackoverflow.com/questions/30699302/android-design-support-library-expandable-floating-action-buttonfab-menu
//https://github.com/ajay-dewari/FloatingActionButton-Menu/blob/master/app/src/main/res/layout/activity_main.xml
//https://github.com/ajay-dewari/FloatingActionButton-Menu/blob/master/app/src/main/java/com/ajaysinghdewari/floatingactionbuttonmenu/activities/MainActivity.java
public class Laporan_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FloatingActionButton fab,fab1,fab2,fab3;
    LinearLayout fab1l,fab2l,fab3l;
    View fabBGLayout;
    boolean isFABOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
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
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(0);
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
            case R.id.chekup:
                Toast.makeText(this,"checkup",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
