package com.example.jsc.myapplication.presenter;

import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.model.UserModel;
import com.example.jsc.myapplication.view.IUserView;

/**
 * Created by jsc on 2018/1/2.
 */

public class UserPresenter extends BasePresenter {
    private final UserModel userModel;
    private IUserView view;

    public UserPresenter(IUserView view) {
        this.view = view;
        userModel = new UserModel();
    }

    public void getUserDetail() {
        subscription.add(userModel.getUserDeatail().subscribe(view::setUserDatail, getErrorAction(view, UserDetailBean.class)));
    }
}
