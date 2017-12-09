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
    public void save(String user_id,String username, String password,String role,String status) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_id",user_id);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("role",role);
        editor.putString("status",status);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        data.put("user_id", sp.getString("user_id", ""));
        data.put("role", sp.getString("role", ""));
        data.put("status", sp.getString("status", ""));
        return data;
    }
}
