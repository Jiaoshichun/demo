package com.example.jsc.myapplication.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jsc.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends AppCompatActivity {


    @BindView(R.id.translationX)
    TextView translationX;
    @BindView(R.id.translationY)
    TextView translationY;
    @BindView(R.id.scrollX)
    TextView scrollX;
    @BindView(R.id.scrollY)
    TextView scrollY;
    @BindView(R.id.rotation)
    TextView rotation;
    @BindView(R.id.rotationX)
    TextView rotationX;
    @BindView(R.id.rotationY)
    TextView rotationY;
    @BindView(R.id.scaleX)
    TextView scaleX;
    @BindView(R.id.scaleY)
    TextView scaleY;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.pivotX)
    TextView pivotX;
    @BindView(R.id.pivotY)
    TextView pivotY;
    @BindView(R.id.pivotX_add)
    TextView pivotXAdd;
    @BindView(R.id.pivotX_minus)
    TextView pivotXMinus;
    @BindView(R.id.pivotY_add)
    TextView pivotYAdd;
    @BindView(R.id.pivotY_minus)
    TextView pivotYMinus;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.reset_pivot)
    TextView resetPivot;
    @BindView(R.id.txt)
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int[] outLocation = new int[2];
                txt.getLocationInWindow(outLocation);
                txt.setText("高度:" + img.getHeight() + " 宽度:" + img.getWidth() + " X:" + img.getX() + " Y" + img.getY() + " outLocationX" + outLocation[0] + " outLocationY" + outLocation[1]);
            }
        });
    }

    @OnClick({R.id.translationX, R.id.translationY, R.id.scrollX, R.id.scrollY, R.id.rotation, R.id.rotationX, R.id.rotationY,
            R.id.scaleX, R.id.scaleY, R.id.pivotX_add, R.id.pivotX_minus, R.id.pivotY_add, R.id.pivotY_minus, R.id.reset, R.id.reset_pivot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.translationX:
                img.setTranslationX(30);
                break;
            case R.id.translationY:
                img.setTranslationY(30);
                break;
            case R.id.scrollX:
                img.setScrollX(30);
                break;
            case R.id.scrollY:
                img.setScrollY(30);
                break;
            case R.id.rotation:
                img.setRotation(30f);
                break;
            case R.id.rotationX:
                img.setRotationX(30f);
                break;
            case R.id.rotationY:
                img.setRotationY(30f);
                break;
            case R.id.scaleX:
                img.setScaleX(0.5f);
                break;
            case R.id.scaleY:
                img.setScaleY(0.5f);
                break;
            case R.id.pivotX_add:
                img.setPivotX(img.getPivotX() + img.getWidth() / 2);
                pivotX.setText(String.format("当前的pivotX:%s", img.getPivotX()));
                break;
            case R.id.pivotX_minus:
                img.setPivotX(img.getPivotX() - img.getWidth() / 2);
                pivotX.setText(String.format("当前的pivotX:%s", img.getPivotX()));
                break;
            case R.id.pivotY_add:
                img.setPivotY(img.getPivotY() + img.getHeight() / 2);
                pivotY.setText(String.format("当前的pivotX:%s", img.getPivotY()));
                break;
            case R.id.pivotY_minus:
                img.setPivotY(img.getPivotY() - img.getHeight() / 2);
                pivotY.setText(String.format("当前的pivotX:%s", img.getPivotY()));
                break;
            case R.id.reset:

                img.setScaleX(1);
                img.setScaleY(1);
                img.setScrollX(0);
                img.setScrollY(0);
                img.setTranslationX(0);
                img.setTranslationY(0);
                img.setRotation(0);
                img.setRotationX(0);
                img.setRotationY(0);

                break;
            case R.id.reset_pivot:
                img.setPivotX(0);
                img.setPivotY(0);
                pivotX.setText(String.format("当前的pivotX:%s", img.getPivotX()));
                pivotY.setText(String.format("当前的pivotX:%s", img.getPivotY()));
                break;
        }
        int[] outLocation = new int[2];
        txt.getLocationInWindow(outLocation);
        txt.setText("高度:" + img.getHeight() + " 宽度:" + img.getWidth() + " X:" + img.getX() + " Y" + img.getY() + " outLocationX" + outLocation[0] + " outLocationY" + outLocation[1]);
    }

}
