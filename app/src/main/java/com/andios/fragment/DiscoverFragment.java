package com.andios.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andios.activity.MapActivity;
import com.andios.activity.R;
import com.andios.util.Constants;
import com.andios.util.ProjectInfo;
import com.andios.util.SharedHelper;
import com.andios.util.UserInfo;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by YangZheWen 2017/11/21.
 */

public class DiscoverFragment extends Fragment implements View.OnClickListener {

    private TextView local;
    private TextView signIn;
    private TextView signOut;
    private TextView Z_details;
    private TextView Y_details;
    private TextView J_details;
    private Spinner editText;
    private SharedHelper sharedHelper;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feedback, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        editText= (Spinner) getView().findViewById(R.id.work);
        local= (TextView) getView().findViewById(R.id.local);
        signIn= (TextView) getView().findViewById(R.id.signIn);
        signOut= (TextView) getView().findViewById(R.id.signOut);
        Z_details= (TextView) getView().findViewById(R.id.Z_details);
        Y_details= (TextView) getView().findViewById(R.id.Y_details);
        J_details= (TextView) getView().findViewById(R.id.J_details);
        signIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        Z_details.setOnClickListener(this);
        Y_details.setOnClickListener(this);
        J_details.setOnClickListener(this);
        queryProject(getContext());

    }

    private void initSpinner(Context context,List <ProjectInfo> list){
        List<String>stringList=new ArrayList<>();
        if (list.size()==0){
            stringList.add("无项目");
        }else {
            for (int i = 0; i < list.size(); i++) {
                stringList.add(list.get(i).getP_id());
            }
        }
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        editText.setAdapter(adapter);
    }

    /**
     * 实现监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        sharedHelper=new SharedHelper(getContext());
        Map<String,String>data_=sharedHelper.read_();
        switch(v.getId()){
            case R.id.signIn:
                if (editText.getSelectedItem().toString().equals("")){
                    Toast.makeText(getContext(), "项目代号不能为空！", Toast.LENGTH_SHORT).show();
                    break;
                }
                Constants.project_id=editText.getSelectedItem().toString();
                Constants.signInOrOut=0;
                intent();
                break;
            case R.id.signOut:
                if (editText.getSelectedItem().toString().equals("")){
                    Toast.makeText(getContext(), "项目代号不能为空！", Toast.LENGTH_SHORT).show();
                    break;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("当前时间为:"+getTime()+"是否确认签退");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.project_id=editText.getSelectedItem().toString();
                        Constants.signInOrOut=1;
                        intent();
                    }
                });
                builder.show();
                break;
            case R.id.Z_details:
                sharedHelper.save("0");
                for (int i=0;i<10;i=i+5){
                    queryPerson(getContext(),i);
                }
                local.setText("近10天签到了"+data_.get("project_id")+"次");
                break;
            case R.id.Y_details:
                sharedHelper.save("0");
                for (int i=0;i<30;i=i+5){
                    queryPerson(getContext(),i);
                }
                local.setText("近10天签到了"+data_.get("project_id")+"次");
                break;
            case R.id.J_details:
                sharedHelper.save("0");
                for (int i=0;i<90;i=i+5){
                    queryPerson(getContext(),i);
                }
                local.setText("近10天签到了"+data_.get("project_id")+"次");
                break;
        }
    }

    /**
     * 实现页面跳转
     */
    private void intent(){
        Intent intent=new Intent(getActivity(), MapActivity.class);
        startActivity(intent);
    }

    /**
     * 获取个人签到记录
     * @param context
     * @param start
     */
    private void queryPerson(final Context context,int start){
        sharedHelper=new SharedHelper(context);
        Map<String,String> data=sharedHelper.read();
        final String url= Constants.url+"attendance/queryPerson?user_id="+data.get("user_id")+"&start="+start;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                SharedHelper sharedHelper=new SharedHelper(context);
                Map<String,String>data_=sharedHelper.read_();
                Gson gson=new Gson();
                List<UserInfo>list=gson.fromJson(s,new TypeToken<List<UserInfo>>(){}.getType());
                if (list.size()>0){
                    String data_p = data_.get("project_id");
                    int i = Integer.valueOf(data_p)+list.size();
                    sharedHelper.save(String.valueOf(i));
                }
                else return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        Volley.newRequestQueue(context).add(request);
    }

    /**
     * 获取项目
     * @param context
     * @return
     */
    private void queryProject(final Context context){
        sharedHelper=new SharedHelper(context);
        Map<String,String>data=sharedHelper.read();
        final String url=Constants.url+"phone/queryProjectByUser?user_id="+data.get("user_id");
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                sharedHelper=new SharedHelper(context);
                Gson gson=new Gson();
                List<ProjectInfo> list=gson.fromJson(s,new TypeToken<List<ProjectInfo>>(){}.getType());
                initSpinner(context,list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        Volley.newRequestQueue(context).add(request);
    }
    /**
     * 获取系统时间
     * @return  以字符串的形式返回时间
     */
        private String getTime(){
            SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
            Date date=new Date(System.currentTimeMillis());
            String str=format.format(date);
            return str;
        }
}
