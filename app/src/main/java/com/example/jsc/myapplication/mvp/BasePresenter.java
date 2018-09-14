package com.example.jsc.myapplication.mvp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jsc.myapplication.net.Fault;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter基类
 * Created by jsc on 2017/11/27.
 */

public class BasePresenter<T extends BaseView> implements Presenter<T>,PresenterDelegate {
    protected CompositeSubscription subscription;
    protected String TAG = getClass().getSimpleName();
    private T mView;
    public BasePresenter() {
        subscription = new CompositeSubscription();
    }

    /**
     * 网络请求错误处理方法
     *
     * @param view
     * @param clazz
     * @return
     */
    public Action1<Throwable> getErrorAction(final BaseView view, final Class clazz) {
        return (throwable) -> {
            if (throwable instanceof Fault) {
                Fault fault = (Fault) throwable;
                view.onError(clazz, fault.errorCode, fault.errorMsg);
            } else {
                view.onError(clazz, -1, throwable.getMessage());

            }
            throwable.printStackTrace();
        };
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
