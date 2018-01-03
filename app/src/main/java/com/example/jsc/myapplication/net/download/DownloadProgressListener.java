package com.example.jsc.myapplication.net.download;

/**
 * Created by jsc on 2017/11/28.
 */

 interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
