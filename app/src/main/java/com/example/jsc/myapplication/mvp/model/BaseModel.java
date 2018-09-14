package com.example.jsc.myapplication.mvp.model;



import com.example.jsc.myapplication.net.BaseResponse;
import com.example.jsc.myapplication.net.Fault;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jsc on 2017/11/27.
 */

public class BaseModel {
    /**
     * 线程切换
     *
     * @param observable 被观察者对象
     * @param <T>        返回的被观察者对象 的泛型 BaseResponse<T>  请求响应体基类中的泛型
     * @return
     */
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取出请求数据 data里面的数据
     *
     * @param <T> data中的数据类型
     * @return
     */
    protected <T> Func1<BaseResponse<T>, T> getData() {
        return baseResponse -> {
            if (!baseResponse.isSuccess()) {
                throw new Fault(baseResponse.code, baseResponse.message);
            }
            return baseResponse.data;
        };
    }
}
