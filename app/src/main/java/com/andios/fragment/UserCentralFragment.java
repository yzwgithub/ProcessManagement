package com.andios.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andios.activity.R;
import com.andios.util.SharedHelper;
import com.andios.util.UserInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YangZheWen on 2017/6/13.
 */

public class UserCentralFragment extends Fragment {
    SharedHelper sharedHelper;
   private TextView name;
   private TextView phone_number;
   private TextView persion_id;
   private TextView positio;
   private TextView bumen;
   private TextView person_job;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragement_mine,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        sharedHelper=new SharedHelper(getContext());
        SharedHelper sharedHelper=new SharedHelper(getContext());
        Map<String,String>data=sharedHelper.read();
        String user_id=data.get("user_id");
        getUser("http://192.168.110.199:8080/phone/queryUser?user_id="+user_id);
    }

    private void initView(){
        name= (TextView) getView().findViewById(R.id.name);
        phone_number= (TextView) getView().findViewById(R.id.phone_number);
        persion_id= (TextView) getView().findViewById(R.id.persion_id);
        positio= (TextView) getView().findViewById(R.id.positio);
        bumen= (TextView) getView().findViewById(R.id.bumen);
        person_job= (TextView) getView().findViewById(R.id.person_job);
    }
    private void getUser(String url){
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson=new Gson();
                UserInfo userInfo=gson.fromJson(s,UserInfo.class);
                setDetails(userInfo.getReal_name(),userInfo.getPhone(),userInfo.getIdcard(),
                        userInfo.getPositio(),userInfo.getPracreq(),userInfo.getJob());
                Toast.makeText(getContext(),"成功"+s,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Toast.makeText(getContext(),"失败"+volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
    }
    private void setDetails(String mname,String mphone,String mpersion_id,String mpositio,String mbumen,String mpersion_job){
        name.setText(mname);
        phone_number.setText(mphone);
        persion_id.setText(mpersion_id);
        positio.setText(mpositio);
        bumen.setText(mbumen);
        person_job.setText(mpersion_job);
    }
}
