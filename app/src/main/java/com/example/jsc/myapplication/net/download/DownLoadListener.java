package com.example.jsc.myapplication.net.download;

import java.io.File;

/**
 * Created by jsc on 2017/11/28.
 */

public interface DownLoadListener {
    void onCompleted(File file);

    void onError(String errorMsg);

    void onProgress(int progress);
}
