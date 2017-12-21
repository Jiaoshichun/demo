package com.example.jsc.myapplication.net;



import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jsc on 2017/6/14.
 */

public interface GitHubService {

//    http://gxgscyeizsl0cf34.apitest.share-times.com/auth/cloudnews/v1/verifyCode?phoneNumber=13012345678&operate=1
    @GET("/users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
    @GET("/auth/cloudnews/v1/verifyCode")
    Observable<CodeBean> getCode(@Field("phoneNumber") String phone,@Field("operate") String operate);
    @GET("/user/api/v1/login")
    Observable<CodeBean> logon(@Field("phoneNumber") String phone,@Field("operate") String operate);

}
