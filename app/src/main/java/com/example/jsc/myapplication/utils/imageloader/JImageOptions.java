package com.example.jsc.myapplication.utils.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.File;

public class JImageOptions {
    int placeholderResId;
    int errorResId;
    boolean isCenterCrop;
    boolean isCenterInside;
    boolean skipLocalCache; //是否缓存到本地
    boolean skipMemoryCache;
    Bitmap.Config config = Bitmap.Config.ARGB_4444;
    int targetWidth;
    int targetHeight;
    float bitmapAngle; //圆角角度 单位dp
    boolean isCircle; //是否是圆形

    Drawable placeholder;
    View targetView;//targetView展示图片
    BitmapCallBack callBack;
    String url;
    File file;
    int drawableResId;
    Uri uri;

    public JImageOptions(File file) {
        this.file = file;
    }

    public JImageOptions(Uri uri) {
        this.uri = uri;
    }

    public JImageOptions(@DrawableRes int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public JImageOptions(String url) {
        this.url = url;
    }

    public void into(View targetView) {
        this.targetView = targetView;
        JImageLoader.getInstance().loadOptions(this);
    }

    public void bitmap(BitmapCallBack callBack) {
        this.callBack = callBack;
        JImageLoader.getInstance().loadOptions(this);
    }

    public JImageOptions placeholder(@DrawableRes int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    public JImageOptions placeholder(Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public JImageOptions error(@DrawableRes int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public JImageOptions centerCrop() {
        isCenterCrop = true;
        return this;
    }

    public JImageOptions centerInside() {
        isCenterInside = true;
        return this;
    }

    public JImageOptions config(Bitmap.Config config) {
        this.config = config;
        return this;
    }


    public JImageOptions resize(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    /**
     * 圆角
     *
     * @param bitmapAngle 度数  单位dp
     * @return
     */
    public JImageOptions angle(float bitmapAngle) {
        this.bitmapAngle = bitmapAngle;
        return this;
    }

    public JImageOptions skipLocalCache(boolean skipLocalCache) {
        this.skipLocalCache = skipLocalCache;
        return this;
    }

    public JImageOptions skipMemoryCache(boolean skipMemoryCache) {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }


    public JImageOptions isCircle() {
        this.isCircle = true;
        return this;
    }


}
