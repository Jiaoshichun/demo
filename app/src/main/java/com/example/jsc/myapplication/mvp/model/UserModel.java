package com.example.jsc.myapplication.mvp.model;

import com.example.jsc.myapplication.mvp.NetListener;
import com.example.jsc.myapplication.net.api.UserServiceApi;
import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.net.RetrofitServiceManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jsc on 2018/1/2.
 */

public class UserModel extends BaseModel {

    private final UserServiceApi userServiceApi;

    public UserModel() {
        userServiceApi = RetrofitServiceManager.getInstance().create(UserServiceApi.class);
    }

    public Observable<UserDetailBean> getUserDetail() {
        return userServiceApi.getUserData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(getData());
    }
}
