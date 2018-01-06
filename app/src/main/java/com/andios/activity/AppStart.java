package com.andios.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.andios.util.Constants;
import com.andios.util.Shared;
import com.andios.util.SharedHelper;

import java.util.Map;

/**
 * Created by YangZheWen on 2017/10/23.
 * App启动动画
 */

public class AppStart extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View view = View.inflate(AppStart.this,R.layout.start_app, null);
        setContentView(view);
        alphaAnimation(view);
    }

    /**
     * 渐变动画的实现
     */
    private void alphaAnimation(View view){
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f); //渐变展示启动屏
        aa.setDuration(3000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {}
            @Override
            public void onAnimationStart(Animation arg0) {}
        });
    }
    /**
     * 实现页面跳转
     */
    protected void redirectTo() {
        Intent intent=new Intent();
        SharedHelper sharedHelper= Shared.getInstance(AppStart.this);
        Map<String,String>map=sharedHelper.read();
        if (!map.get("username").equals("")&&!map.get("password").equals("")){
            intent.setClass(this,MainActivity.class);
        }else intent.setClass(this,Login.class);
        startActivity(intent);
        finish();
    }
}
