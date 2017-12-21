package com.example.jsc.myapplication.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jsc on 2017/11/6.
 */

public class DBHelp extends SQLiteOpenHelper{
    // 数据库名
    private static final String DATABASE_NAME = "finch.db";
    public static final String TABLE_USER="user";
    public static final String TABLE_JOB="job";
    private static final int DATABASE_VERSION = 1;
    public DBHelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USER+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
         db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_JOB+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, job TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
