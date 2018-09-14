package com.example.jsc.myapplication.net.api;

import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.net.BaseResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jsc on 2017/11/27.
 */

public interface UserServiceApi {
    //获取用户数据
    @GET("user/app/v2/user")
    Observable<BaseResponse<UserDetailBean>> getUserData();

}
