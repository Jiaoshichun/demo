package com.example.jsc.myapplication.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.common.UIUtils;
import com.example.jsc.myapplication.ui.activity.XidingActivity;
import com.example.jsc.myapplication.utils.imageloader.JImageLoader;

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
            JImageLoader.getInstance()
                    .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541159508824&di=e8ee61cfb1d514ed112554b039011ae6&imgtype=0&src=http%3A%2F%2Fs1.sinaimg.cn%2Fmw690%2F0062ywFUgy6Y2pCcTde70%26690")
                    .centerCrop().angle(UIUtils.dip2px(20)).into(iv);
        }
    }
}
