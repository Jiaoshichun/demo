package com.example.jsc.myapplication.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.ui.view.ChoiceView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jsc on 2017/10/10.
 */

public class HeBingAdapter extends CursorAdapter {
    HashMap<Integer,Boolean> hashMap=new HashMap<>();
    private Cursor cursor;

    public HeBingAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ChoiceView inflate = new ChoiceView(context);
        TextView name = (TextView) inflate.findViewById(R.id.name);
        name.setText(cursor.getString(2));
        return inflate;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(2));
        ChoiceView inflate = (ChoiceView) view;
        inflate.setTag(cursor.getPosition());
        inflate.setOnClickListener((v) -> {
            ((ChoiceView) v).toggle();
            hashMap.put((Integer) v.getTag(), ((ChoiceView) v).isChecked());

        });
        Boolean checked = hashMap.get(cursor.getPosition());
        if (checked != null)
            inflate.setChecked(checked);
    }

    public ArrayList<Integer> getCheckedIds() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            Boolean aBoolean = hashMap.get(i);
            if (aBoolean != null && aBoolean) {
                integers.add(i);
            }
        }
        return integers;
    }
}
