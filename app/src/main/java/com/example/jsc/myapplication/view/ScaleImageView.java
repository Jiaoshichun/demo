package com.example.jsc.myapplication.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * Created by jsc on 2017/7/5.
 */

public class ScaleImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private final float[] floats = new float[9];
    private Matrix mMatrix = new Matrix();
    private float initScale = 1.0f;//默认缩放比例
    private ScaleGestureDetector mScaleGestureDetector;
    private final float MAX_SCALE = 4.0f;
    private int mTouchSlop;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        setOnTouchListener(this);
        super.setScaleType(ScaleType.MATRIX);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Drawable drawable = getDrawable();
        if (drawable == null) return true;
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (scaleFactor > 1.0f && scale < MAX_SCALE || (scaleFactor < 1.0f && scale > initScale)) {
            /**
             * 最大值最小值判断
             */
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > MAX_SCALE) {
                scaleFactor = MAX_SCALE / scale;
            }
            /**
             * 设置缩放比例
             */
            mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(),
                    detector.getFocusY());
            checkOverBorder();//检查是否超出边界
            setImageMatrix(mMatrix);
        }
        return true;
    }

    //检查是否超出边界
    private void checkOverBorder() {
        int width = getWidth();
        int height = getHeight();
        RectF rect = getMatrixRectF();
        float tranX = 0;
        float tranY = 0;
        if (rect.width() > width) {
            if (rect.left > 0) {
                tranX = -rect.left;
            }
            if (rect.right < width) {
                tranX = width - rect.right;
            }
        }
        if (rect.height() > height) {
            if (rect.top > 0) {
                tranY = -rect.top;
            }
            if (rect.bottom < height) {
                tranY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            tranX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            tranY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        mMatrix.postTranslate(tranX, tranY);

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    public final float getScale() {
        mMatrix.getValues(floats);
        return floats[Matrix.MSCALE_X];
    }

    private RectF getMatrixRectF() {
        Matrix matrix = mMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    private float lastX;
    private float lastY;
    private float lastPointerCount;
    private boolean isCanDrag;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        int pointerCount = event.getPointerCount();
        float x = 0;
        float y = 0;
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;
        if (lastPointerCount != pointerCount) {
            isCanDrag = false;
            lastX = x;
            lastY = y;
        }
        lastPointerCount = pointerCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float tranX = event.getX() - lastX;
                float tranY = event.getY() - lastY;
                if (!isCanDrag) {
                    isCanDrag = isCanDrag(tranX, tranY);
                }
                if (isCanDrag) {
                    RectF matrixRectF = getMatrixRectF();
                    int width = getWidth();
                    int height = getHeight();
                    if (matrixRectF.height() <= height) tranY = 0;
                    if (matrixRectF.width() <= width) tranX = 0;
                    mMatrix.postTranslate(tranX, tranY);
                    checkOverBorder();
                    setImageMatrix(mMatrix);
                }
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;
        }
        return true;
    }
    /**
     * 是否是推动行为
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy)
    {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }
    @Override
    public void onGlobalLayout() {
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        int width = getWidth();
        int height = getHeight();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicHeight > height || intrinsicWidth > width) {
            if ((intrinsicWidth * 1.0f) / intrinsicHeight > (width * 1.0f) / height) {
                initScale = width * 1.0f / intrinsicWidth;
            } else {
                initScale = height * 1.0f / intrinsicHeight;
            }
        }
        mMatrix.postTranslate((width - intrinsicWidth) / 2, (height - intrinsicHeight) / 2);
        mMatrix.postScale(initScale, initScale, width / 2, height / 2);
        setImageMatrix(mMatrix);
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

}
