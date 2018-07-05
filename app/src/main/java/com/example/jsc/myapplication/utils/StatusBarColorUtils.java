package com.example.jsc.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.example.jsc.myapplication.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 非沉浸式修改状态栏工具类(沉浸式请用StatusBarImmersiveUtils)
 *
 * 修改状态栏背景颜色
 * 修改状态栏文字颜色(修改为深颜色时  做好无法修改为深颜色时的状态栏颜色的适配)
 * 参考 :https://github.com/imflyn/Eyes
 * https://dev.mi.com/console/doc/detail?pId=1159
 * http://open-wiki.flyme.cn/index.php?title=%E7%8A%B6%E6%80%81%E6%A0%8F%E5%8F%98%E8%89%B2
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class StatusBarColorUtils {

    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    /**
     * 修改状态栏背景颜色
     *
     * @param activity
     * @param statusColor 背景颜色
     */
    public static void setStatusBarColor(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColorLollipop(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarColorKitkat(activity, statusColor);
        }
    }
    /**
     * 修改状态栏背景颜色
     *
     * @param activity
     * @param statusColor 背景颜色
     * @param iconDark    状态栏文字颜色 true深颜色  false浅颜色  注：如果是true 设置失败时 将不会改变状态栏背景颜色
     */
    public static void setStatusBarColor(Activity activity, int statusColor, boolean iconDark) {
        if (iconDark) {//状态栏文字是否是深颜色
            if (setStatusBarDarkIcon(activity.getWindow(), true)) {
                setStatusBarColor(activity, statusColor);
                setStatusBarDarkIcon(activity.getWindow(), true);
            }
        } else {//状态栏文字设置为浅颜色
            setStatusBarDarkIcon(activity.getWindow(), false);
            //则文字设置为深颜色 如果设置失败不进行状态栏颜色修改
            setStatusBarColor(activity, statusColor);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarColorLollipop(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void setStatusBarColorKitkat(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        //获取父布局
        View mContentChild = mContentView.getChildAt(0);
        //获取状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);

        //如果已经存在假状态栏则移除，防止重复添加
        removeFakeStatusBarViewIfExist(activity);
        //添加一个View来作为状态栏的填充
        addFakeStatusBarView(activity, statusColor, statusBarHeight);
        //设置子控件到状态栏的间距
        addMarginTopToContentChild(mContentChild, statusBarHeight);
        //不预留系统栏位置
        if (mContentChild != null) {
            mContentChild.setFitsSystemWindows(false);
        }
        //如果在Activity中使用了ActionBar则需要再将布局与状态栏的高度跳高一个ActionBar的高度，否则内容会被ActionBar遮挡
        int action_bar_id = activity.getResources().getIdentifier("action_bar", "id", activity.getPackageName());
        View view = activity.findViewById(action_bar_id);
        if (view != null) {
            TypedValue typedValue = new TypedValue();
            if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
                setContentTopPadding(activity, actionBarHeight);
            }
        }
    }

    private static void setContentTopPadding(Activity activity, int padding) {
        ViewGroup mContentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        mContentView.setPadding(0, padding, 0, 0);
    }

    private static void removeFakeStatusBarViewIfExist(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            mDecorView.removeView(fakeView);
        }
    }

    private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View mStatusBarView = new View(activity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        layoutParams.gravity = Gravity.TOP;
        mStatusBarView.setLayoutParams(layoutParams);
        mStatusBarView.setBackgroundColor(statusBarColor);
        mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);

        mDecorView.addView(mStatusBarView);
        return mStatusBarView;
    }

    private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (!TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(TAG_MARGIN_ADDED);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }


    /**
     * 修改魅族手机状态栏字体颜色
     *
     * @param window
     * @param dark
     * @returna
     */
    private static boolean changeMeizuDrak(Window window, boolean dark) {
        return changeMeizuFlag(window.getAttributes(), "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark);
    }

    private static boolean changeMeizuFlag(WindowManager.LayoutParams winParams, String flagName, boolean on) {
        try {
            Field f = winParams.getClass().getDeclaredField(flagName);
            f.setAccessible(true);
            int bits = f.getInt(winParams);
            Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
            f2.setAccessible(true);
            int meizuFlags = f2.getInt(winParams);
            int oldFlags = meizuFlags;
            if (on) {
                meizuFlags |= bits;
            } else {
                meizuFlags &= ~bits;
            }
            if (oldFlags != meizuFlags) {
                f2.setInt(winParams, meizuFlags);
                return true;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置状态栏颜色 6.0以上
     *
     * @param window
     * @param dark
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean setStatusBarDarkIcon6(Window window, boolean dark) {
        boolean result = false;
        View view = window.getDecorView();
        if (view != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int oldVis = view.getSystemUiVisibility();
            int newVis = oldVis;
            if (dark) {
                newVis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                newVis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (newVis != oldVis) {
                view.setSystemUiVisibility(newVis);
                result = true;
            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param window 当前窗口
     * @param dark   是否深色 true为深色 false 为白色
     */
    public static boolean setStatusBarDarkIcon(Window window, boolean dark) {
        boolean result = false;
        try {
            if (MobileRomUtils.isMIUI() && MobileRomUtils.getMiUiVersion() < 9) {
                //如果是小米手机并且miui版本小于9  使用小米系统更改状态字体颜色方法
                //详情  https://dev.mi.com/console/doc/detail?pId=1159
                result = changeXiaomiDrak(window, dark);
            }
            if (!result && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = setStatusBarDarkIcon6(window, dark);
            }
            if (!result) {
                result = changeMeizuDrak(window, dark);
            }
            if (!result) {
                result = changeXiaomiDrak(window, dark);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 修改小米手机状态栏字体颜色
     *
     * @param window
     * @param darkmode true 深色  false白色
     */
    private static boolean changeXiaomiDrak(Window window, boolean darkmode) {
        boolean result = false;
        Class<? extends Window> clazz = window.getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, darkmode ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

