package com.example.jsc.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jsc.myapplication.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {
    private final String TAG = "RxjavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

    }

    public void bt1(View v) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe" + 1 + "  线程:" + Thread.currentThread().getName());
                e.onNext(1);
                Log.e(TAG, "subscribe" + 2 + "  线程:" + Thread.currentThread().getName());
                e.onNext(2);
                Log.e(TAG, "subscribe" + 3 + "  线程:" + Thread.currentThread().getName());
                e.onNext(3);
                Log.e(TAG, "subscribe" + 4 + "  线程:" + Thread.currentThread().getName());
                e.onNext(4);

            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept  主线程后" + integer + "  线程:" + Thread.currentThread().getName());
            }
        }).observeOn(Schedulers.newThread()).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept  newThread后" + integer + "  线程:" + Thread.currentThread().getName());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept  mainThread后" + integer + "  线程:" + Thread.currentThread().getName());
            }
        });
    }

    public void bt2(View v) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe" + 1);
                e.onNext(1);
                Log.e(TAG, "subscribe" + 2);
                e.onNext(2);
                Log.e(TAG, "subscribe" + 3);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                return Observable.fromArray(integer + "", integer * 10 + "").delay(2, TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String o) throws Exception {
                Log.e(TAG, "accept" + o);
            }
        });
    }

    Subscription subscription;

    public void bt3(View v) {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                for(int i=0;i<400;i++){
                    Log.e(TAG, "subscribe" +i);
                    e.onNext(i);
                    Thread.sleep(2000);
                }
//                Log.e(TAG, "subscribe" + 1);
//                e.onNext(1);
//                Log.e(TAG, "subscribe" + 2);
//                e.onNext(2);
//                Log.e(TAG, "subscribe" + 3);
//                e.onNext(3);
//                Log.e(TAG, "subscribe" + 4);
//                e.onNext(4);
//                Log.e(TAG, "subscribe" +5);
//                e.onNext(5);
//                Log.e(TAG, "subscribe complete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, "onSubscribe");
                subscription = s;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext" + integer);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });

    }

    public void bt4(View v) {
        subscription.request(1);
    }
}
