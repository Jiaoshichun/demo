package com.example.jsc.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.DynamicLayout;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.bean.UserDetailBean;
import com.example.jsc.myapplication.common.TrackEventUtils;
import com.example.jsc.myapplication.common.UIUtils;
import com.example.jsc.myapplication.mvp.MvpActivity;
import com.example.jsc.myapplication.mvp.presenter.UserPresenter;
import com.example.jsc.myapplication.mvp.view.UserView;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends MvpActivity<UserPresenter, UserView> implements View.OnClickListener, UserView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TrackEventUtils.init(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);
        findViewById(R.id.btn13).setOnClickListener(this);
        findViewById(R.id.btn14).setOnClickListener(this);
        findViewById(R.id.btn15).setOnClickListener(this);
        findViewById(R.id.btn16).setOnClickListener(this);
        findViewById(R.id.btn17).setOnClickListener(this);
        ScrollView scrollView = findViewById(R.id.scrollView);
        TextView txt = findViewById(R.id.txt);
        String text = "萌新求问,非引战,刺激战场和全军突击哪个好一些?_刺激..._百度贴吧\n" +
                "2018年2月9日 - 萌新求问,非引战,刺..以前玩荒岛特训,结果觉得瞄准巨难受,今天下了个刺激战场,发现虽然还是一样的操作,但是瞄准舒服多了,群里人有人说全军突击也可以";
        txt.setText(text);
        DynamicLayout dynamicLayout = new DynamicLayout(text, txt.getPaint(), UIUtils.dip2px(200), Layout.Alignment.ALIGN_NORMAL, 0, 0, true);
        int lineCount = dynamicLayout.getLineCount();
        Log.e(TAG," dynamicLayout.getLineCount():"+ lineCount
                +"  getLineStart:"+dynamicLayout.getLineStart(lineCount-1)
                +"  getEllipsisStart:"+dynamicLayout.getEllipsisStart(lineCount-1)
                +"  getLineEnd:"+dynamicLayout.getLineEnd(lineCount-1)
                +"  getLineWidth:"+dynamicLayout.getLineWidth(lineCount-1)
                +"  getLineVisibleEnd"+dynamicLayout.getLineVisibleEnd(lineCount-1));
    }

    private void log(String tag) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            String stringBuffer = tag + "---方法调用栈" + "className:" + element.getClassName() + "--" +
                    "getFileName：" + element.getFileName() + "---" +
                    "getMethodName：" + element.getMethodName() + "---" +
                    "getLineNumber：" + element.getLineNumber();
            Log.e(TAG, stringBuffer);
        }
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                log("click");
                startActivity(new Intent(this, TransitionActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this, ImageScaleActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(this, RecyclerActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(this, XidingActivity.class));
                break;
            case R.id.btn8:
                startActivity(new Intent(this, CoordinatorActivity.class));
                break;
            case R.id.btn9:
                startActivity(new Intent(this, EditTextActivity.class));
                break;
            case R.id.btn10:
                startActivity(new Intent(this, CircleViewActivity.class));
                break;
            case R.id.btn11:
                startActivity(new Intent(this, HeBingActivity.class));
                break;
            case R.id.btn12:
                startActivity(new Intent(this, ProviderActivity.class));
                break;
            case R.id.btn13:
                Intent intent = new Intent(this, ViewPagerActivity.class);
                intent.putExtra("type", "Messenger");
                startActivity(intent);
                break;
            case R.id.btn14:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case R.id.btn15:
                startActivity(new Intent(this, WebServerActivity.class));
                break;
            case R.id.btn16:
                startActivity(new Intent(this, VoteActivity.class));
                break;
            case R.id.btn17:
                startActivity(new Intent(this, InflaterFactoryActivity.class));
                break;

        }
    }


    @Override
    public void setUserDetail(UserDetailBean bean) {

    }
}
