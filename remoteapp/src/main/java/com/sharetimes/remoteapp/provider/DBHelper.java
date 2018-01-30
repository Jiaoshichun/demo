package com.sharetimes.remoteapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jsc on 2018/1/30.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "remote.db";
    public static final String TABLE_NAME = "name";
    public static final String TABLE_JOB = "job";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_JOB + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,job TEXT)");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
