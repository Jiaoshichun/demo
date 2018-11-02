package com.example.jsc.myapplication.utils.imageloader;

public interface ILoaderStartegy {
    void loadImageOptions(JImageOptions jImageOptions);

    void clearMemoryCache();

    void clearDiskCache();
}
