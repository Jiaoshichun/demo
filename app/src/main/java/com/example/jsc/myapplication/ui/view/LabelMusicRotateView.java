package com.example.jsc.myapplication.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.common.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class LabelMusicRotateView extends FrameLayout {
    private int maxRotateAngel = 120;//最大的旋转角度
    private int fromAngel = 130;//从什么角度开始
    private int maxNum = 5;//最多显示的数量
    private int perAngel = maxRotateAngel / maxNum;//每个图片的间隔
    private int[] iconIds = {R.mipmap.music2, R.mipmap.music1};//旋转的音符
    private int duration = 3000;//音符旋转的时间
    private ValueAnimator musicAnimator;//音符的值动画
    private List<ImageView> musicIconList = new ArrayList<>();//旋转音符的集合
    private ImageView imgCover;
    private ValueAnimator coverAnimator;//封面的旋转动画


    public LabelMusicRotateView(@NonNull Context context) {
        this(context, null);
    }

    public LabelMusicRotateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelMusicRotateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_label_music_roate, this);
        imgCover = findViewById(R.id.img_cover);
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(UIUtils.dip2px(65),
                    UIUtils.dip2px(65));
            setLayoutParams(params);
        } else {
            params.width = UIUtils.dip2px(65);
            params.height = UIUtils.dip2px(65);
        }
        setLayoutParams(params);
        for (int i = 0; i < maxNum; i++) {
            addRotateView(perAngel * i + fromAngel, iconIds[i % iconIds.length]);
        }
        musicAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(duration);
        musicAnimator.setRepeatCount(ValueAnimator.INFINITE);
        musicAnimator.setInterpolator(new LinearInterpolator());
        musicAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float values = (Float) animation.getAnimatedValue();
                for (int i = 0; i < musicIconList.size(); i++) {
                    ImageView imageView = musicIconList.get(i);
                    Integer initAngel = (Integer) imageView.getTag();
                    float angel = initAngel + values * maxRotateAngel;
                    if (angel > maxRotateAngel + fromAngel) {
                        angel = angel - maxRotateAngel;
                    }
                    updateViewPos(imageView, (int) angel);
                    setImageAlpha(imageView,angel);
                }
            }
        });
        musicAnimator.start();
        float iconDuration=360f*duration/ maxRotateAngel;
        coverAnimator = ValueAnimator.ofInt(0,360).setDuration((long) iconDuration);
        coverAnimator.setRepeatCount(ValueAnimator.INFINITE);
        coverAnimator.setInterpolator(new LinearInterpolator());
        coverAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer values = (Integer) animation.getAnimatedValue();
                imgCover.setRotation(values);

            }
        });
        coverAnimator.start();
        Glide.with(context).load(R.mipmap.head).into(new SimpleTarget<Drawable>(){
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), drawableToBitmap(resource));
                circularBitmapDrawable.setCircular(true);
                imgCover.setImageDrawable(circularBitmapDrawable);
            }
        });

    }
    public void startAnimation(){
        if(getVisibility()!= View.VISIBLE) return;
        if(!musicAnimator.isRunning()) musicAnimator.start();
        if(!coverAnimator.isRunning())coverAnimator.start();
    }
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(visibility!=View.VISIBLE){
            if(musicAnimator.isRunning()) musicAnimator.cancel();
            if(coverAnimator.isRunning())coverAnimator.cancel();
        }
    }
    public void setImgCover(String imgPath){

    }
    //根据角度计算透明度
    private void setImageAlpha(ImageView imageView, float angel) {
        //获取旋转的进度
        float v = (angel - fromAngel) / maxRotateAngel;
        if(v<0.2f){
            imageView.setAlpha(v/0.2f);
        }else if(v<0.8f){
            imageView.setAlpha(1f);
        }else {
            imageView.setAlpha((1-v)/0.2f);
        }
    }

    /**
     * 添加音符旋转view
     * @param angel  默认的角度
     * @param iconId  图标
     */
    private void addRotateView(int angel, int iconId) {
        int width = UIUtils.dip2px(11);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(iconId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setRotation(angel - 180);
        LayoutParams layoutParams = new LayoutParams(width, width);
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        addView(imageView, layoutParams);
        int centerX = UIUtils.dip2px(40);
        int centerY = UIUtils.dip2px(32.5f);
        imageView.setY((int) (centerY - UIUtils.dip2px(5.5f) + UIUtils.dip2px(34.5f) * Math.sin(Math.PI / 180 * angel) + 0.5));
        imageView.setX((int) (centerX - UIUtils.dip2px(5.5f) + UIUtils.dip2px(34.5f) * Math.cos(Math.PI / 180 * angel) + 0.5));
        imageView.setTag(angel);
        musicIconList.add(imageView);
    }

    /**
     * 更新音符的位置
     * @param imageView  音符view
     * @param angel  当前的角度
     */
    private void updateViewPos(ImageView imageView, int angel) {
        imageView.setRotation(angel - 180);
        int centerX = UIUtils.dip2px(40);
        int centerY = UIUtils.dip2px(32.5f);
        imageView.setY((int) (centerY - UIUtils.dip2px(5.5f) + UIUtils.dip2px(34.5f) * Math.sin(Math.PI / 180 * angel) + 0.5));
        imageView.setX((int) (centerX - UIUtils.dip2px(5.5f) + UIUtils.dip2px(34.5f) * Math.cos(Math.PI / 180 * angel) + 0.5));

    }
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
