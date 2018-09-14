package com.example.jsc.myapplication.ui.activity.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.utils.ActivityStackHelper;

import java.util.LinkedList;

public class OneActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        txtView = findViewById(R.id.txt);
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        LinkedList<Activity> stack = ActivityStackHelper.getStack();
        StringBuffer buffer = new StringBuffer("OneActivity\r\nactivity栈顺序:\r\n");
        for (Activity a : stack) {
            buffer.append(a.getClass().getCanonicalName()).append("\r\n");
        }
        txtView.setText(buffer);
    }

    @Override
    public void onClick(View v) {

    }
}
