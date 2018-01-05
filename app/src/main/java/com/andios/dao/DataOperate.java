package com.andios.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andios.util.Constants;
import com.andios.util.SharedHelper;

import java.util.Map;

/**
 * Created by YangZheWen 2017/11/21.
 * 数据库操作类
 */

public class DataOperate {
    /**
     * 插入数据
     * @param context 上下文对象
     * //@param id id号码
     * @param iswork 签到状态
     * @param project_name 项目名称
     * @param date 签到时间
     * @param details 签到时添加的备注信息
     */
    public void insert(Context context/*,int id*/, String iswork,String project_name,String date, String details){

        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        ContentValues cv=new ContentValues();
        //cv.put(HistoryHelper.ID,id);
        cv.put(HistoryHelper.IsGoToWork,iswork);
        cv.put(HistoryHelper.PROJECT_NAME,project_name);
        cv.put(HistoryHelper.TIME,date);
        cv.put(HistoryHelper.DETAILS,details);
        sqLiteDatabase.insert(HistoryHelper.TABLE_NAME,null,cv);
        sqLiteDatabase.close();
    }

    /**
     * 查询表中的数据
     * @param context 上下文对象
     * @return
     */
    public Cursor select(Context context){
        SharedHelper sharedHelper=new SharedHelper(context);
        Map<String,String> data=sharedHelper.read();
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query(HistoryHelper.TABLE_NAME,null,/*HistoryHelper.IsGoToWork+"=?"*/null,
                /*new String [] {Constants.username}*/null,null,null,null);
        return cursor;
    }

    public Cursor select_(Context context){
        SharedHelper sharedHelper=new SharedHelper(context);
        Map<String,String> data=sharedHelper.read();
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query(HistoryHelper.TABLE_NAME,null,HistoryHelper.IsGoToWork+"=?",
                new String [] {Constants.username},null,null,null);
        return cursor;
    }

    /**
     * 删除一条数据
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
     * 清空数据
     * @param context
     */
    public void delAll(Context context){
        History history=new History(context);
        SQLiteDatabase sqLiteDatabase=history.getWritableDatabase();
        String sql="DELETE FROM "+HistoryHelper.TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
}
