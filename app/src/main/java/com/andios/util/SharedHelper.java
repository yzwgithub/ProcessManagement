package com.andios.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2017/8/31.
 */

public class SharedHelper {
    private Context mContext;

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }


    //定义一个保存数据的方法
    public void save(String username, String password) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        return data;
    }
}
