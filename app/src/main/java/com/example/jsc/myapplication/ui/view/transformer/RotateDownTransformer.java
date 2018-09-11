package com.example.jsc.myapplication.view.transformer;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * Created by jsc on 2018/1/5.
 */

public class RotateDownTransformer implements PageTransformer {
    private static final float ROT_MOD = -15f;
    @Override
    public void transformPage(View page, float position) {
        final float width = page.getWidth();
        final float height = page.getHeight();
        final float rotation = ROT_MOD * position * -1.25f;

        page.setPivotX(width * 0.5f);
        page.setPivotY(height);
        page.setRotation(rotation);
    }
}
