package com.example.jsc.myapplication.model;

import com.example.jsc.myapplication.api.UserServiceApi;
import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.net.RetrofitServiceManager;

import rx.Observable;

/**
 * Created by jsc on 2018/1/2.
 */

public class UserModel extends BaseModel {

    private final UserServiceApi userServiceApi;

    public UserModel() {
        userServiceApi = RetrofitServiceManager.getInstance().create(UserServiceApi.class);
    }

    public Observable<UserDetailBean> getUserDeatail() {
        return userServiceApi.getUserData().map(getData());
    }
}
