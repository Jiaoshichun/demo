package com.example.jsc.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.jsc.myapplication.R;

/**
 * Created by jsc on 2017/8/3.
 */

public class CustomCoordinatorFragment extends Fragment {

    private float offX;
    private float offY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_custom_coordinator, null);
        inflate.findViewById(R.id.btn).setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                    offX = event.getRawX() - layoutParams.leftMargin;
                    offY = event.getRawY() - layoutParams.topMargin;
                    break;
                case MotionEvent.ACTION_MOVE:
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                    params.leftMargin = (int) (event.getRawX() - offX);
                    params.topMargin = (int) (event.getRawY() - offY);
                    params.gravity = Gravity.NO_GRAVITY;
                    v.setLayoutParams(params);
                    break;
            }
            return true;
        });
        return inflate;
    }
}
