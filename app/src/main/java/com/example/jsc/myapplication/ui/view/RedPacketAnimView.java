package com.example.jsc.myapplication.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

public class RedPacketAnimView extends ImageView {

    private Pos pos;

    public RedPacketAnimView(Context context) {
        this(context, null);
    }

    public RedPacketAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RedPacketAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void startAnim(View endView) {
        if (endView == null) return;
        int[] outLocation = new int[2];
        if (pos == null || pos.x == 0 || pos.y == 0) {
            pos = new Pos();
            getLocationInWindow(outLocation);
            pos.x = outLocation[0];
            pos.y = outLocation[1];
        }
        setVisibility(View.VISIBLE);

        endView.getLocationInWindow(outLocation);
        final Pos endPos = new Pos();
        endPos.x = outLocation[0];
        endPos.y = outLocation[1];
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0, 1);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float alpha;
                float scale;
                if (value < 0.2) {
                    alpha = (float) (0.4 + 0.6 * (value / 0.2));
                } else if (value < 0.8) {
                    alpha = 1;
                } else {
                    alpha = (float) ((1 - value) / 0.2);
                }
                if (value < 0.4) {
                    scale = 1 + (value / 2);
                } else if (value < 0.6) {
                    scale = 1.2f;
                } else {
                    scale = (float) (1.2 - (value - 0.6) / 2);
                }
                setTranslationX(value * (endPos.x - pos.x));
                setScaleX(scale);
                setScaleY(scale);
                setAlpha(alpha);
            }
        });
        valueAnimator1.setInterpolator(new LinearInterpolator());
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setTranslationY(value * (endPos.y - pos.y));
            }
        });
        valueAnimator2.setInterpolator(new OvershootInterpolator(20f));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator1, valueAnimator2);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setPivotX(0);
                setPivotY(0);
                setAlpha(0f);
                setScaleX(1);
                setScaleY(1);
                setVisibility(View.INVISIBLE);

            }
        });
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    public static class Pos {
        public float x;
        public float y;
        public float alpha;
        public float scale;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = getContext().getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }
}
