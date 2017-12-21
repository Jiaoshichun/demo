package com.example.jsc.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jsc.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);
        findViewById(R.id.btn13).setOnClickListener(this);
        findViewById(R.id.btn14).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, TransitionActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, RxjavaActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.btn4:
//                startActivity(new Intent(this, KotlinActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this, ImageScaleActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(this, RecyclerActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(this, AddressBookActivity.class));
                break;
            case R.id.btn8:
                startActivity(new Intent(this, CoordinatorActivity.class));
                break;
            case R.id.btn9:
                startActivity(new Intent(this, EditTextActivity.class));
                break;
            case R.id.btn10:
                startActivity(new Intent(this, CircleViewActivity.class));
                break;
            case R.id.btn11:
                startActivity(new Intent(this, HeBingActivity.class));
                break;
            case R.id.btn12:
                startActivity(new Intent(this, ProviderActivity.class));
                break;
            case R.id.btn13:
                startActivity(new Intent(this, MessengerActivity.class));
                break;
            case R.id.btn14:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
        }
    }
}
