package com.andios.fragment;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.andios.processmanagement.R;


/**
 * Created by ASUS on 2017/6/13.
 */

public class HomeFragment extends Fragment {
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
