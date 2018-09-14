package com.example.jsc.myapplication.mvp.view;

import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.mvp.BaseView;

/**
 * Created by jsc on 2018/1/2.
 */

public interface UserView extends BaseView {
    void setUserDetail(UserDetailBean bean);
}
