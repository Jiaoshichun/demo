package com.example.jsc.myapplication.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.example.jsc.myapplication.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private View head;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final ArrayList<TransitionActivity.ViewAtt> viewattrs = intent.getParcelableArrayListExtra("view");
        String name = intent.getStringExtra("name");
        head = findViewById(R.id.head);
        this.name = (TextView) findViewById(R.id.name);
        this.name.setText(name);
        head.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                head.getViewTreeObserver().removeOnPreDrawListener(this);
                for (TransitionActivity.ViewAtt attr : viewattrs) {
                    View view = findViewById(attr.id);
                    int[] ints = new int[2];
                    view.getLocationOnScreen(ints);
                    view.setTranslationX(attr.x - ints[0]);
                    view.setTranslationY(attr.y - ints[1]);
                    view.setPivotX(0);
                    view.setPivotY(0);
                    view.setScaleX(attr.width / view.getMeasuredWidth());
                    view.setScaleY(attr.height / view.getMeasuredHeight());

                    view.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setDuration(800).setInterpolator(new AccelerateInterpolator());

                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        final ArrayList<TransitionActivity.ViewAtt> viewattrs = getIntent().getParcelableArrayListExtra("view");
        for (TransitionActivity.ViewAtt attr : viewattrs) {
            View view = findViewById(attr.id);
            int[] ints = new int[2];
            view.getLocationOnScreen(ints);
            view.setTranslationX(0);
            view.setTranslationY(0);
            view.setPivotX(0);
            view.setPivotY(0);
            view.setScaleX(1);
            view.setScaleY(1);

            view.animate().scaleX(attr.width / view.getMeasuredWidth()).scaleY(attr.height / view.getMeasuredHeight()).translationX(attr.x - ints[0]).translationY(attr.y - ints[1]).setDuration(800).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    overridePendingTransition(0, 0);
                    finish();
                }
            });

        }
    }
}
