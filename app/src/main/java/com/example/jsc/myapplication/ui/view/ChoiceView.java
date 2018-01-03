package com.example.jsc.myapplication.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.example.jsc.myapplication.R;

/**
 * Created by jsc on 2017/10/10.
 */

public class ChoiceView extends LinearLayout implements Checkable {
    private CheckBox checkBox;

    public ChoiceView(Context context) {
        this(context, null);
    }

    public ChoiceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChoiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.adapter_hebing, this);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
    }

    @Override
    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return checkBox.isChecked();
    }

    @Override
    public void toggle() {
        setChecked(!checkBox.isChecked());
    }
}
