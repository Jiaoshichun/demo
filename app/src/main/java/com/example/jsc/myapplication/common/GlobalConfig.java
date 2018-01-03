package com.example.jsc.myapplication.common;


import com.example.jsc.myapplication.BuildConfig;

/**
 * Created by jsc on 2017/11/27.
 */

public interface GlobalConfig {

    String BASE_URL_DEBUG = "http://gxgscyeizsl0cf34.apitest.share-times.com/";
    String BASE_URL_RELEASE = "https://api.share-times.com/";
    String BASE_URL = BuildConfig.ISDEBUG ? BASE_URL_DEBUG : BASE_URL_RELEASE;
    //http://gxgscyeizsl0cf34.apitest.share-times.com
    //下载文件地址
    String download_url = BASE_URL + "auth/api/v1/downttf";
    String TAG = "miliao";
    int LOG_LEVEL = 0;
    int IM_SDK_APP_ID_RELEASE = 1400027619;
    int IM_SDK_APP_ID_DEBUG = 1400021190;
    int IM_SDK_APP_ID = BuildConfig.DEBUG ? IM_SDK_APP_ID_DEBUG : IM_SDK_APP_ID_RELEASE;//腾讯云appid

    //测试wed
    String http_wed_debug = "http://gxgscyeizsl0cf34.miliao.share-times.com/";
    //正式wed
    String http_wed_release = "https://miliao.share-times.com/";
    String http_wed = BuildConfig.ISDEBUG ? http_wed_debug : http_wed_release;

    String code = "";//微信登录,生成


}
