package com.example.jsc.myapplication.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.jsc.myapplication.common.UIUtils;

public class RedPacketAnimView extends android.support.v7.widget.AppCompatImageView {

    private Pos pos;
    private boolean pause;
    private AnimatorSet animatorSet;
    private boolean hasPause;

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
        hasPause = false;
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
        valueAnimator1.addUpdateListener(animation -> {
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
        });
        valueAnimator1.setInterpolator(new AccelerateInterpolator());
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);
        valueAnimator2.addUpdateListener(animation -> {
            Float value = (Float) animation.getAnimatedValue();
            setTranslationY(value - pos.y);
        });
        valueAnimator2.setInterpolator(new AccelerateInterpolator());

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator1, valueAnimator2);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setPivotX(0);
                setPivotY(0);
                setAlpha(0f);
                setScaleX(1);
                setScaleY(1);
                setTranslationX(0);
                setTranslationY(0);
                setVisibility(View.INVISIBLE);
                endView.clearAnimation();

            }
        });
        animatorSet.setDuration(1000);
        valueAnimator2.setEvaluator((TypeEvaluator<Float>) (fraction, startValue, endValue) -> {
            if (fraction > 0.5 && !hasPause) {
                animatorSet.pause();
                pause = true;
                hasPause = true;
            }
            int[] screenWidthAndHeight = UIUtils.getScreenWidthAndHeight();
            return (1 - fraction) * (1 - fraction) * pos.y + 2 * fraction * (1 - fraction) * screenWidthAndHeight[1] * 0.5f + fraction * fraction * endPos.y;
        });
        animatorSet.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {

                if (pause) {
                    animatorSet.resume();
                    pause = false;
                    return;
                }
            }
        },2000);
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
