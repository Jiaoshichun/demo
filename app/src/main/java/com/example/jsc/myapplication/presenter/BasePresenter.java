package com.example.jsc.myapplication.presenter;


import com.example.jsc.myapplication.net.Fault;
import com.example.jsc.myapplication.view.BaseView;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter基类
 * Created by jsc on 2017/11/27.
 */

public class BasePresenter {
    protected CompositeSubscription subscription;
    protected String TAG = getClass().getSimpleName();

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

    /**
     * activity / fragment销毁时 调用该方法 避免内存泄漏
     */
    public void onDestroy() {
        subscription.clear();
    }
}
