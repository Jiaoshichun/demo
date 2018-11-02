package com.example.jsc.myapplication.utils.imageloader;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import java.io.File;

public class JImageLoader {
    private ILoaderStrategy iLoaderStrategy;

    private JImageLoader() {
    }

    private static class Inner {
        private final static JImageLoader INSTANCE = new JImageLoader();
    }


    public static JImageLoader getInstance() {
        return Inner.INSTANCE;
    }

    /**
     * 设置加载图片策略
     *
     * @param iLoaderStrategy
     */
    public void setLoaderStrategy(ILoaderStrategy iLoaderStrategy) {
        if (iLoaderStrategy != null)
            this.iLoaderStrategy = iLoaderStrategy;
    }

    public JImageOptions load(String url) {
        return new JImageOptions(url);
    }

    public JImageOptions load(File file) {
        return new JImageOptions(file);
    }

    public JImageOptions load(@DrawableRes int drawableResId) {
        return new JImageOptions(drawableResId);
    }

    public JImageOptions load(Uri uri) {
        return new JImageOptions(uri);
    }

    public void loadOptions(JImageOptions jImageOptions) {
        try {
            iLoaderStrategy.loadImageOptions(jImageOptions);
        } catch (NullPointerException e) {
            throw new RuntimeException("必须调用 " +
                    "JImageLoader.getInstance().setLoaderStrategy(ILoaderStrategy iLoaderStrategy) " +
                    "设置图片加载策略,建议在Application中进行设置");
        }

    }

    public void clearMemoryCache() {
        try {
            iLoaderStrategy.clearMemoryCache();
        } catch (NullPointerException e) {
            throw new RuntimeException("必须调用 " +
                    "JImageLoader.getInstance().setLoaderStrategy(ILoaderStrategy iLoaderStrategy) " +
                    "设置图片加载策略,建议在Application中进行设置");
        }
    }

    public void clearDiskCache() {
        try {
            iLoaderStrategy.clearDiskCache();
        } catch (NullPointerException e) {
            throw new RuntimeException("必须调用 " +
                    "JImageLoader.getInstance().setLoaderStrategy(ILoaderStrategy iLoaderStrategy) " +
                    "设置图片加载策略,建议在Application中进行设置");
        }
    }

    public void clearAll() {
        clearMemoryCache();
        clearDiskCache();
    }

}
