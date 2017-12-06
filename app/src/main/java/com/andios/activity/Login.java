package com.andios.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.andios.util.SharedHelper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Login extends AppCompatActivity {
    private Spinner spinner;
    private EditText enter_username,enter_password;
    private Button login_button;
    private TextView forget_password;
    private CheckBox checkBox1;
    private SharedHelper sharedHelper;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        initView();
        context=getApplicationContext();
        sharedHelper=new SharedHelper(context);
        login_button.setOnClickListener(new onClickListener());
        forget_password.setOnClickListener(new onClickListener());
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String username=enter_username.getText().toString();
                String password=enter_password.getText().toString();
                if (isChecked)
                    sharedHelper.save(username, password);
                else sharedHelper.save(null, null);
            }
        });
    }
    private void initView(){
        spinner= (Spinner) findViewById(R.id.role);
        enter_username= (EditText) findViewById(R.id.login_enter_username);
        enter_password= (EditText) findViewById(R.id.login_enter_password);
        login_button= (Button) findViewById(R.id.login_button);
        forget_password= (TextView) findViewById(R.id.forget_password);
        checkBox1= (CheckBox) findViewById(R.id.remember);
    }
    class onClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_button:
                    String role=spinner.getSelectedItem().toString();
                    String username=enter_username.getText().toString();
                    String password=enter_password.getText().toString();
                    if (checkBox1.isChecked())
                        sharedHelper.save(username, password);
                    else sharedHelper.save(null, null);
                    Login("http://192.168.1.138:8080/user/login?");
                    break;
                case R.id.forget_password:
                    break;
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String>data=sharedHelper.read();
        enter_username.setText(data.get("username"));
        enter_password.setText(data.get("password"));
        if (data.get("username").equals("")||data.get("password").equals("")){
            checkBox1.setChecked(false);
        }else checkBox1.setChecked(true);
    }
    private void pageRedirect(Context context, Class<?>newClass){
        Intent intent=new Intent(context,newClass);
        startActivity(intent);
    }
    private void Login(String url){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                pageRedirect(Login.this,MainActivity.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Login.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("userName","123");
                map.put("password","123");
                map.put("role","0");
                return map;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
}
