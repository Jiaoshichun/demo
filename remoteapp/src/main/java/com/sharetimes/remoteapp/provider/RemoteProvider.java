package com.sharetimes.remoteapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class RemoteProvider extends ContentProvider {
    private final static String AUTHORITIES = "com.jsc.remote";
    private static UriMatcher uriMatcher;
    private static final int CODE_NAME = 1;
    private static final int CODE_JOB = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITIES, DBHelper.TABLE_NAME, CODE_NAME);
        uriMatcher.addURI(AUTHORITIES, DBHelper.TABLE_JOB, CODE_JOB);
    }

    private  SQLiteDatabase writableDatabase;

    public RemoteProvider() {

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
        String tableName = getTableName(uri);
        long insert = writableDatabase.insert(tableName, null, values);
        return ContentUris.withAppendedId(uri,insert);
    }

    @Override
    public boolean onCreate() {
        DBHelper dbHelper = new DBHelper(getContext());
        writableDatabase = dbHelper.getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + DBHelper.TABLE_NAME);
        writableDatabase.execSQL("INSERT INTO " + DBHelper.TABLE_NAME + " VALUES (1,'小明')");
        writableDatabase.execSQL("INSERT INTO " + DBHelper.TABLE_NAME + " VALUES (2,'小崽')");

        writableDatabase.execSQL("DELETE FROM " + DBHelper.TABLE_JOB);
        writableDatabase.execSQL("INSERT INTO " + DBHelper.TABLE_JOB + " VALUES (1,'后台')");
        writableDatabase.execSQL("INSERT INTO " + DBHelper.TABLE_JOB + " VALUES (2,'前端')");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
       return writableDatabase.query(getTableName(uri),projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String getTableName(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case CODE_JOB:
                return DBHelper.TABLE_JOB;
            case CODE_NAME:
                return DBHelper.TABLE_NAME;
        }
        return null;
    }
}
