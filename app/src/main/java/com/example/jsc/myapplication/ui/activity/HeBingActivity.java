package com.example.jsc.myapplication.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.ui.adapter.HeBingAdapter;
import com.example.jsc.myapplication.utils.CaoZuoMp3Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class HeBingActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private HeBingAdapter heBingAdapter;
    private final String TAG = "HeBingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_he_bing);
        listView = (ListView) findViewById(R.id.list);
        findViewById(R.id.btn).setOnClickListener(this);

        setData();

    }

    private void setData() {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Cursor cursor = getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.YEAR,
                            MediaStore.Audio.Media.MIME_TYPE,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DATA},
                    null,
                    null, null);
//            Cursor cursor = getContentResolver().query(
//                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                    new String[]{MediaStore.Audio.Media._ID,
//                            MediaStore.Audio.Media.DISPLAY_NAME,
//                            MediaStore.Audio.Media.TITLE,
//                            MediaStore.Audio.Media.DURATION,
//                            MediaStore.Audio.Media.ARTIST,
//                            MediaStore.Audio.Media.ALBUM,
//                            MediaStore.Audio.Media.YEAR,
//                            MediaStore.Audio.Media.MIME_TYPE,
//                            MediaStore.Audio.Media.SIZE,
//                            MediaStore.Audio.Media.DATA},
//                    MediaStore.Audio.Media.MIME_TYPE + "=? or "
//                            + MediaStore.Audio.Media.MIME_TYPE + "=?",
//                    new String[]{"audio/mpeg", "audio/x-ms-wma"}, null);
            heBingAdapter = new HeBingAdapter(this, cursor, true);
            listView.setAdapter(heBingAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ArrayList<Integer> checkedIds = heBingAdapter.getCheckedIds();
                    String s = "";
                    ArrayList<String> strings = new ArrayList<>();
                    for (int i = 0; i < checkedIds.size(); i++) {
                        s += checkedIds.get(i) + "--";
                        Cursor cursor = heBingAdapter.getCursor();
                        if (cursor.moveToPosition(checkedIds.get(i))) {
                            strings.add(cursor.getString(9));
                        }
                    }
                    new Thread(() -> {
                        try {
                            String[] strings1 = new String[strings.size()];
                            CaoZuoMp3Utils.heBingMp3("11", strings.toArray(strings1));
                            Log.e(TAG, "合并完成");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ).start();
                    Log.e("lllll", s);
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isRefuse = false;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

            } else {
                isRefuse = true;

            }
        }
        if (isRefuse) {

        } else {
            setData();
        }

    }


    public boolean hasPermission(String... permissionName) {
        HashSet<String> permissionSet = new HashSet<>();
        if (permissionName == null) return true;
        for (int i = 0; i < permissionName.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissionName[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionSet.add(permissionName[i]);
            }
        }
        if (permissionSet.size() == 0) return true;
        else {
            ActivityCompat.requestPermissions(this,
                    permissionSet.toArray(new String[permissionSet.size()]),
                    1);
            return false;
        }

    }
}
