package com.example.jsc.myapplication.net.download;

import android.support.annotation.NonNull;
import android.util.Log;


import com.example.jsc.myapplication.common.FileUtil;
import com.example.jsc.myapplication.common.GlobalConfig;
import com.example.jsc.myapplication.net.Fault;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 文件下载工具类
 * Created by jsc on 2017/11/28.
 */

public class DownloadAPI {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;

    /**
     * 下载文件
     *
     * @param url
     * @param file
     * @param loadListener
     */
    public static void download(String url, File file, DownLoadListener loadListener) {
        DownloadExecutor downloadExecutor = new DownloadExecutor((bytesRead, contentLength, done) -> {
            if (loadListener != null) {
                if (contentLength != 0) {
                    loadListener.onProgress((int) (((float) bytesRead * 100) / contentLength));
                }
            }
        });
        downloadExecutor.executeDownload(url, file, new Subscriber() {
            @Override
            public void onCompleted() {
                if (loadListener != null) loadListener.onCompleted(file);
            }

            @Override
            public void onError(Throwable e) {
                if (loadListener != null) loadListener.onError(e.getMessage());
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    static class DownloadExecutor {
        private Retrofit retrofit;

        private DownloadExecutor(DownloadProgressListener listener) {

            DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();


            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(GlobalConfig.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        void executeDownload(@NonNull String url, final File file, Subscriber subscriber) {
            Log.d(TAG, "download: " + url);

            retrofit.create(DownloadServiceApi.class)
                    .download(url)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .map(ResponseBody::byteStream)
                    .observeOn(Schedulers.computation())
                    .doOnNext(inputStream -> {
                        try {
                            FileUtil.writeFile(inputStream, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new Fault(-1, e.getMessage());
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

}
