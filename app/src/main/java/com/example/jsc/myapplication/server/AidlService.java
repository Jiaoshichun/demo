package com.example.jsc.myapplication.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.jsc.myapplication.aidl.Book;
import com.example.jsc.myapplication.aidl.IMyAidlInterface;

public class AidlService extends Service {
    public AidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    //实现了AIDL的抽象函数
    private IMyAidlInterface.Stub mbinder = new IMyAidlInterface.Stub() {
        public Book book;

        @Override
        public void setBook(Book book) throws RemoteException {
            this.book = book;
        }

        @Override
        public Book getBook() throws RemoteException {
            return book;
        }


    };
}
