package com.example.jsc.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsc on 2017/6/8.
 */

public class TransitionAdapter extends MyListView.SwitchBaseAdapter {
    private ArrayList<Integer> mData ;
    private Context context;

    public TransitionAdapter( ArrayList<Integer> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }


    @Override
    public int getCount() {
        Log.e("TransitionAdapter","getCount");
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_transition, null);
        TextView name = (TextView) inflate.findViewById(R.id.name);
        name.setText("name" + position);
        Log.e("TransitionAdapter","getView");
        return inflate;
    }

    @Override
    public List getData() {
        return null;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.e("TransitionAdapter","notifyDataSetChanged");
    }
}
