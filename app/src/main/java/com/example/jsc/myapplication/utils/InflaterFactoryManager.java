package com.example.jsc.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class InflaterFactoryManager implements LayoutInflater.Factory {
    private final static String TAG = "InflaterFactoryManager";
    private LayoutInflater.Factory factory;
    private LayoutInflater.Factory2 factory2;
    private LayoutInflater layoutInflater;
    @ColorInt
    private  int mBackgroundColor = -1;

    public InflaterFactoryManager(Activity activity) {
        this.activity = activity;
        layoutInflater = activity.getLayoutInflater();
    }

    private Activity activity;

    public static InflaterFactoryManager inject(Activity activity) {
        InflaterFactoryManager inflaterFactoryManager = new InflaterFactoryManager(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            AppCompatDelegate delegate = appCompatActivity.getDelegate();
            inflaterFactoryManager.setInterruptFactory((name, context, attrs) -> delegate.createView(null, name, context, attrs));
        }
        try {
            Field mFactorySet = layoutInflater.getClass().getField("mFactorySet");
            mFactorySet.setAccessible(true);
            Boolean factorySet = (Boolean) mFactorySet.get(layoutInflater);
            if (factorySet) {
                mFactorySet.set(mFactorySet, false);
                if (layoutInflater.getFactory() != null) {
                    inflaterFactoryManager.setInterruptFactory(layoutInflater.getFactory());
                    layoutInflater.setFactory(inflaterFactoryManager);
                } else if (layoutInflater.getFactory2() != null) {
                    inflaterFactoryManager.setInterruptFactory(layoutInflater.getFactory2());
                    layoutInflater.setFactory2(new LayoutInflater.Factory2() {
                        @Override
                        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                            return inflaterFactoryManager.onCreateView(name, context, attrs);
                        }

                        @Override
                        public View onCreateView(String name, Context context, AttributeSet attrs) {
                            return inflaterFactoryManager.onCreateView(name, context, attrs);
                        }
                    });
                } else {
                    layoutInflater.setFactory(inflaterFactoryManager);
                }
            } else {
                layoutInflater.setFactory(inflaterFactoryManager);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            layoutInflater.setFactory(inflaterFactoryManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            layoutInflater.setFactory(inflaterFactoryManager);
        }
        return inflaterFactoryManager;
    }

    private void setInterruptFactory(LayoutInflater.Factory2 factory2) {
        this.factory2 = factory2;
    }

    private void setInterruptFactory(LayoutInflater.Factory factory) {
        this.factory = factory;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (mBackgroundColor == -1) return null;
        Log.e(TAG, "--------start:" + name);
        View view = null;
        if (factory != null) {
            view = factory.onCreateView(name, context, attrs);
        } else if (factory2 != null) {
            view = factory2.onCreateView(null, name, context, attrs);
        }
        if (view == null) {
            view = createFromTag(name, context, attrs);
        }
        if (view != null) {
            if (!(view instanceof ViewGroup)) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginLayoutParams.leftMargin = 2000;
                    view.setLayoutParams(marginLayoutParams);
                }
                view.setBackgroundColor(mBackgroundColor);
            }
            Log.e(TAG, "--------end:" + view.getClass().getCanonicalName());
        }
        return view;
    }

    public  void setBackgroundColor(@ColorInt int backgroundColor) {
        mBackgroundColor = backgroundColor;
        activity.recreate();
    }

    private View createFromTag(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        if (-1 == name.indexOf('.')) {
            view = createView(name, "android.view.", attrs);
            if (view == null) {
                createView(name, "android.widget.", attrs);
            }
            if (view == null) {
                createView(name, "android.webkit.", attrs);
            }
        } else {
            view = createView(name, null, attrs);
        }
        return view;
    }

    private View createView(String name, String prefix, AttributeSet attrs) {
        View view = null;
        try {
            view = layoutInflater.createView(name, prefix, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }
}
