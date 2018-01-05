package com.andios.util;

/**
 * Created by ASUS on 2018/1/5.
 */

public class ButtonUtil {

    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;

    public static boolean isFastClick(){
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime)>=MIN_CLICK_DELAY_TIME){
            flag=true;
        }
        lastClickTime=curClickTime;
        return flag;
    }
}
