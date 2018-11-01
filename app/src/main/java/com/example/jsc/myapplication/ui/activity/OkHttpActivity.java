package com.example.jsc.myapplication.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.common.FileUtil;
import com.example.jsc.myapplication.mvp.MvpActivity;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

public class OkHttpActivity extends MvpActivity implements View.OnClickListener {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String cacheFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_put).setOnClickListener(this);
        findViewById(R.id.btn_upload).setOnClickListener(this);
        findViewById(R.id.btn_download).setOnClickListener(this);
        try {
            InputStream open = getAssets().open("icon.png");
            cacheFilePath = FileUtil.getCacheFilePath("icon.png");
            FileUtil.copyFile(open, new FileOutputStream(cacheFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                doGet();
                break;
            case R.id.btn_post:
                doPost();
                break;
            case R.id.btn_put:
                doPut();
                break;
            case R.id.btn_upload:
                doUpload();
                break;
            case R.id.btn_download:
                download();
                break;
        }
    }

    private void download() {
        new Thread() {
            @Override
            public void run() {

                try {
                    SSLContext sslContext = SSLContext.getInstance("SSL");

                    X509TrustManager x509TrustManager = new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    };
                    sslContext.init(null, new X509TrustManager[]{x509TrustManager}, new SecureRandom());
                    OkHttpClient client = new OkHttpClient.Builder()
                            .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Response originalResponse = chain.proceed(chain.request());
                                    return originalResponse.newBuilder()
                                            .body(new ResponseBody() {

                                                private BufferedSource bufferedSource;

                                                @Nullable
                                                @Override
                                                public MediaType contentType() {
                                                    if (originalResponse.body() != null) {
                                                        return originalResponse.body().contentType();
                                                    }
                                                    return null;
                                                }

                                                @Override
                                                public long contentLength() {
                                                    return originalResponse.body() != null ? originalResponse.body().contentLength() : 0;
                                                }

                                                @Override
                                                public BufferedSource source() {
                                                    if (bufferedSource == null) {
                                                        bufferedSource = Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
                                                            long totalBytesRead = 0L;

                                                            @Override
                                                            public long read(Buffer sink, long byteCount) throws IOException {
                                                                long bytesRead = super.read(sink, byteCount);
                                                                // read() returns the number of bytes read, or -1 if this source is exhausted.
                                                                totalBytesRead += bytesRead != -1 ? bytesRead : 0;//当前已读取的内容
                                                                long l = contentLength();//总长度
                                                                Log.e(TAG, "当前已读取的长度:" + totalBytesRead + "--总长度:" + l + "---百分比:" + (totalBytesRead * 1.0 / l));
                                                                return bytesRead;
                                                            }
                                                        });
                                                    }
                                                    return bufferedSource;
                                                }
                                            })
                                            .build();
                                }
                            })
                            .hostnameVerifier((hostname, session) -> true)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://app.int.jumei.com:8180/7500/20181023/7.5_default_7500_10_23_17_30.apk")
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    InputStream inputStream = response.body().byteStream();
                    String filePath = FileUtil.getCacheFilePath("下载的文件");
                    FileUtil.copyFile(inputStream, new FileOutputStream(filePath));
                    Log.e(TAG, "下载文件的路径为:" + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void doUpload() {
        new Thread() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient.Builder().build();

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*; charset=utf-8"), new File(cacheFilePath));
                    Request request = new Request.Builder()
                            .url("http://localhost:8080/Test")
                            .post(requestBody)
                            .build();
                    Response execute = client.newCall(request).execute();
                    Log.e(TAG, "doUpload:" + execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void doPut() {
        new Thread() {
            @Override
            public void run() {

                try {
                    UserDetailBean userDetailBean = new UserDetailBean();
                    userDetailBean.uid = "111";
                    userDetailBean.answerPrice = "answerPrice";

                    OkHttpClient client = new OkHttpClient.Builder().build();
                    RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(userDetailBean));
                    Request request = new Request.Builder()
                            .url("http://localhost:8080/Test")
                            .put(requestBody)
                            .build();
                    Response execute = client.newCall(request).execute();
                    Log.e(TAG, "doPut:" + execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void doPost() {
        new Thread() {
            @Override
            public void run() {

                try {
                    UserDetailBean userDetailBean = new UserDetailBean();
                    userDetailBean.uid = "111";
                    userDetailBean.answerPrice = "answerPrice";

                    OkHttpClient client = new OkHttpClient.Builder().build();
                    RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(userDetailBean));
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("http://localhost:8080/Test")
                            .build();
                    Response execute = client.newCall(request).execute();
                    Log.e(TAG, "doPost:" + execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void doGet() {
        new Thread() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient.Builder().build();
                    Request request = new Request.Builder()
                            .url("http://localhost:8080/Test")
                            .get()
                            .build();
                    Response execute = client.newCall(request).execute();
                    Log.e(TAG, "doGet:" + execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

}
