package com.example.jsc.myapplication.ui.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jsc.myapplication.R;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 1.确保电脑和手机在一个网段
 * 2.电脑通过浏览器 输入手机ip:3213 可访问手机内的视频文件
 * 3.浏览器点击视频可以进行播放
 */
public class WebServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_server);

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

// listen on port 5000
        server.listen(3213);
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
