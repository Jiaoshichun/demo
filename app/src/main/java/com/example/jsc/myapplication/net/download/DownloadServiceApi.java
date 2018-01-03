package com.example.jsc.myapplication.net.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 一些公共的网络请求
 * Created by jsc on 2017/11/28.
 */

interface DownloadServiceApi {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
