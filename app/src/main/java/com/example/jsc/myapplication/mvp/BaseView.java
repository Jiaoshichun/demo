package com.example.jsc.myapplication.mvp;

import android.content.Context;

/**
 * Created by jsc on 2017/11/27.
 */

public interface BaseView {
    void onError(Class clazz, int errorCode, String errorMsg);
    Context getContext() ;
}
