package com.example.jsc.myapplication.net;

/**
 * 网络请求基类
 * Created by jsc on 2017/11/27.
 */

public class BaseResponse<T> {
    public int code;
    public String message;
    public T data;

    public boolean isSuccess() {
        return code == 0;
    }
}
