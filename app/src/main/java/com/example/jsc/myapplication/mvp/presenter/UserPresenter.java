package com.example.jsc.myapplication.mvp.presenter;

import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.mvp.model.UserModel;
import com.example.jsc.myapplication.mvp.BasePresenter;
import com.example.jsc.myapplication.mvp.view.UserView;

import rx.functions.Action1;

/**
 * Created by jsc on 2018/1/2.
 */

public class UserPresenter extends BasePresenter<UserView> {
    private final UserModel userModel;

    public UserPresenter() {

        userModel = new UserModel();
    }

    public void getUserDetail() {
        subscription.add(userModel.getUserDeatail().subscribe(userDetailBean -> getView().setUserDetail(userDetailBean),
                getErrorAction(getView(), UserDetailBean.class)));
    }
}
