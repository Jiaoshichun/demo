package com.example.jsc.myapplication.utils;

import android.app.Activity;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Activity栈管理类
 * Created by haoge on 2016/11/25.
 */
public class ActivityStackHelper {
    private static LinkedList<Activity> stack = new LinkedList<>();

    public static LinkedList<Activity> getStack() {
        return stack;
    }

    /**
     * 添加一个Activity到模拟栈中。
     *
     * @param activity 需要放入的Activity实例。
     */
    public static void add(Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        if (!stack.contains(activity)) {
            stack.add(activity);
        }
    }

    /**
     * 将模拟栈中顶层的Activity移除
     */
    public static void pop() {
        if (!stack.isEmpty()) {
            Activity pop = stack.pop();
            recycler(pop);
        }
    }

    /**
     * 将Activity移除，但不finish
     */
    public static void remove(Activity activity) {
        if (stack.contains(activity)) {
            stack.remove(activity);
        }
    }

    /**
     * 将指定Activity从模拟栈中移除
     *
     * @param activity 指定移除的Activity
     */
    public static void pop(Activity activity) {
        if (stack.contains(activity)) {
            stack.remove(activity);
            recycler(activity);
        }
    }

    public static boolean contains(Activity activity) {
        return stack.contains(activity);
    }

    public static boolean contains(Class clazz) {
        for (Activity a : stack) {
            if (a != null && TextUtils.equals(a.getClass().getName(), clazz.getName()))
                return true;
        }
        return false;
    }
    public static Activity getActivity(Class clazz){
        for (Activity a : stack) {
            if (a != null && TextUtils.equals(a.getClass().getName(), clazz.getName()))
                return a;
        }
        return null;
    }

    /**
     * 将模拟栈中所有的Activity移除清空。
     */
    public static void popAll() {
        while (!stack.isEmpty()) {
            Activity pop = stack.pop();
            recycler(pop);
        }
    }

    /**
     * 获取栈中最顶层的Activity
     *
     * @param <T> T extends Activity
     * @return 最顶层的Activity
     */
    public static <T extends Activity> T top() {
        if (!stack.isEmpty()) {
            //noinspection unchecked
            return (T) stack.getLast();
        }
        return null;
    }

    public static <T extends Activity> T bottom() {
        if (!stack.isEmpty()) {
            //noinspection unchecked
            return (T) stack.getFirst();
        }
        return null;
    }

    /**
     * 将模拟栈中出了指定的Activity之外的所有Activity全部移除。
     *
     * @param excludes 所指定保留的Activity的类名集合
     */
    public synchronized static void clearAllExclude(String... excludes) {
        List<String> excludeActivities = Arrays.asList(excludes);
        for (int i = 0; i < stack.size(); i++) {
            Activity activity = stack.get(i);
            if (!excludeActivities.contains(activity.getClass().getSimpleName())) {
                stack.remove(activity);
                recycler(activity);
                i--;
            }
        }
    }

    /**
     * highActivity是否在lowActivity的上面并且两个activity之间没有第三个activity
     *
     * @param lowActivity
     * @param highActivity
     * @return
     */
    public static boolean isBeforeAndNear(Activity lowActivity, Activity highActivity) {
        boolean result = false;
        if (lowActivity != null && highActivity != null
                && !lowActivity.isFinishing() && !highActivity.isFinishing()) {
            int highIndex = stack.indexOf(highActivity);
            int lowIndex = stack.indexOf(lowActivity);
            if (lowIndex != -1 && highIndex != -1) {
                if (lowIndex + 1 == highIndex) {
                    result = true;
                }
            }
        }
        return result;
    }

    private static void recycler(Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        activity.finish();
    }
}
