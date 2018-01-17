package com.example.jsc.myapplication.common;

import android.content.Context;
import android.util.Log;

import com.example.jsc.myapplication.BuildConfig;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import java.util.Properties;

/**
 * 用于埋点统计的工具类
 * Created by jsc on 2018/1/15.
 */

public class TrackEventUtils {
    public static final String EVENT_ID = "event_id";



    /**
     * 应用启动界面 需要进行初始化
     *
     * @param context 上下文对象
     */
    public static void init(Context context) {
         /*
          * 启动界面 腾讯统计*Android   APP ID: 3103284158   App Key: A347YFSBP9NK************************
         *  http://docs.developer.qq.com/mta/fast_access/android.html  文档地址
         */
//        StatConfig.setDebugEnable(!BuildConfig.ISDEBUG);
        try {
            StatService.startStatService(context, null, com.tencent.stat.common.StatConstants.VERSION);
            Log.e("MTA", "MTA初始化成功");
        } catch (MtaSDkException e) {
            e.printStackTrace();
            Log.e("MTA", "MTA初始化失败" + e.toString());
        }
    }

    /**
     * 埋点统计上报
     *
     * @param eventId    事件id
     * @param properties 上报参数 key -value形式
     */
    public static void trackCustomKVEvent(String eventId, Properties properties) {
        StatService.trackCustomKVEvent(UIUtils.getContext(), eventId, properties);
    }

    /**
     * 埋点统计上报
     *
     * @param eventId 事件id
     * @param content 上报参数 任意参数
     */
    public static void trackCustomEvent(String eventId, String... content) {
        StatService.trackCustomEvent(UIUtils.getContext(), eventId, content);
    }

    /**
     * 埋点统计上报  统计时长事件 开始的事件
     *
     * @param eventId    事件id
     * @param properties 上报参数 key -value形式
     */
    public static void trackCustomBeginKVEvent(String eventId, Properties properties) {
        StatService.trackCustomBeginKVEvent(UIUtils.getContext(), eventId, properties);
    }

    /**
     * 埋点统计上报 统计时长事件 结束的事件
     *
     * @param eventId    事件id
     * @param properties 上报参数 key -value形式
     */
    public static void trackCustomEndKVEvent(String eventId, Properties properties) {
        StatService.trackCustomEndKVEvent(UIUtils.getContext(), eventId, properties);
    }

    /**
     * 埋点统计上报  统计时长事件 开始的事件
     *
     * @param eventId 事件id
     * @param content 上报参数 任意参数
     */
    public static void trackCustomBeginEvent(String eventId, String... content) {
        StatService.trackCustomBeginEvent(UIUtils.getContext(), eventId, content);
    }

    /**
     * 埋点统计上报 统计时长事件 结束的事件
     *
     * @param eventId 事件id
     * @param content 上报参数 任意参数
     */
    public static void trackCustomEndEvent(String eventId, String... content) {
        StatService.trackCustomEndEvent(UIUtils.getContext(), eventId, content);
    }

}
