package com.example.jsc.myapplication.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.jsc.myapplication.BaseActivity;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.ui.fragment.CustomCoordinatorFragment;
import com.facebook.stetho.common.LogUtil;

public class CoordinatorActivity extends BaseActivity implements View.OnClickListener {

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
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        StringBuffer stringBuffer = new StringBuffer();
        getAllView(decorView,stringBuffer);
        LogUtil.e("------=========",stringBuffer);
    }
    public void getAllView(ViewGroup viewGroup,StringBuffer stringBuffer){
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View at = viewGroup.getChildAt(i);
            stringBuffer.append("parent:").append(viewGroup)
                    .append(at.getClass().getSimpleName())
                    .append("->")
                    .append(at.getClass().getSimpleName())
                    .append("\r\n");
            if(at instanceof  ViewGroup){
                getAllView((ViewGroup) at,stringBuffer);
            }
        }
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
