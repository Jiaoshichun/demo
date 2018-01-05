package com.example.jsc.myapplication.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.jsc.myapplication.R;

public class ViewPagerActivity extends AppCompatActivity {
    private final String TAG = "ViewPagerActivity";
    private ViewPager vp;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        vp = findViewById(R.id.vp);
        myAdapter = new MyAdapter(this);
        vp.setAdapter(myAdapter);
//        vp.setPageMargin(25);
        vp.setOffscreenPageLimit(3);
//        vp.setPageMarginDrawable(R.mipmap.head);
        vp.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                Log.e(TAG, "transformPage的page界面:" + page.getTag() + " position：" + position);
//                int width = page.getWidth();
                //我们给不同状态的页面设置不同的效果
                //通过position的值来分辨页面所处于的状态
//                if (position < -1) {//滑出的页面
//                    page.setScrollX((int) (width * 0.75 * -1));
//                } else if (position <= 1) {//[-1,1]
//                    if (position < 0) {//[-1,0]
//                        page.setScrollX((int) (width * 0.75 * position));
//                    } else {//[0,1]
//                        page.setScrollX((int) (width * 0.75 * position));
//                    }
//                } else {//即将滑入的页面
//                    page.setScrollX((int) (width * 0.75));
//                }
                int width = page.getWidth();
                float scale;
                float alpha;
                if (position < -1) {
                    scale = 0.6f;

                    alpha = 0.5f;
                } else if (position <= 1) {
                    if (position < 0) {
                        scale = 1 + position * 0.4f;
                        alpha = 1 + position * 0.5f;
                    } else {
                        scale = 1 - position * 0.4f;
                        alpha = 1 - position * 0.5f;
                    }
                } else {
                    scale = 0.6f;
                    alpha = 0.5f;
                }
//                if (position < 0)
//                    page.setTranslationX(width - width * scale);
//                else
//                    page.setTranslationX(width - width * scale);
                page.setScaleX(scale);
                page.setScaleY(scale);
                page.setAlpha(alpha);
            }
        });
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
}
