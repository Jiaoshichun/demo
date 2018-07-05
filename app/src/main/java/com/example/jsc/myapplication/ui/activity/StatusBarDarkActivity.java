package com.example.jsc.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.utils.StatusBarImmersiveUtils;

public class StatusBarDarkActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_dark);
        StatusBarImmersiveUtils.open(this);
    }

    public void onDark(View view) {
        StatusBarImmersiveUtils.setDarkIcon(this,Color.parseColor("#77444444"));
    }

    public void onLight(View view) {
        StatusBarImmersiveUtils.setLightIcon(this);

    }
}
