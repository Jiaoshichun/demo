package com.example.jsc.myapplication.mvp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MvpFragment<T extends BasePresenter<V>, V extends BaseView> extends Fragment implements BaseView {
    private T mPresenter;
    public final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                throw new RuntimeException(TAG + "： Generics must be declared ");
            }
            Type type = types[0];
            Class pClazz = (Class) type;
            mPresenter = (T) pClazz.newInstance();
            try {
                mPresenter.attachView((V) this);
            } catch (ClassCastException e) {
                throw new RuntimeException(TAG + " must implements " + types[1]);
            }
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mPresenter.onCreate(savedInstanceState, getActivity().getIntent());
    }

    public void onNewIntent(Intent intent) {
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
        return getActivity();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null)
            mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null)
            mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null)
            mPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter.detachView();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null)
            mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
