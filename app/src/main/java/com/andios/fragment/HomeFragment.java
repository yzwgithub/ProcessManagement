package com.andios.fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.andios.activity.ActivityDetail;
import com.andios.adapter.RecyclerViewAdapter;
import com.andios.activity.R;
import com.andios.interfaces.OnItemClickListener;


/**
 * Created by ASUS on 2017/6/13.
 */

public class HomeFragment extends Fragment {
    private RecyclerViewAdapter adapter;
    private String[] text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragement_home,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolBar();
        initData();
        initRecyclerView();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), ActivityDetail.class);
                startActivity(intent);
            }
        });
    }
    private void initData(){
        text=new String[20];
        for(int i=0;i<20;i++){
            text[i]="Text"+i;
        }
    }
    private void initRecyclerView(){
        RecyclerView recyclerView= (RecyclerView) getView().findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        recyclerView.setAdapter(adapter=new RecyclerViewAdapter(getActivity(),text));
    }
    private void initToolBar(){
        Toolbar toolbar= (Toolbar) getView().findViewById(R.id.tb_toolbar);
        toolbar.setPopupTheme(R.style.Widget_AppCompat_PopupMenu);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setTitle("易GO");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        break;
                    case  R.id.action_item1:
                        break;
                    case R.id.action_item2:
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
