package com.example.jsc.myapplication.ui.view.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 带有缩放和透明度的vp切换动画
 * Created by jsc on 2018/1/5.
 */

public class AlphaAndScalePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {

        float scale;
        float alpha;
        if (position < -1) {
            scale = 0.8f;
            alpha = 0.5f;
        } else if (position <= 1) {
            if (position < 0) {
                scale = 1 + position * 0.2f;
                alpha = 1 + position * 0.5f;
            } else {
                scale = 1 - position * 0.2f;
                alpha = 1 - position * 0.5f;
            }
        } else {
            scale = 0.8f;
            alpha = 0.5f;
        }
        if (position < 0) {
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight() / 2);
        } else {
            page.setPivotX(0);
            page.setPivotY(page.getHeight() / 2);
        }
        page.setScaleX(scale);
        page.setScaleY(scale);
        page.setAlpha(alpha);
    }
}
