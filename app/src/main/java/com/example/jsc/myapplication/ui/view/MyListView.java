package com.example.jsc.myapplication.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

/**
 * Created by jsc on 2017/6/30.
 */

public class MyListView extends ListView {
    private SwitchBaseAdapter adapter;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof SwitchBaseAdapter) {
            this.adapter = (SwitchBaseAdapter) adapter;
        } else {
            throw new IllegalStateException("the adapter must be implements SwitchBaseAdapter");
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("MyListView","onDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("MyListView","onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("MyListView","onLayout");
    }

    public void moveUp(int position) {
        if (position != 0) {
            Collections.swap(adapter.getData(), position, position - 1);
            adapter.notifyDataSetChanged();
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    getViewTreeObserver().removeOnPreDrawListener(this);
                    if (getFirstVisiblePosition() == position) {

                    } else {

                    }
                    return true;
                }
            });

        }
    }

    public void moveDown(int position) {
        if (position != adapter.getData().size() - 1) {

        }
    }

    private Handler mHandler = new Handler();
    /**
     * 当moveY的值大于向上滚动的边界值，触发GridView自动向上滚动
     * 当moveY的值小于向下滚动的边界值，触发GridView自动向下滚动
     * 否则不进行滚动
     */
    private Runnable mScrollRunnable = new Runnable() {

        @Override
        public void run() {
            int scrollY;
            if (getFirstVisiblePosition() == 0 || getLastVisiblePosition() == getCount() - 1) {
                mHandler.removeCallbacks(mScrollRunnable);
            }

//            if (moveY > mUpScrollBorder) {
//                scrollY = speed;
//                mHandler.postDelayed(mScrollRunnable, 25);
//            } else if (moveY < mDownScrollBorder) {
//                scrollY = -speed;
//                mHandler.postDelayed(mScrollRunnable, 25);
//            } else {
//                scrollY = 0;
//                mHandler.removeCallbacks(mScrollRunnable);
//            }

//            smoothScrollBy(scrollY, 10);
        }
    };


    public static abstract class SwitchBaseAdapter extends BaseAdapter {
        /**
         * 获取adapter的数据
         *
         * @return
         */
        public abstract List getData();
    }
}
