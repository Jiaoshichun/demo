package com.example.kayo.myapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kayo.myapplication.R;
import com.example.kayo.myapplication.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单展开动画
 * 1.确定 中间菜单按钮半径 {@link #mMenuRadius}，菜单条目的半径 {@link #mItemRadius}，菜单与条目的间距 {@link #mCirCleDistance}
 * 可通过调用{@link #setDistance(int, int, int)}来进行设置（必须在设置条目数据之前调用）
 * 2.设置菜单条目列表数据 {@link #setItemDataList(List)}
 * 3 设置条目点击事件 {@link #setItemClickListener(ItemClickListener)}
 */
public class VoteView extends FrameLayout {
    //条目图标  目前只有五个
    private final int[] ICONS = {R.mipmap.bg_vote1, R.mipmap.bg_vote2, R.mipmap.bg_vote3, R.mipmap.bg_vote4, R.mipmap.bg_vote5};
    private List<FrameLayout> itemViewList = new ArrayList<>();//条目的view
    private List<String> itemDataList = new ArrayList<>();//菜单条目内容

    private boolean isOpen = true;
    private boolean isLoadingAnim = false;
    private int mMenuRadius = UIUtils.dip2px(39.5f);//菜单半径
    private int mItemRadius = UIUtils.dip2px(17f);//条目半径
    private int mCirCleDistance = UIUtils.dip2px(10);//菜单按钮和条目按钮中间的间距


    private int mRadius = mMenuRadius + mItemRadius + mCirCleDistance;//展开时 条目中心与菜单中心的位置
    private int mWidth = mCirCleDistance + 2 * mMenuRadius + 2 * mItemRadius;
    private int mHeight = 2 * mCirCleDistance + 2 * mMenuRadius + 4 * mItemRadius;
    private ImageView menuView;
    private ItemClickListener mItemClickListener;

    public VoteView(@NonNull Context context) {
        this(context, null);
    }

    public VoteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        menuView = new ImageView(context);
        menuView.setImageResource(R.mipmap.icon_tab);
        menuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

    }

    /**
     * 设置距离  要在设置数据之前调用
     *
     * @param menuRadius     菜单按钮半径
     * @param itemRadius     条目按钮半径
     * @param circleDistance 菜单按钮和条目按钮的距离
     */
    public void setDistance(int menuRadius, int itemRadius, int circleDistance) {
        this.mMenuRadius = menuRadius;
        this.mItemRadius = itemRadius;
        this.mCirCleDistance = circleDistance;
        this.mRadius = mMenuRadius + mItemRadius + mCirCleDistance;//展开时 条目中心与菜单中心的位置
        this.mWidth = mCirCleDistance + 2 * mMenuRadius + 2 * mItemRadius;
        this.mHeight = 2 * mCirCleDistance + 2 * mMenuRadius + 4 * mItemRadius;
    }

    /**
     * 设置菜单图标
     *
     * @param id 菜单图片的资源id
     */
    public void setMenuIcon(@DrawableRes int id) {
        menuView.setImageResource(id);
    }

    /**
     * 设置菜单的图标
     *
     * @param drawable 图标
     */
    public void setMenuIcon(Drawable drawable) {
        menuView.setImageDrawable(drawable);
    }


    /**
     * 开启动画
     */
    private void startAnim() {
        if (isLoadingAnim) return;
        isOpen = !isOpen;
        isLoadingAnim = true;
        int start = isOpen ? 0 : 1;
        int end = isOpen ? 1 : 0;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                for (int i = 0; i < itemViewList.size(); i++) {
                    FrameLayout frameLayout = itemViewList.get(i);
                    double radian = (double) frameLayout.getTag();
                    frameLayout.setScaleX(value);
                    frameLayout.setScaleY(value);
                    frameLayout.setAlpha(value);
                    frameLayout.setX(getLeft((int) (mRadius * value + 0.5), radian));
                    frameLayout.setY(getTop((int) (mRadius * value + 0.5), radian));
                }
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isLoadingAnim = false;
            }
        });
        valueAnimator.start();


    }

    /**
     * 生成条目view
     *
     * @param pos    条目的位置
     * @param radian view角度
     */
    private void createItemView(final int pos, double radian) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        LayoutParams params = new LayoutParams(2 * mItemRadius, 2 * mItemRadius);
        params.topMargin = getTop(isOpen ? mRadius : 0, radian);
        params.leftMargin = getLeft(isOpen ? mRadius : 0, radian);
        frameLayout.setBackgroundResource(ICONS[pos % ICONS.length]);

        frameLayout.setTag(radian);
        addView(frameLayout, params);
        itemViewList.add(frameLayout);

        if (pos < itemDataList.size()) {
            TextView textView = new TextView(getContext());
            textView.setIncludeFontPadding(false);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIUtils.dip2px(12));
            textView.setTextColor(Color.WHITE);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            textView.setGravity(Gravity.CENTER);
            final String title = itemDataList.get(pos);
            textView.setText(title);
            frameLayout.addView(textView, layoutParams);

            frameLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(pos);
                    }
                }
            });
        }


    }

    /**
     * 获取上边距
     *
     * @param radius 条目中心与菜单中心的位置
     * @param radian 条目的角度
     * @return 返回条目距离父控件上边距
     */
    private int getTop(int radius, double radian) {
        return (int) (mHeight / 2 - mItemRadius - radius * Math.cos(radian) + 0.5f);
    }

    /**
     * 获取左边距
     *
     * @param radius 条目中心与菜单中心的位置
     * @param radian 条目的角度
     * @return 返回条目距离父控件左边距
     */
    private int getLeft(int radius, double radian) {
        return (int) (mMenuRadius - mItemRadius + radius * Math.sin(radian) + 0.5f);
    }

    /**
     * 设置条目的数量
     *
     * @param items 条目数据
     */
    public void setItemDataList(List<String> items) {
        removeAllViews();
        itemViewList.clear();
        itemDataList.clear();
        itemDataList.addAll(items);
        ArrayList<Double> list = new ArrayList<>();
        switch (itemDataList.size()) {
            case 2:
                list.add(Math.PI / 3);
                list.add(Math.PI / 3 * 2);
                break;
            case 3:
                list.add(Math.PI / 4);
                list.add(Math.PI / 2);
                list.add(Math.PI / 4 * 3);
                break;
            case 4:
                list.add(Math.PI / 5);
                list.add(Math.PI / 5 * 2);
                list.add(Math.PI / 5 * 3);
                list.add(Math.PI / 5 * 4);
                break;
            case 5:
                list.add(0d);
                list.add(Math.PI / 4);
                list.add(Math.PI / 2);
                list.add(Math.PI / 4 * 3);
                list.add(Math.PI);
                break;
        }
        for (int i = 0; i < list.size(); i++) {
            createItemView(i, list.get(i));
        }
        LayoutParams params = new LayoutParams(2 * mMenuRadius, 2 * mMenuRadius);
        params.gravity = Gravity.CENTER_VERTICAL;
        addView(menuView, params);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int pos);
    }
}
