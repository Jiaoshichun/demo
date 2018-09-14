package com.example.jsc.myapplication.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.example.jsc.myapplication.BaseActivity;
import com.example.jsc.myapplication.R;

public class EditTextActivity extends BaseActivity {

    private EditText edt;
    private EditText edt2;
    private boolean hasXia;
    private boolean isXie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        edt = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
    }

    public void da(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            EditText editText = (EditText) currentFocus;
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editText.getTextSize() + 4);
        }
    }

    public void xiao(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            EditText editText = (EditText) currentFocus;
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editText.getTextSize() - 4);
        }
    }

    public void cu(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            EditText editText = (EditText) currentFocus;
            //添加删除线
            TextPaint paint = editText.getPaint();
            paint.setFakeBoldText(!paint.isFakeBoldText());
            int selectionStart = editText.getSelectionStart();
            editText.setText(editText.getText());
            if (selectionStart != -1) {
                editText.setSelection(selectionStart);
            }
        }
    }

    public void xie(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            EditText editText = (EditText) currentFocus;
            isXie = !isXie;
            //添加删除线
            TextPaint paint = editText.getPaint();
            paint.setTextSkewX(isXie ? -0.5f : 0);
            int selectionStart = editText.getSelectionStart();
            editText.setText(editText.getText());
            if (selectionStart != -1) {
                editText.setSelection(selectionStart);
            }
        }

    }

    public void xian(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            EditText editText = (EditText) currentFocus;
            hasXia = !hasXia;
            TextPaint paint = editText.getPaint();
            //添加删除线
            paint.setUnderlineText(hasXia);
            int selectionStart = editText.getSelectionStart();
            editText.setText(editText.getText());
            if (selectionStart != -1) {
                editText.setSelection(selectionStart);
            }
        }
    }

    public void zuo(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            ((EditText) currentFocus).setGravity(Gravity.LEFT|Gravity.TOP);
        }
    }

    public void zhong(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            ((EditText) currentFocus).setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
        }
    }

    public void you(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            ((EditText) currentFocus).setGravity(Gravity.RIGHT|Gravity.TOP);
        }
    }

    public void hei(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            ((EditText) currentFocus).setTextColor(Color.BLACK);
        }
    }

    public void hong(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            ((EditText) currentFocus).setTextColor(Color.RED);
        }
    }

    public void lan(View view) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            ((EditText) currentFocus).setTextColor(Color.BLUE);
        }
    }
}
