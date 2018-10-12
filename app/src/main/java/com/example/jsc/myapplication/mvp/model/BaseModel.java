package com.example.jsc.myapplication.mvp.model;



import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.mvp.NetListener;
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
