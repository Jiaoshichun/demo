package com.example.jsc.myapplication.utils;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 吸顶工具类  用于将RecyclerView的其中一个条目进行吸顶操作
 * 使用方式:
 * 1.创建吸顶工具类 传入对应的RecyclerView,吸顶布局,吸顶布局的高度
 *
 * 2.在RecyclerView的吸顶Holder中，调用{@link #onBindXidingItemView(ViewGroup, int)}即可
 */
public class XidingUtils {
    private RecyclerView recyclerView;//包含吸顶条目的RecylerView
    private ViewGroup xidingContainer;//吸顶时的吸顶容器
    private ViewGroup itemContainer;//吸顶条目中的吸顶容器
    private View xidingView;//吸顶的View
    private int position = -1;//吸顶条目在列表中位置
    private boolean isXidingStatus;
    private int height;

    public XidingUtils(final RecyclerView recyclerView, @LayoutRes int xidingLayoutId, int xidingViewHeight) {
        this(recyclerView, LayoutInflater.from(recyclerView.getContext()).inflate(xidingLayoutId, null), xidingViewHeight);
    }

    /**
     * @param recyclerView     包含吸顶Holder的RecyclerView
     * @param xidingView       吸顶布局
     * @param xidingViewHeight 吸顶布局的高度
     */
    public XidingUtils(final RecyclerView recyclerView, View xidingView, int xidingViewHeight) {
        this.recyclerView = recyclerView;
        this.xidingView = xidingView;
        this.height = xidingViewHeight;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (position == -1) return;
                try {
                    int firstVisiblePosition = getFirstVisiblePosition(recyclerView);
                    if (firstVisiblePosition >= position) {
                        if (!isXidingStatus)
                            changeXidingStatus(true);
                    } else {
                        if (isXidingStatus)
                            changeXidingStatus(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //为recyclerView增加一个FrameLayout的父布局 用户添加吸顶布局
        ViewGroup parent = (ViewGroup) recyclerView.getParent();
        if (parent == null) {
            throw new RuntimeException("recyclerView not attached parent");
        }
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        xidingContainer = new FrameLayout(recyclerView.getContext());
        int index = parent.indexOfChild(recyclerView);
        parent.removeView(recyclerView);
        parent.addView(xidingContainer, index, layoutParams);
        xidingContainer.addView(recyclerView, new FrameLayout.LayoutParams(layoutParams.width, layoutParams.height));

    }

    //更新吸顶状态
    private void changeXidingStatus(boolean isXiding) {
        if (xidingView == null) return;
        isXidingStatus = isXiding;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                , height);
        ViewGroup oldParent = (ViewGroup) xidingView.getParent();
        if (oldParent != null) {
            oldParent.removeView(xidingView);
        }
        ViewGroup newParent;
        if (isXidingStatus) {
            newParent = xidingContainer;
        } else {
            newParent = itemContainer;
        }
        if (newParent == null) return;
        newParent.addView(xidingView, layoutParams);
    }

    /**
     * 在吸顶的viewholder的onBindView中调用该方法
     *
     * @param viewGroup 条目中吸顶容器
     * @param position  吸顶条目的位置
     */
    public void onBindXidingItemView(ViewGroup viewGroup, int position) {
        this.itemContainer = viewGroup;
        this.position = position;
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        layoutParams.height = height;
        viewGroup.setLayoutParams(layoutParams);
        int firstVisiblePosition = getFirstVisiblePosition(recyclerView);
        if (firstVisiblePosition >= position) {
            changeXidingStatus(true);
        } else {
            changeXidingStatus(false);
        }
    }

    public int getXidingHoldePos() {
        return position;
    }

    /**
     * 获取第一条展示的位置
     */
    private int getFirstVisiblePosition(RecyclerView recyclerView) {
        int position = -1;
        if (recyclerView == null) return position;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastPositions = staggeredGridLayoutManager.findFirstVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }

    /**
     * 刷新数据后 用于保持吸顶状态
     */
    public void keepXidingStatus() {
        if (isXidingStatus) {
            if (position < recyclerView.getChildCount())
                recyclerView.scrollToPosition(position);
        }
    }
}

