package com.example.jsc.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.net.GitHubService;
import com.example.jsc.myapplication.net.Repo;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    private TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        txtMsg = (TextView) findViewById(R.id.txtmsg);
    }

    public void btn1(View v) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        GitHubService service = retrofit.create(GitHubService.class);
        service.listRepos("octocat").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Repo>>() {
            @Override
            public void accept(@NonNull List<Repo> repos) throws Exception {
                StringBuilder stringBuilder = new StringBuilder();
                for (Repo r : repos) {
                    stringBuilder.append(r.toString());
                }
                txtMsg.setText(stringBuilder.toString());
            }
        });

    }
}
