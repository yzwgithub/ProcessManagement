package com.andios.util;

import android.content.Context;

import com.andios.dao.DataOperate;

/**
 * Created by ASUS on 2018/1/5.
 */

public class Shared {
    private static SharedHelper sharedHelper;
    private static DataOperate dataOperate;
    public static SharedHelper getInstance(Context context){
        if (sharedHelper==null){
            sharedHelper=new SharedHelper(context);
        }
        return sharedHelper;
    }
    public static DataOperate getInstance(){
        if (dataOperate==null){
            dataOperate=new DataOperate();
        }
        return dataOperate;
    }
}
