package com.andios.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 2017/11/21.
 */

public class DataOperate {
    public void insert(Context context,int id, String project_name, String date, String details){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(HistoryHelper.ID,id);
        cv.put(HistoryHelper.PROJECT_NANE,project_name);
        cv.put(HistoryHelper.TIME,date);
        cv.put(HistoryHelper.DETAILS,details);
        sqLiteDatabase.insert(HistoryHelper.TABLE_NAME,null,cv);
    }
    public Cursor select(Context context){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query(HistoryHelper.TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }
}
