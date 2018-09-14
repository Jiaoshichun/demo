package com.example.jsc.myapplication.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsc.myapplication.BaseActivity;
import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.common.UIUtils;
import com.example.jsc.myapplication.ui.view.RedPacketAnimView;
import com.example.jsc.myapplication.ui.view.VoteView;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt;
    private RedPacketAnimView img;
    private VoteView voteView;
    private List<String> mItemTitle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        txt = findViewById(R.id.txt);
        img = findViewById(R.id.img);
        txt.setOnClickListener(this);
        img.setOnClickListener(this);
        voteView = findViewById(R.id.voteView);
        findViewById(R.id.btn_num).setOnClickListener(this);
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) mItemTitle.add("投票");
            else mItemTitle.add("榜单");
        }
        voteView.setDistance(UIUtils.dip2px(39.5f), UIUtils.dip2px(17f), UIUtils.dip2px(10));
        voteView.setItemDataList(mItemTitle.subList(0, 2));
        voteView.setItemClickListener(new VoteView.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getContext(), mItemTitle.get(pos), Toast.LENGTH_LONG).show();
            }
        });
    }

    private int num = 1;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt:
                img.startAnim(txt);
                break;
            case R.id.img:

                break;
            case R.id.btn_num:
                if (num == 5)
                    num = 1;
                num++;
                voteView.setItemDataList(mItemTitle.subList(0, num));
                break;
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
