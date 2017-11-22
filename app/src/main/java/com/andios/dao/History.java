package com.andios.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by ASUS on 2017/11/21.
 */

public class History extends SQLiteOpenHelper {
    public History(Context context) {
        super(context, HistoryHelper.DATABASE_NAME, null, HistoryHelper.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+HistoryHelper.TABLE_NAME+
                "("+
                HistoryHelper.ID+
                " INTEGER primary key autoincrement,"+
                HistoryHelper.PROJECT_NANE+" text,"+
                HistoryHelper.TIME+" text,"+
                HistoryHelper.DETAILS+
                " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS"+HistoryHelper.TABLE_NAME;
        db.execSQL(sql);
    }
}
