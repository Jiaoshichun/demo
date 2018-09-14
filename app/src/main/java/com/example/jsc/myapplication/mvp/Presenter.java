package com.example.jsc.myapplication.mvp;

public interface Presenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
