package com.example.jsc.myapplication.utils.imageloader;

public interface ILoaderStrategy {
    void loadImageOptions(JImageOptions jImageOptions);

    void clearMemoryCache();

    void clearDiskCache();
}
