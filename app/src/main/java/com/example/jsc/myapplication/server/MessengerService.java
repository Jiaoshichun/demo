package com.example.jsc.myapplication.server;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import static com.example.jsc.myapplication.ui.activity.MessengerActivity.WHAT_CLIENT;

public class MessengerService extends Service {
    private final String TAG = "MessengerService";
    private final Handler mServerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_CLIENT:
                    Messenger replyTo = msg.replyTo;
                    Log.e(TAG, "我收到了:" + msg.getData().getString("data"));
                    Message obtain = Message.obtain(mServerHandler, WHAT_CLIENT);
                    Bundle data = new Bundle();
                    data.putString("data", "怎么不敢");
                    obtain.setData(data);
                    try {
                        replyTo.send(obtain);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private final Messenger mMessenger= new Messenger(mServerHandler);

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind方法");
        return mMessenger.getBinder();
    }
}
