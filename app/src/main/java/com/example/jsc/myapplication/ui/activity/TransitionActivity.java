package com.example.jsc.myapplication.ui.activity;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jsc.myapplication.BaseActivity;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.ui.adapter.TransitionAdapter;

import java.util.ArrayList;

public class TransitionActivity extends BaseActivity implements View.OnClickListener {

    private ListView list;
    private TransitionAdapter adapter;
    private ArrayList<Integer> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        list = (ListView) findViewById(R.id.list);
        adapter = new TransitionAdapter(mData, this);
        list.setAdapter(adapter);
        for (int i = 0; i < 20; i++) {
            mData.add(i);
        }
        adapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View[] views = {view.findViewById(R.id.head), view.findViewById(R.id.name)};
                ArrayList<ViewAtt> viewAtts = new ArrayList<ViewAtt>();
                for (View v : views) {
                    ViewAtt viewAtt = new ViewAtt();
                    viewAtt.id = v.getId();
                    int[] ints = new int[2];
                    v.getLocationOnScreen(ints);
                    viewAtt.x = ints[0];
                    viewAtt.y = ints[1];
                    viewAtt.width = v.getMeasuredWidth();
                    viewAtt.height = v.getMeasuredHeight();
                    viewAtts.add(viewAtt);
                }
                Intent intent = new Intent(TransitionActivity.this, DetailActivity.class);
                intent.putParcelableArrayListExtra("view", viewAtts);
                intent.putExtra("name", "name" + position);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        findViewById(R.id.subtraction).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("TransitionActivity", "firstVisibleItem:" + list.getFirstVisiblePosition() + "  paddingtop值:" + (list.getChildAt(0) == null ? "null" : list.getChildAt(0).getPaddingTop()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subtraction:
                mData.remove(0);
                adapter.notifyDataSetChanged();
                break;
            case R.id.add:
                mData.add(154);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    public static class ViewAtt implements Parcelable {
        public int id;
        public float x;
        public float y;
        public float width;
        public float height;

        public ViewAtt() {
        }

        protected ViewAtt(Parcel in) {
            id = in.readInt();
            x = in.readFloat();
            y = in.readFloat();
            width = in.readFloat();
            height = in.readFloat();
        }

        public static final Creator<ViewAtt> CREATOR = new Creator<ViewAtt>() {
            @Override
            public ViewAtt createFromParcel(Parcel in) {
                return new ViewAtt(in);
            }

            @Override
            public ViewAtt[] newArray(int size) {
                return new ViewAtt[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeFloat(x);
            dest.writeFloat(y);
            dest.writeFloat(width);
            dest.writeFloat(height);
        }
    }
}
