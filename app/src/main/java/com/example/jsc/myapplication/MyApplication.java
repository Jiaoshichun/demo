package com.example.jsc.myapplication;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by jsc on 2017/6/14.
 */

public class MyApplication extends Application {
    private final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new UEHandler(this));
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
                if(!file.exists()&&!file.isDirectory()) file.mkdirs();
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
}
