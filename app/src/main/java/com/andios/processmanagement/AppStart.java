package com.andios.processmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by ASUS on 2017/10/23.
 * App启动动画
 */

public class AppStart extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View view = View.inflate(this,R.layout.start_app, null);
        setContentView(view);
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
    protected void redirectTo() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
