package com.example.jsc.myapplication.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jsc.myapplication.BaseActivity;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.ui.view.transformer.TabletTransformer;

public class ViewPagerActivity extends BaseActivity {
    private final String TAG = "ViewPagerActivity";
    private ViewPager vp;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        overridePendingTransition(-1,-1);
        vp = findViewById(R.id.vp);
        myAdapter = new MyAdapter(this);
        vp.setAdapter(myAdapter);
//        vp.setPageMargin(25);
        vp.setOffscreenPageLimit(3);
//        vp.setPageMarginDrawable(R.mipmap.head);
        vp.setPageTransformer(true,new TabletTransformer());
    }

    static class MyAdapter extends PagerAdapter {
        private Context context;
        private final int[] ids = {R.mipmap.one, R.mipmap.two, R.mipmap.three};

        MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(ids[position]);
            imageView.setTag(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(-1,-1);
    }
}
