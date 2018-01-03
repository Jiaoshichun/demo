package com.example.jsc.myapplication.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jsc on 2017/9/18.
 */

public class CircleView extends View {

    private Paint progressPaint;//当前比例的画笔
    private Paint extPaint;//剩余比例的画笔
    // 半径
    private float mRadius = 100;

    //线条宽度
    private float mWidth = 5;

    //小圆的半径
    private float mCircleRadius = 15;

    //当前的进度
    private int mProgress;
    //园所在的矩形
    private RectF rectF;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint();
        progressPaint.setColor(Color.WHITE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(mWidth);

        extPaint = new Paint();
        extPaint.setAntiAlias(true);
        extPaint.setColor(Color.parseColor("#b3ffffff"));
        extPaint.setStrokeWidth(mWidth);
        extPaint.setStyle(Paint.Style.STROKE);
        rectF = new RectF(mCircleRadius, mCircleRadius, 2 * mRadius + mCircleRadius, 2 * mRadius + mCircleRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float angle = (float) mProgress / 100 * 360;
        progressPaint.setStyle(Paint.Style.STROKE);//设置空心
        //画进度圆弧
        canvas.drawArc(rectF, 90, angle, false, progressPaint);
        //画小圆点
        progressPaint.setStyle(Paint.Style.FILL);//设置填满
        float cx = (float) ((mRadius + mCircleRadius) - (mRadius * Math.sin(angle * Math.PI / 180)));
        float cy = (float) ((mRadius + mCircleRadius) + (mRadius * Math.cos(angle * Math.PI / 180)));
        if (angle != 0 && angle != 360)
            canvas.drawCircle(cx, cy, mCircleRadius, progressPaint);
        //画剩余部分的圆弧
        canvas.drawArc(rectF, 90 + angle, 360 - angle, false, extPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setProgress(int progress) {
        mProgress = progress;
//                invalidate();
        postInvalidate();
    }
}
