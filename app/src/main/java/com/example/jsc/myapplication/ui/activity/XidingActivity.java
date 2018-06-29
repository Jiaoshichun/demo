package com.example.jsc.myapplication.ui.activity;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.common.UIUtils;
import com.example.jsc.myapplication.ui.adapter.XidingAdapter;
import com.example.jsc.myapplication.utils.XidingUtils;

public class XidingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private XidingAdapter adapter;
    private FrameLayout fLayoutRoot;
    private XidingUtils xidingUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        recyclerView = findViewById(R.id.recycler);
        fLayoutRoot = findViewById(R.id.flayout_root);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom=UIUtils.dip2px(5);
            }
        });
        adapter = new XidingAdapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        View view = UIUtils.getView(R.layout.view_xiding);
        xidingUtils = new XidingUtils(recyclerView, fLayoutRoot, view, UIUtils.dip2px(40));
    }
    public void onBindData(ViewGroup viewGroup,int position){
        xidingUtils.onBindXidingItemView(viewGroup,position);
    }
}
