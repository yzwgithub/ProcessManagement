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
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.andios.util.Constants;
import com.andios.util.LoginInfo;
import com.andios.util.ProjectInfo;
import com.andios.util.SharedHelper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by YangZheWen on 2017/10/23.
 * 登录
 */
public class Login extends AppCompatActivity {
    private Spinner spinner;
    private EditText enter_username,enter_password;
    private Button login_button;
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

    /**
     * 初始化控件
     */
    private void initView(){
        spinner= (Spinner) findViewById(R.id.role);
        enter_username= (EditText) findViewById(R.id.login_enter_username);
        enter_password= (EditText) findViewById(R.id.login_enter_password);
        login_button= (Button) findViewById(R.id.login_button);
        checkBox1= (CheckBox) findViewById(R.id.remember);
    }

    /**
     * 事件监听
     */
    class onClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_button:
                    String role=spinner.getSelectedItem().toString();
                    String username=enter_username.getText().toString();
                    String password=enter_password.getText().toString();
                    if (username.equals("")||password.equals("")||returnInt(role).equals("-1")) {
                        Toast.makeText(context, "请填写完整的登录信息", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (checkBox1.isChecked()) {
                        sharedHelper.save(null, username, password, returnInt(role), null);
                    }
                    else {sharedHelper.save(null, null,null,"-1",null);}
                    login(Login.this,username,password,returnInt(role));
                    break;
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String>data=sharedHelper.read();
        if (data.get("username").equals("")||data.get("password").equals("")){
            sharedHelper.save(null,null,null,"-1",null);
            checkBox1.setChecked(false);
        }else checkBox1.setChecked(true);
        String role=data.get("role");
        int Introle=Integer.parseInt(role);
        spinner.setSelection(Introle+1,true);
        enter_username.setText(data.get("username"));
        enter_password.setText(data.get("password"));
    }

    /**
     * 通过获取列表的字符，返回对应的数值
     * @param role
     * @return
     */
    private String returnInt(String role){
        if (role.equals("请选择"))
            return "-1";
        if (role.equals("合伙人"))
            return "0";
        if (role.equals("部门经理"))
            return "1";
        if (role.equals("财务经理"))
            return "2";
        if (role.equals("助理人员"))
            return "3";
        if (role.equals("办公室主任"))
            return "4";
        if (role.equals("质量监督部经理"))
            return "5";
        return "-1";
    }

    /**
     * 实现登录
     * @param context
     * @param userName
     * @param password
     * @param role
     */
    public void login(final Context context,final String userName, final String password, final String role){
        final String url= Constants.url+"user/login?";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                SharedHelper sharedHelper=new SharedHelper(context);
                Gson gson=new Gson();
                LoginInfo loginInfo =gson.fromJson(s,LoginInfo.class);
                if (loginInfo ==null){
                    Toast.makeText(context, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                sharedHelper.save(loginInfo.getUser_id(), loginInfo.getUser_name(),
                        loginInfo.getPassword(), loginInfo.getRole(), loginInfo.getStatus());
                //Toast.makeText(context,"登录成功！",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络连接超时，请检查网络设置", Toast.LENGTH_SHORT).show();
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
