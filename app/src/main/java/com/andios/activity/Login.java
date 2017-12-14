package com.andios.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import com.andios.util.LoginData;
import com.andios.util.SharedHelper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by YangZheWen on 2017/10/23.
 * 登录
 */
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
                String role=spinner.getSelectedItem().toString();
                String username=enter_username.getText().toString();
                String password=enter_password.getText().toString();
                if (isChecked)
                    sharedHelper.save(null, username,password,returnInt(role),null);
                else sharedHelper.save(null, null,null,"0",null);
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
            final String url="http://192.168.110.199:8080/user/login?";
            switch (view.getId()){
                case R.id.login_button:
                    String role=spinner.getSelectedItem().toString();
                    String username=enter_username.getText().toString();
                    String password=enter_password.getText().toString();
                    if (username.equals("")||password.equals("")||returnInt(role).equals("-1")) {
                        Toast.makeText(context, "请填写完整的登录信息", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (checkBox1.isChecked())
                        sharedHelper.save(null, username,password,returnInt(role),null);
                    else sharedHelper.save(null, null,null,"0",null);
                    //login(url,username,password,returnInt(role)-1);
                    Intent intent=new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
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
        if (data.get("username").equals("")||data.get("password").equals("")){
            sharedHelper.save(null,null,null,"0",null);
            checkBox1.setChecked(false);
        }else checkBox1.setChecked(true);
        String role=data.get("role");
        int Introle=Integer.parseInt(role);
        spinner.setSelection(Introle,true);
        enter_username.setText(data.get("username"));
        enter_password.setText(data.get("password"));
    }
    private String returnInt(String role){
        if (role.equals("合伙人"))
            return "1";
        if (role.equals("部门经理"))
            return "2";
        if (role.equals("项目经理"))
            return "3";
        if (role.equals("员工"))
            return "4";
        return "-1";
    }
    private void login(final String url, final String userName, final String password, final String role){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson=new Gson();
                LoginData loginData=gson.fromJson(s,LoginData.class);
                if (loginData==null){
                    Toast.makeText(Login.this, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                sharedHelper.save(loginData.getUser_id(),loginData.getUser_name(),
                        loginData.getPassword(),loginData.getRole(),loginData.getStatus());
                Toast.makeText(Login.this,loginData.getUser_id(),Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Login.this, "网络连接超时，请检查网络设置", Toast.LENGTH_SHORT).show();
                Log.i("Login","mwssage:"+volleyError.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("userName",userName);
                map.put("password",password);
                map.put("role",role);
                return map;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
}
