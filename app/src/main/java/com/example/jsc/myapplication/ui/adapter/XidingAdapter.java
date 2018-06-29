package com.example.jsc.myapplication.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.ui.activity.XidingActivity;

public class XidingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int[] ids = {R.mipmap.one, R.mipmap.two, R.mipmap.three};

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_xiding_item, parent, false));
        return new XidingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_xiding, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==0){
            ((ItemHolder)holder).onBindData(ids[(int) (Math.random()*3)]);
        }else {
            ((XidingHolder)holder).onBindData();
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 4) return 1;
        return 0;
    }

    static class XidingHolder extends RecyclerView.ViewHolder {

        private final FrameLayout fLayout;

        public XidingHolder(View itemView) {
            super(itemView);
            fLayout = itemView.findViewById(R.id.flayout);
        }
        public void onBindData(){
            ((XidingActivity)itemView.getContext()).onBindData(fLayout,getAdapterPosition());
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;

        public ItemHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }

        public void onBindData(int ids) {
            Glide.with(itemView).load(ids).into(iv);
        }
    }
}
