package com.andios.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by YANGZHEWEN 2017/11/21.
 * 数据库操作类
 */

public class DataOperate {
    /**
     * 插入数据
     * @param context 上下文对象
     * @param id id号码
     * @param iswork 签到状态
     * @param project_name 项目名称
     * @param date 签到时间
     * @param details 签到时添加的备注信息
     */
    public void insert(Context context,int id, String iswork,String project_name,String date, String details){

        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(HistoryHelper.ID,id);
        cv.put(HistoryHelper.IsGoToWork,iswork);
        cv.put(HistoryHelper.PROJECT_NAME,project_name);
        cv.put(HistoryHelper.TIME,date);
        cv.put(HistoryHelper.DETAILS,details);
        sqLiteDatabase.insert(HistoryHelper.TABLE_NAME,null,cv);
    }

    /**
     * 查询表中的数据
     * @param context 上下文对象
     * @return
     */
    public Cursor select(Context context){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query(HistoryHelper.TABLE_NAME,null,null,
                null,null,null,null);
        return cursor;
    }

    /**
     * 删除数据
     * @param context
     * @param position
     */
    public void delete(Context context,int position){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        String where =HistoryHelper.ID+ " = ?";
        String[] whereValue ={ Integer.toString(position) };
        sqLiteDatabase.delete(HistoryHelper.TABLE_NAME,where,whereValue);
    }

    /**
     * 向 HistoryHelper.TABLE_NAME表中插入数据
     * @param context 上下文对象
     * @param id id号码
     * @param local 地点信息
     */
    public void insertLocal(Context context,int id ,String local){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HistoryHelper.ID,id);
        cv.put(HistoryHelper.Local,local);
        sqLiteDatabase.insert(HistoryHelper.TABLE_NAME,null,cv);
    }
}
