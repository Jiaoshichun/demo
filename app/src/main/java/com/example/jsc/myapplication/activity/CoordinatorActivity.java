package com.example.jsc.myapplication.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.fragment.CustomCoordinatorFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CoordinatorActivity extends AppCompatActivity implements View.OnClickListener {

    private View fLayout;
    private CustomCoordinatorFragment customCoordinatorFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        fLayout = findViewById(R.id.flayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.string.open,  /* "open drawer" description for accessibility */
                R.string.close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {

            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                switchFragment(1);
                drawerLayout.closeDrawers();
                break;
            case R.id.btn2:
                switchFragment(2);
                drawerLayout.closeDrawers();
                break;
        }

    }

    private void switchFragment(int i) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (i) {
            case 1:
                if (customCoordinatorFragment == null)
                    customCoordinatorFragment = new CustomCoordinatorFragment();
                fragmentTransaction.replace(R.id.flayout, customCoordinatorFragment);
                break;
            case 2:
                break;
        }

        fragmentTransaction.commit();
    }

}
