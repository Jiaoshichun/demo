package com.example.jsc.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.example.jsc.myapplication.ui.activity.MessengerActivity;
import com.example.jsc.myapplication.ui.activity.ViewPagerActivity;
import com.example.jsc.myapplication.utils.ActivityLifecycleManager;
import com.facebook.stetho.Stetho;
import com.tencent.mta.track.StatisticsDataAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jsc on 2017/6/14.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Handler handler;
    private static int mTid;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new UEHandler(this));
        //开启SteTho调试  可以通过谷歌浏览器输入 chrome://inspect   进行调试
        Stetho.initializeWithDefaults(this);
        handler = new Handler();
        mTid = Process.myTid();
        context = getApplicationContext();
        StatisticsDataAPI.instance(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleManager());

    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getTid() {
        return mTid;
    }

    private class UEHandler implements Thread.UncaughtExceptionHandler {
        private MyApplication softApp;

        UEHandler(MyApplication app) {
            softApp = app;
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {

            String info = this.toErrorLog(softApp, ex);

            write2ErrorLog(info);
            Log.e(TAG, info);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        private String toErrorLog(MyApplication softApp, Throwable ex) {
            String info = null;
            ByteArrayOutputStream baos = null;
            PrintStream printStream = null;
            try {
                baos = new ByteArrayOutputStream();
                printStream = new PrintStream(baos);
                ex.printStackTrace(printStream);
                byte[] data = baos.toByteArray();
                info = new String(data);
                PackageManager manager = softApp.getPackageManager();
                PackageInfo pinfo = manager.getPackageInfo(softApp.getPackageName(), 0);
                info += "" + (char) (13) + (char) (10) + "Version:" + pinfo.versionName;
                info += "" + (char) (13) + (char) (10) + "VersionCode:" + pinfo.versionCode;
                info += "" + (char) (13) + (char) (10) + "Android:" + android.os.Build.MODEL + ","
                        + android.os.Build.VERSION.RELEASE;
                info += "" + (char) (13) + (char) (10) + "Hardware:"
                        + android.os.Build.BOARD + ","
                        + android.os.Build.BRAND + ","
                        + android.os.Build.CPU_ABI + ","
                        + android.os.Build.DEVICE + ","
                        + android.os.Build.DISPLAY + ","
                        + android.os.Build.FINGERPRINT + ","
                        + android.os.Build.HOST + ","
                        + android.os.Build.ID + ","
                        + android.os.Build.MANUFACTURER + ","
                        + android.os.Build.MODEL + ","
                        + android.os.Build.PRODUCT + ","
                        + android.os.Build.TAGS + ","
                        + android.os.Build.TIME + ","
                        + android.os.Build.TYPE + ","
                        + android.os.Build.USER;
                data = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (printStream != null) {
                        printStream.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return info;
        }

        private void write2ErrorLog(String content) {
            FileOutputStream fos = null;
            try {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "my";
                File file = new File(path);
                if (!file.exists() && !file.isDirectory()) file.mkdirs();
                String filePath = path + File.separator + "error.txt";
                fos = new FileOutputStream(filePath);
                fos.write(content.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        hookTest();
    }

    public static void hookTest() {
        try {
            @SuppressLint("PrivateApi") Class<?> aClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = aClass.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object invoke = currentActivityThread.invoke(null);
            Field mH = aClass.getDeclaredField("mH");
            mH.setAccessible(true);
            Object handler = mH.get(invoke);
            Field mCallback = Handler.class.getDeclaredField("mCallback");
            mCallback.setAccessible(true);
            mCallback.set(handler, (Handler.Callback) msg -> {
                switch (msg.what) {
                    case 100:
                        Log.e(TAG, "hookTest");
                        Object obj = msg.obj;
                        break;
                }
                return false;
            });
            Field mInstrumentation = aClass.getDeclaredField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            mInstrumentation.set(invoke, new MyInstrumentation((Instrumentation) mInstrumentation.get(invoke)));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static class MyInstrumentation extends Instrumentation {
        private Instrumentation instrumentation;

        public MyInstrumentation(Instrumentation instrumentation) {
            this.instrumentation = instrumentation;
        }

        @Override
        public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
            Log.e(TAG, "newActivity+" + className);
            if (TextUtils.equals(className, ViewPagerActivity.class.getName())) {
                if (TextUtils.equals(intent.getStringExtra("type"), "Messenger"))
                    className = MessengerActivity.class.getName();
            }
            return super.newActivity(cl, className, intent);
        }
    }
}
