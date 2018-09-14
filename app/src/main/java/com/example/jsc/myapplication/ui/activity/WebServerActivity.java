package com.example.jsc.myapplication.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jsc.myapplication.BaseActivity;
import com.example.jsc.myapplication.R;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 1.确保电脑和手机在一个网段
 * 2.电脑通过浏览器 输入手机ip:3213 可访问手机内的视频文件
 * 3.浏览器点击视频可以进行播放
 */
public class WebServerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_server);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        TextView txt = findViewById(R.id.txt);
        txt.setText(String.format("1.确保电脑和手机在一个网段\n 2.电脑通过浏览器 输入手机%s:3213 可访问手机内的视频文件\n3.浏览器点击视频可以进行播放", ip));
        server();
    }

    private void server() {
        AsyncHttpServer server = new AsyncHttpServer();
        server.get("/", (request, response) -> {
            InputStream open = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                open = getAssets().open("index.html");
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[4096];
                int len;
                while ((len = open.read(bytes)) > 0) {
                    byteArrayOutputStream.write(bytes, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (open != null) {
                    try {
                        open.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (byteArrayOutputStream != null) {
                    try {
                        response.send(byteArrayOutputStream.toString());
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        server.get("/jquery-3.2.1.js", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                try {
                    String fullPath = request.getPath();
                    fullPath = fullPath.replace("%20", " ");
                    String resourceName = fullPath;
                    if (resourceName.startsWith("/")) {
                        resourceName = resourceName.substring(1);
                    }
                    if (resourceName.indexOf("?") > 0) {
                        resourceName = resourceName.substring(0, resourceName.indexOf("?"));
                    }
                    response.setContentType("application/javascript");
                    BufferedInputStream bInputStream = new BufferedInputStream(getAssets().open(resourceName));
                    response.sendStream(bInputStream, bInputStream.available());
                } catch (IOException e) {
                    e.printStackTrace();
                    response.code(404).end();
                    return;
                }
            }
        });
        server.get("/file", (request, response) -> {
            fileList.clear();
            File dir = new File(Environment.getExternalStorageDirectory().getPath());
            findMp4(dir);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", file.getName());
                    jsonObject.put("path", file.getAbsolutePath());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            response.send(jsonArray.toString());
        });

        server.get("/file/.*", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                String path = request.getPath().replace("/file/", "");
                try {
                    path = URLDecoder.decode(path, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                File file = new File(path);
                if (file.exists() && file.isFile()) {
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        response.sendStream(fis, fis.available());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                response.code(404).send("Not found!");
            }
        });
// listen on port 5000
        server.listen(3213);
    }

    private ArrayList<File> fileList = new ArrayList<>();

    private void findMp4(File dir) {
        String[] fileNames = dir.list();
        if (fileNames != null) {
            for (String fileName : fileNames) {
                File file = new File(dir, fileName);
                if (file.exists() && file.isFile() && file.getName().endsWith(".mp4")) {
                    fileList.add(file);
                } else if (file.isDirectory() && file.exists()) {
                    findMp4(file);
                }
            }
        }
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
