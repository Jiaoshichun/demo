package com.example.jsc.myapplication.ui.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.utils.ActivityStackHelper;

import java.util.LinkedList;

public class TwoActivity extends AppCompatActivity {

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        txtView = findViewById(R.id.txt);
        LinkedList<Activity> stack = ActivityStackHelper.getStack();
        StringBuffer buffer = new StringBuffer("TwoActivity\r\nactivity栈顺序:\r\n");
        for (Activity a : stack) {
            buffer.append(a.getClass().getCanonicalName()).append("\r\n");
        }
        txtView.setText(buffer);
    }
}
