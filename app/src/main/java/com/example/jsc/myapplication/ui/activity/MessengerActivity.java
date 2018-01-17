package com.example.jsc.myapplication.ui.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jsc.myapplication.R;
import com.example.jsc.myapplication.aidl.Book;
import com.example.jsc.myapplication.aidl.IMyAidlInterface;
import com.example.jsc.myapplication.common.ToastUtils;
import com.example.jsc.myapplication.server.MessengerService;


public class MessengerActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int WHAT_CLIENT = 10;
    Button btnStartServer;
    Button btnSend;
    private ServiceConnection conn;
    private Messenger mServerMessenger;
    private final String TAG = "MessengerActivity";
    private IMyAidlInterface iMyAidlInterface;
    private ServiceConnection aidlConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(TAG, "onServiceConnected");
                mServerMessenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e(TAG, "onServiceDisconnected");
            }
        };
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        btnStartServer = (Button) findViewById(R.id.btn_start_server);
        btnStartServer.setOnClickListener(this);
        bindService(new Intent(this, MessengerService.class), conn, Service.BIND_AUTO_CREATE);


        Intent intent = new Intent()
                .setAction("com.application.jsc.aidl")
                .setPackage(getPackageName());
        aidlConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent, aidlConn, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_server:
                bindService(new Intent(this, MessengerService.class), conn, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_send:
                if (mServerMessenger != null) {
                    Message obtain = Message.obtain(null, WHAT_CLIENT);
                    obtain.replyTo = mClientMessenger;
                    Bundle data = new Bundle();
                    data.putString("data", "能收到吗?");
                    obtain.setData(data);
                    try {
                        mServerMessenger.send(obtain);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn1:
                if (iMyAidlInterface != null) {
                    try {
                        Book book = new Book("好名字", "教育", 10.99d);
                        iMyAidlInterface.setBook(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn2:
                if (iMyAidlInterface != null) {
                    try {
                        ToastUtils.showLong(iMyAidlInterface.getBook().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private final Handler mClientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_CLIENT:
                    Toast.makeText(getApplicationContext(), msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    Messenger mClientMessenger = new Messenger(mClientHandler);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        unbindService(aidlConn);
    }

}
