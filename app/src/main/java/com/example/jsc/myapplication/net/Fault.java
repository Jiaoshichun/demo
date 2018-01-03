package com.example.jsc.myapplication.net;

/**
 * Created by jsc on 2017/11/27.
 */

public class Fault extends RuntimeException {
    public String errorMsg;
    public int errorCode;

    public Fault(int errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
}
