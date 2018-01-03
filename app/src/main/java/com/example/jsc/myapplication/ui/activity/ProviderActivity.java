package com.example.jsc.myapplication.ui.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsc.myapplication.R;

public class ProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtShow;
    private ContentResolver contentResolver;
    // 设置URI
    private Uri uri_user = Uri.parse("content://com.jsc.myprovider/user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        txtShow = (TextView) findViewById(R.id.show);
        contentResolver = getContentResolver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                ContentValues values = new ContentValues();
                values.put("name", "张三");
                Uri insert = contentResolver.insert(uri_user, values);
                if (insert != null)
                    Toast.makeText(this, ContentUris.parseId(insert)+"", Toast.LENGTH_SHORT).show();
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
                } finally {
                    if (query != null)
                        query.close();
                }

                break;
        }
    }
}
