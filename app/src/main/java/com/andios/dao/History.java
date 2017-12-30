package com.andios.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by YANGZHEWEN 2017/11/21.
 * 数据库创建
 */

public class History extends SQLiteOpenHelper {
    /**
     * 构造方法
     * @param context
     */
    public History(Context context) {
        super(context, HistoryHelper.DATABASE_NAME, null, HistoryHelper.DATABASE_VERSION);
    }

    /**
     * 重写的onCreate方法
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+HistoryHelper.TABLE_NAME+
                "("+
                HistoryHelper.ID+
                " INTEGER primary key autoincrement,"+
                HistoryHelper.PROJECT_NAME+" text,"+
                HistoryHelper.IsGoToWork+" text,"+
                HistoryHelper.TIME+" text,"+
                HistoryHelper.DETAILS+
                " text);";
        db.execSQL(sql);
//        String p_id="CREATE TABLE "+HistoryHelper.TABLE_NAME1+
//                "("+
//                HistoryHelper.ID+
//                " INTEGER primary key autoincrement,"+
//                HistoryHelper.PROJECT_NAME+
//                " text);";
//        db.execSQL(sql);
//        db.execSQL(p_id);
    }

    /**
     * 重写的onUpgrade方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS"+HistoryHelper.TABLE_NAME;
//        String p_id="DROP TABLE IF EXISTS"+HistoryHelper.TABLE_NAME1;
        db.execSQL(sql);
//        db.execSQL(p_id);
    }
}
