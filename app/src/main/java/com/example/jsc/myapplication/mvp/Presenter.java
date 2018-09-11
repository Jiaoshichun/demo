package com.example.kayo.myapplication.mvp;

public interface Presenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
