package com.example.jsc.myapplication.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.stetho.common.LogUtil;

public class ActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {
    public final static String TAG="ActivityLifecycleManager";
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.i(TAG,"onActivityCreated,activity:"+activity.getClass().getCanonicalName());
        ActivityStackHelper.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.i(TAG,"onActivityStarted,activity:"+activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.i(TAG,"onActivityResumed,activity:"+activity.getClass().getCanonicalName());
        ActivityStackHelper.remove(activity);
        ActivityStackHelper.add(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.i(TAG,"onActivityPaused,activity:"+activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.i(TAG,"onActivityStopped,activity:"+activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LogUtil.i(TAG,"onActivitySaveInstanceState,activity:"+activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.i(TAG,"onActivityDestroyed,activity:"+activity.getClass().getCanonicalName());
        ActivityStackHelper.pop(activity);
    }
}
