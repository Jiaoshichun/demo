package com.example.jsc.myapplication.ui.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsc.myapplication.BaseActivity;
import com.example.jsc.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recycler;
    private MyAdapter adapter;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initView();
        initData();
    }

    private void initView() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new MyAdapter(this, mData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new MyItemDecoration(10,true));

        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new RecyclerView.ItemAnimator() {
            @Override
            public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public void runPendingAnimations() {

            }

            @Override
            public void endAnimation(RecyclerView.ViewHolder item) {

            }

            @Override
            public void endAnimations() {

            }

            @Override
            public boolean isRunning() {
                return false;
            }
        });
        adapter.setOnItemClickListener(position -> {
                    String s = mData.get(position);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
        );
        findViewById(R.id.add).setOnClickListener(this);
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            mData.add("条目" + i);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                mData.add(7, "添加条目");
                adapter.notifyItemInserted(7);
                break;
            default:
                break;
        }
    }


    public static class MyItemDecoration extends RecyclerView.ItemDecoration {
        Rect r = new Rect();
        private Paint paint = new Paint();
        private int divHeight;
        private boolean isVertical;

        public MyItemDecoration(int divHeight, boolean isVertical) {
            this.divHeight = divHeight;
            paint.setColor(Color.WHITE);
            this.isVertical = isVertical;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (isVertical)
                drawHorizontal(c, parent);
            else
                drawVertical(c, parent);
        }

        private void drawVertical(Canvas c, RecyclerView parent) {
            int top = parent.getPaddingTop() + parent.getTop();
            int bottom = parent.getBottom() - parent.getPaddingBottom();
            for (int i = 0; i < parent.getChildCount(); i++) {
                View childAt = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + divHeight;
                r.set(left, top, right, bottom);
                c.drawRect(r, paint);
            }
        }

        private void drawHorizontal(Canvas c, RecyclerView parent) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < parent.getChildCount(); i++) {
                View childAt = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + divHeight;
                r.set(left, top, right, bottom);
                c.drawRect(r, paint);
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (isVertical)
                outRect.set(0, 0, 0, divHeight * 2);
            else
                outRect.set(0, 0, divHeight * 2, 0);
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> mData;
        private LayoutInflater layoutInflater;
        private OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public MyAdapter(Context context, @Nullable List<String> mData) {
            this.mData = mData;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e("recycler", "onCreateViewHolder  viewType:" + viewType);
            View itemView = layoutInflater.inflate(R.layout.adapter_recycler, parent, false);
            itemView.setOnClickListener(v -> {
                        if (listener != null) listener.onItemClick(mData.indexOf(v.getTag()));
                    }
            );
            MyViewHolder myViewHolder = new MyViewHolder(itemView);
            myViewHolder.delete.setOnClickListener(v -> {
                        int position = mData.indexOf(v.getTag());
                        mData.remove(position);
                        notifyItemRemoved(position);
                    }
            );

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Log.e("recycler", "onBindViewHolder  position:" + position);
            String s = mData.get(position);
            holder.txt.setText(s);
            holder.itemView.setTag(s);
            holder.delete.setTag(s);

        }

        @Override
        public int getItemCount() {
            Log.d("recycler", "getItemCount " + mData.size());
            return mData.size();
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt;
        public TextView delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txt);
            delete = (TextView) itemView.findViewById(R.id.delete);

        }
    }
}
