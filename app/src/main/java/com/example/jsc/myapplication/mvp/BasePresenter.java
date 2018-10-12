package com.example.jsc.myapplication.mvp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter基类
 * Created by jsc on 2017/11/27.
 */

public class BasePresenter<T extends BaseView> implements Presenter<T>,PresenterDelegate {
    private CompositeSubscription subscription = new CompositeSubscription();
    protected String TAG = getClass().getSimpleName();
    private T mView;
    public BasePresenter() {

    }



    @Override
    public void onNewIntent(Intent mIntent) {

    }

    @Override
    public void onCreate(Bundle bundle, Intent mIntent) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
    protected void managerSubscription(Subscription s){
        subscription.add(s);
    }
    @Override
    public void onStop() {

    }
    protected T getView() {
        return mView;
    }

    public boolean isViewAttached() {
        return null != mView;
    }

    public Context getContext() {
        if (mView == null) {
            return null;
        }
        return mView.getContext();
    }
    /**
     * activity / fragment销毁时 调用该方法 避免内存泄漏
     */
    public void onDestroy() {
        subscription.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void attachView(T view) {
        this.mView=view;
    }

    @Override
    public void detachView() {
        this.mView=null;
    }
}
