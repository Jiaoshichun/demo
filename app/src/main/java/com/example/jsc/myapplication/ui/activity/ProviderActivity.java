package com.example.jsc.myapplication.ui.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriPermission;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.jsc.myapplication.R;

import java.util.List;

public class ProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtShow;
    private ContentResolver contentResolver;
    // 设置URI
    private Uri uri_user = Uri.parse("content://com.jsc.remote/name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        txtShow = (TextView) findViewById(R.id.show);
        contentResolver = getContentResolver();


        int width = 400, height = 400, left = 100, top = 100;
        FrameLayout frameLayout = findViewById(R.id.fLayout);


        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;
        RelativeLayout relativeLayout = new RelativeLayout(this);
        frameLayout.addView(relativeLayout, layoutParams);

        VideoView videoView = new VideoView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        relativeLayout.addView(videoView, params);
        videoView.setVideoPath("http://testqiniu.91jikang.com/Fi8FBAgSanp3v4pfx_7-TlN6F1l9?e=1515504055&token=6KLD8RkZ2GILsEWBwwDQEVF2-dPHwjiyMqJTZDWU:QOmyuwuo8gJsHqefkLFnoRF0GGk=");
//        videoView.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                try {

                    ContentValues values = new ContentValues();
                    values.put("name", "张三");

                    Uri insert = contentResolver.insert(uri_user, values);

                    if (insert != null)
                        Toast.makeText(this, ContentUris.parseId(insert) + "", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.query:
                Cursor query = null;
                try {
                    query = contentResolver.query(uri_user, null, null, null, null);
                    StringBuilder stringBuffer = new StringBuilder();
                    if (query != null) {
                        while (query.moveToNext()) {
                            stringBuffer.append(query.getInt(0)).append(":").append(query.getString(1));
                        }
                        txtShow.setText(stringBuffer.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (query != null)
                        query.close();
                }

                break;
        }
    }
}
