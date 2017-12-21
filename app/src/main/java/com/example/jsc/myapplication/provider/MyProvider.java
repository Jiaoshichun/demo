package com.example.jsc.myapplication.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.jsc.myapplication.utils.DBHelp;

import static android.content.UriMatcher.NO_MATCH;

public class MyProvider extends ContentProvider {
    public static final String AUTHORITIES = "com.jsc.myprovider";
    private static final UriMatcher mUriMatcher;
    private final static int USER_CODE = 1;
    private final static int JOB_CODE = 2;

    static {
        mUriMatcher = new UriMatcher(NO_MATCH);
        mUriMatcher.addURI(AUTHORITIES, DBHelp.TABLE_USER, USER_CODE);
        mUriMatcher.addURI(AUTHORITIES, DBHelp.TABLE_JOB, JOB_CODE);
    }

    private Context mContext;
    private SQLiteDatabase writableDatabase;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        DBHelp dbHelp = new DBHelp(mContext);
        writableDatabase = dbHelp.getWritableDatabase();
        // 初始化两个表的数据(先清空两个表,再各加入一个记录)
        writableDatabase.execSQL("delete from user");
        writableDatabase.execSQL("insert into user values(1,'Carson');");
        writableDatabase.execSQL("insert into user values(2,'Kobe');");

        writableDatabase.execSQL("delete from job");
        writableDatabase.execSQL("insert into job values(1,'Android');");
        writableDatabase.execSQL("insert into job values(2,'iOS');");

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long insert = writableDatabase.insert(getTableName(uri), null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, insert);
    }

    private String getTableName(Uri uri) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case USER_CODE:
                return DBHelp.TABLE_USER;
            case JOB_CODE:
                return DBHelp.TABLE_JOB;
        }
        return null;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return writableDatabase.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
