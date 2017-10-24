package com.andios.processmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private EditText enter_username,enter_password;
    private Button login_button;
    private TextView forget_password,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        initView();
        enter_username.setOnClickListener(new onClick());
        enter_password.setOnClickListener(new onClick());
        login_button.setOnClickListener(new onClick());
        forget_password.setOnClickListener(new onClick());
        register.setOnClickListener(new onClick());
    }
    private void initView(){
        enter_username= (EditText) findViewById(R.id.login_enter_username);
        enter_password= (EditText) findViewById(R.id.login_enter_password);
        login_button= (Button) findViewById(R.id.login_button);
        forget_password= (TextView) findViewById(R.id.forget_password);
        register= (TextView) findViewById(R.id.register);
    }
    class onClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_enter_username:
                case R.id.login_enter_password:
                case R.id.login_button:
                    pageRedirect(Login.this,Register.class);
                    break;
                case R.id.forget_password:
                case R.id.register:
            }
        }
    }
    private void pageRedirect(Context context, Class<?>newClass){
        Intent intent=new Intent(context,newClass);
        startActivity(intent);
    }
}
