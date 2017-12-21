package com.example.jsc.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.view.CircleView;

public class CircleViewActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleView circleView;
    private int mProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        findViewById(R.id.txt).setOnClickListener(this);
        circleView = (CircleView) findViewById(R.id.circle_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt:
                mProgress = mProgress + 10;
                circleView.setProgress(mProgress);
                break;

        }
    }
}
