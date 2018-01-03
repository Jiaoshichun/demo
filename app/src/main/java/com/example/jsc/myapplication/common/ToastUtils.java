package com.example.jsc.myapplication.common;

import android.widget.Toast;


/**
 * Created by jpm on 2016/6/21.
 */
public class ToastUtils {
    private static Toast toast = null;
    private static Toast imgMiddleToast = null;

    /**
     * 普通的Toast
     */
    public static void show(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(UIUtils.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    /**
     * 普通的Toast
     */
    public static void show(int id) {
        if (toast == null) {
            toast = Toast.makeText(UIUtils.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(id);

        toast.show();
    }


    public static void showLong(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(UIUtils.getContext(), "", Toast.LENGTH_LONG);
        }
        toast.setText(text);

        toast.show();
    }

    public static void showLong(int id) {
        if (toast == null) {
            toast = Toast.makeText(UIUtils.getContext(), "", Toast.LENGTH_LONG);
        }
        toast.setText(id);

        toast.show();
    }



}
