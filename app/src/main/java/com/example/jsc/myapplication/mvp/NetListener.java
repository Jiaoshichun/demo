package com.example.jsc.myapplication.mvp;

import com.example.jsc.myapplication.net.Fault;

import rx.Subscriber;

public abstract class NetListener<T> extends Subscriber<T> {
    public abstract void onSuccess(T data);

    public abstract void onFail(Throwable errorMsg);

    public abstract void onError(int errorCode, String errorMsg);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        try {
            if (throwable instanceof Fault) {
                Fault fault = (Fault) throwable;
                onError(fault.errorCode, fault.errorMsg);
            } else {
                onFail(throwable);
            }
        } catch (Exception e) {
            e.printStackTrace();
            onFail(e);
        }
    }

    @Override
    public void onNext(T t) {
        try {
            onSuccess(t);
        } catch (Exception e) {
            e.printStackTrace();
            onFail(e);
        }
    }
}
