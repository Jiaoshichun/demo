package com.example.kayo.myapplication.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<T extends BasePresenter<V>, V extends BaseView> extends Activity implements BaseView {
    private T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedPresenter())
            createPresenter(savedInstanceState);

    }

    /**
     * @return 不需要Presenter 时重新该方法返回false
     */
    protected boolean isNeedPresenter() {
        return true;
    }

    private void createPresenter(Bundle savedInstanceState) {
        try {
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (!(genericSuperclass instanceof ParameterizedType))
                throw new RuntimeException(getClass().getSimpleName() + "：Generics not declared ,method(isNeedPresenter) must return false");
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
        if (isNeedPresenter())
            mPresenter.onNewIntent(intent);
    }

    protected T getPresenter() {
        if (!isNeedPresenter())
            throw new RuntimeException(getClass().getSimpleName() + "：if need Presenter method(isNeedPresenter) must return true");
        return mPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (isNeedPresenter())
            mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedPresenter())
            mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isNeedPresenter())
            mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isNeedPresenter())
            mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isNeedPresenter()) {
            mPresenter.onDestroy();
            mPresenter.detachView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isNeedPresenter())
            mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
