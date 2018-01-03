package com.example.jsc.myapplication.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


import com.example.jsc.myapplication.MyApplication;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UIUtils {
    /**
     * 获取Context对象
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 获取Handler对象
     *
     * @return
     */
    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    /**
     * 获得主线程的id
     *
     * @return
     */
    public static int getTid() {
        return MyApplication.getTid();
    }
    // ------------------------------------
    // dip和px的转换

    /**
     * px转为dip
     *
     * @param px
     * @return
     */
    public static float px2dip(float px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density + 0.5f;
    }

    /**
     * dip转为px
     *
     * @param dip
     * @return
     */
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (float) px / density;
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px( float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }



    // ----------------------------------
    // 通过id获取资源对象

    /**
     * 通过资源id获取字符串
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 通过资源id获取颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    public static String getText(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 根据资源id获取尺寸
     *
     * @param id
     * @return
     */
    public static int getDimension(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    /**
     * 根据资源id获取图片文件
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    /**
     * 根据资源id获取字符串数组
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    /**
     * 根据id获取颜色选择器对象
     *
     * @param id
     * @return
     */
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    public static int[] getScreenWidthAndHeight() {
        int[] ints = new int[2];
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        // 方法1,获取屏幕的默认分辨率
        Display display = manager.getDefaultDisplay(); // getWindowManager().getDefaultDisplay();
        ints[0] = display.getWidth(); // 屏幕宽（像素，如：480px）
        ints[1] = display.getHeight(); // 屏幕高（像素，如：800px）
        return ints;
    }

    /**
     * 根据布局文件生成View对象
     *
     * @param resource
     * @return
     */
    public static View getView(int resource) {
        return View.inflate(getContext(), resource, null);
    }
    // ------------------------------------------
    // 线程相关工具

    /**
     * 判断当前线程是否为主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return getTid() == android.os.Process.myTid();
    }

    /**
     * 运行在主线程的方法
     *
     * @param r
     */
    public static void runOnUi(Runnable r) {
        if (r != null) {
            if (isMainThread()) {
                r.run();
            } else {
                getHandler().post(r);
            }
        }
    }

    //在弹幕中  把颜色值变为int类型
    public static int getColorByInt(int colorInt) {
        return colorInt | -16777216;
    }


    /**
     * 获得状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 截取当前屏幕
     *
     * @param activity
     * @return
     */
    public static Bitmap captureScreen(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();

        return bmp;

    }


    //获取屏幕原始尺寸高度，包括虚拟功能键高度

    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenWidthAndHeight()[1];

        return totalHeight - contentHeight;
    }
    /**
     * make true current connect service is wifi
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
    /**
     * 限制文字的最大长度
     *
     * @param inputText 要限制的edittext
     * @param maxLength 最大长度
     * @param showToast 是否展示toast
     */
    public static void setupLengthFilter(EditText inputText,
                                         final int maxLength, final boolean showToast) {
        // Create a new filter
        InputFilter.LengthFilter filter = new InputFilter.LengthFilter(
                maxLength) {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source != null
                        && source.length() > 0
                        && (((dest == null ? 0 : dest.length()) + dstart - dend) == maxLength)) {
                    if (showToast) {
                        ToastUtils.show("超出最大长度");
                    }
                    return "";
                }
                return super.filter(source, start, end, dest, dstart, dend);
            }
        };

        // Find exist lenght filter.
        InputFilter[] filters = inputText.getFilters();
        int length = 0;
        for (int i = 0; i < filters.length; i++) {
            if (!(filters[i] instanceof InputFilter.LengthFilter)) {
                length++;
            }
        }

        //Only one length filter.
        InputFilter[] contentFilters = new InputFilter[length + 1];
        for (int i = 0; i < filters.length; i++) {
            if (!(filters[i] instanceof InputFilter.LengthFilter)) {
                contentFilters[i] = filters[i];
            }
        }
        contentFilters[length] = filter;
        inputText.setFilters(contentFilters);
    }
    /**
     * 限制文本输入数字大小,int型 正数
     *
     * @param editText
     * @param minNum   最小
     * @param maxNum   最大
     * @param toastMsg 吐司内容  null则不显示
     */
    public static void limitEditTextNum(final EditText editText, final int minNum, final int maxNum, final String toastMsg) {
        if (editText == null) return;
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        // Create a new filter
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (dstart != dend) return null;
                try {
                    int length = String.valueOf(maxNum).length();
                    if (TextUtils.isEmpty(dest)) {
                        if (length == 1) {
                            if (Integer.valueOf(source.toString()) > maxNum) {
                                if (!TextUtils.isEmpty(toastMsg)) {
                                    ToastUtils.show(toastMsg);
                                }
                                return "";
                            }
                        }
                        return null;
                    }
                    if ("0".equalsIgnoreCase(dest.toString())) {
                        if (!TextUtils.isEmpty(toastMsg)) {
                            ToastUtils.show(toastMsg);
                        }
                        return "";
                    }
                    if (dest.toString().length() == length - 1) {
                        if (Integer.valueOf(dest.toString() + source.toString()) > maxNum) {
                            if (!TextUtils.isEmpty(toastMsg)) {
                                ToastUtils.show(toastMsg);
                            }
                            return "";
                        }
                    } else if (dest.toString().length() == length) {
                        if (!TextUtils.isEmpty(toastMsg)) {
                            ToastUtils.show(toastMsg);
                        }
                        return "";
                    }
                    return null;
                } catch (Exception e) {
                    return "";
                }
            }
        };
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(editText.getText().toString()) || Integer.valueOf(editText.getText().toString()) < minNum) {
                        editText.setText(String.valueOf(minNum));
                        if (!TextUtils.isEmpty(toastMsg)) {
                            ToastUtils.show(toastMsg);
                        }
                    }
                }
            }
        });
        editText.setFilters(new InputFilter[]{filter});

    }
    /**
     * 将过长的字符串进行分割  该方法会截取完整的英文单词
     *
     * @param string    要分割的字符串
     * @param maxLength 每个字符串的最大长度
     * @return
     */
    public static List<CharSequence> divideLengthText(CharSequence string, int maxLength) {
        ArrayList<CharSequence> list = new ArrayList<>();
        String text = string.toString();
        if (text.length() > maxLength) {
            int count = text.length() % maxLength == 0 ? text.length() / maxLength : text.length() / maxLength + 1;
            int lastPos = 0;
            for (int i = 0; i < count; i++) {
                if (i < count - 1) {
                    String charSequence = text.substring(lastPos, lastPos + maxLength);
                    //  大写字母65-90 ，小写字母97-122
                    char a = text.charAt(lastPos + maxLength);
                    char b = text.charAt(lastPos + maxLength - 1);
                    //如果分割点 以及 分割点的左侧和右侧的字符都是英文字符 则从前面一个空格处截取  避免将英文单词分开
                    if (((a < 91 && a > 64) || (a > 96 && a < 123)) && ((b < 91 && b > 64) || (b > 96 && b < 123))) {
                        int lastSpace = charSequence.lastIndexOf(" ");
                        if (maxLength - lastSpace < 20) {
                            String substring = text.substring(lastPos, lastPos + lastSpace);
                            list.add(substring);
                            lastPos = lastPos + lastSpace;
                        } else {//如果一直到倒数 20个都没有空格 则表示当前不为英文单词
                            list.add(charSequence);
                            lastPos = lastPos + maxLength;
                        }
                    } else {
                        list.add(charSequence);
                        lastPos = lastPos + maxLength;
                    }
                } else {
                    list.add(text.subSequence(lastPos, text.length()));
                }
            }
        } else {
            list.add(text);
        }
        return list;
    }

}