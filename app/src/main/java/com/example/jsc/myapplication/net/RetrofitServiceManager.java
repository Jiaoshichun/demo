package com.example.jsc.myapplication.net;

import android.util.Log;

import com.example.jsc.myapplication.common.GlobalConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jsc on 2017/11/27.
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 20;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 20;
    private Retrofit mRetrofit;
    private final HttpCommonInterceptor commonInterceptor;

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor());
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        // 添加公共参数拦截器
        commonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("UA", "Android_ML")
                .build();
        builder.addInterceptor(commonInterceptor);
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient(builder))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalConfig.BASE_URL)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {

        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    private OkHttpClient getOkHttpClient(OkHttpClient.Builder builder) {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message ->
                Log.d("RetrofitServiceManager", "OkHttp====Message:" + message));
        loggingInterceptor.setLevel(level);

        //定制OkHttp

        //OkHttp进行添加拦截器loggingInterceptor
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }
}
