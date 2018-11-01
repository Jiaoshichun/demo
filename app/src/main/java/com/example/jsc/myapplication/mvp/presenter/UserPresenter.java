package com.example.jsc.myapplication.mvp.presenter;

import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.common.ToastUtils;
import com.example.jsc.myapplication.mvp.BasePresenter;
import com.example.jsc.myapplication.mvp.NetListener;
import com.example.jsc.myapplication.mvp.model.UserModel;
import com.example.jsc.myapplication.mvp.view.UserView;

import rx.Subscription;

/**
 * Created by jsc on 2018/1/2.
 */

public class UserPresenter extends BasePresenter<UserView> {
    private final UserModel userModel;

    public UserPresenter() {
        userModel = new UserModel();
    }

    public void getUserDetail() {

        managerSubscription(userModel.getUserDetail(new NetListener<UserDetailBean>() {
            @Override
            public void onSuccess(UserDetailBean data) {

            }

            @Override
            public void onFail(Throwable errorMsg) {

            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        }));
    }


}
