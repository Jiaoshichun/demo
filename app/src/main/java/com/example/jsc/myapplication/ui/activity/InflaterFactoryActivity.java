package com.example.jsc.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.utils.InflaterFactoryManager;

public class InflaterFactoryActivity extends AppCompatActivity {
    private final static String TAG = "InflaterFactoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InflaterFactoryManager factoryManager = InflaterFactoryManager.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflater_factory);
        findViewById(R.id.txt_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                factoryManager.setBackgroundColor(Color.RED);
            }
        });
        findViewById(R.id.btn_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                factoryManager.setBackgroundColor(Color.BLUE);
            }
        });
    }
}
