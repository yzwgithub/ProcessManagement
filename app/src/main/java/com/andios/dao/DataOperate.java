package com.andios.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 2017/11/21.
 */

public class DataOperate {
    public void insert(Context context,int id, String iswork,String project_name,String local, String date, String details){

        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(HistoryHelper.ID,id);
        cv.put(HistoryHelper.IsGoToWork,iswork);
        cv.put(HistoryHelper.PROJECT_NAME,project_name);
        cv.put(HistoryHelper.Local,local);
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
    public void delete(Context context,int position){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        String where =HistoryHelper.ID+ " = ?";
        String[] whereValue ={ Integer.toString(position) };
        sqLiteDatabase.delete(HistoryHelper.TABLE_NAME,where,whereValue);
    }
    public void update(Context context,int id, String iswork,String project_name,String local, String date, String details){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        String where =HistoryHelper.ID+ " = ?";
        String[] whereValue ={ Integer.toString(id) };
        ContentValues cv = new ContentValues();
        cv.put(HistoryHelper.ID,id);
        cv.put(HistoryHelper.IsGoToWork,iswork);
        cv.put(HistoryHelper.PROJECT_NAME,project_name);
        //cv.put(HistoryHelper.Local,local);
        cv.put(HistoryHelper.TIME,date);
        cv.put(HistoryHelper.DETAILS,details);
        sqLiteDatabase.update(HistoryHelper.TABLE_NAME,cv,where,whereValue);
    }
}
