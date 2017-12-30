package com.andios.fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andios.activity.MainActivity;
import com.andios.adapter.RecyclerViewAdapter;
import com.andios.activity.R;
import com.andios.dao.DataOperate;
import com.andios.dao.HistoryHelper;
import com.andios.util.Constants;
import com.andios.util.SharedHelper;
import com.andios.util.UserInfo;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;


/**
 * Created by YangZheWen on 2017/6/13.
 */

public class HomeFragment extends Fragment {
    private RecyclerViewAdapter adapter;
    private Cursor cursor;
    private DataOperate dataOperate=new DataOperate();
    private String[] text,time,details;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragement_home,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        initData();
        initRecyclerView();
        refreshLayout= (SwipeRefreshLayout) getView().findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cursor=dataOperate.select(getContext());
                queryPerson(getContext(),cursor.getCount());
                refreshLayout.setRefreshing(false);
                MainActivity.fragmentUpdate();
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        RecyclerView recyclerView= (RecyclerView) getView().findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        recyclerView.setAdapter(adapter=new RecyclerViewAdapter(getActivity(),text,time,details));
    }

    /**
     * 获取签到记录
     * @param context
     * @param start
     */
    private void queryPerson(final Context context,int start){
        SharedHelper sharedHelper=new SharedHelper(context);
        Map<String,String> data=sharedHelper.read();
        final String url= Constants.url+"attendance/queryPerson?user_id="+data.get("user_id")+"&start="+start;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson=new Gson();
                List<UserInfo>list=gson.fromJson(s,new TypeToken<List<UserInfo>>(){}.getType());
                UserInfo[] userInfos=new UserInfo[list.size()];
                for (int i=0;i<list.size();i++){
                    userInfos[i]=list.get(i);
                    if (userInfos[i].getAfternoon()!=null) {
                        dataOperate.insert(context, null, userInfos[i].getReal_name(), userInfos[i].getDate(), userInfos[i].getLocation());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        Volley.newRequestQueue(context).add(request);
    }

    /**
     * 初始化数据，从本地缓存中获取数据
     */
    private void initData(){
        cursor=dataOperate.select(getContext());
        text=new String[cursor.getCount()];
        time=new String[cursor.getCount()];
        details=new String[cursor.getCount()];
        int i=0;
        if (cursor.moveToLast()){
            do{
                text[i]=cursor.getString(cursor.getColumnIndex(HistoryHelper.PROJECT_NAME));
                time[i]=cursor.getString(cursor.getColumnIndex(HistoryHelper.TIME));
                details[i]=cursor.getString(cursor.getColumnIndex(HistoryHelper.DETAILS));
                i++;
            }while (cursor.moveToPrevious());
        }
    }

    /**
     * 从网络上获取个人签到记录
     */
    private void getData(){
        cursor=dataOperate.select(getContext());
        int count=cursor.getCount();
        if (count==0) {
            queryPerson(getContext(), count);
        }else return;
    }
}
