package com.example.jsc.myapplication.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jsc.myapplication.BaseActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class MvpActivity<T extends BasePresenter<V>, V extends BaseView> extends BaseActivity implements BaseView {
    private T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter(savedInstanceState);

    }


    private void createPresenter(Bundle savedInstanceState) {
        try {
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (!(genericSuperclass instanceof ParameterizedType))//如果没有声明泛型 则返回不进行Presenter初始化
                return;
            Type[] types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (types == null || types.length != 2) {
                throw new RuntimeException(getClass().getSimpleName() + "： Generics must be declared ");
            }
            Type type = types[0];
            Class pClazz = (Class) type;
            mPresenter = (T) pClazz.newInstance();
            try {
                mPresenter.attachView((V) this);
            } catch (ClassCastException e) {
                throw new RuntimeException(getClass().getSimpleName() + " must extends " + types[1]);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mPresenter.onCreate(savedInstanceState, getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mPresenter != null)
            mPresenter.onNewIntent(intent);
    }

    protected T getPresenter() {
        if (mPresenter == null)
            throw new RuntimeException(getClass().getSimpleName() + "：No declare generics presenter");
        return mPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null)
            mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null)
            mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null)
            mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter.detachView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null)
            mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
