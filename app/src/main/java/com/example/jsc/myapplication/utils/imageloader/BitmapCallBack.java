package com.example.jsc.myapplication.utils.imageloader;

import android.graphics.Bitmap;
import android.view.View;

public interface BitmapCallBack {
    void onBitmapLoaded(Bitmap bitmap);

    void onBitmapFailed(Exception e);
}
