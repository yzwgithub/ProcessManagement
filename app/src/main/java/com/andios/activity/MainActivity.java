package com.andios.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.andios.adapter.SectionsPagerAdapter;
import com.andios.fragment.DiscoverFragment;
import com.andios.fragment.HomeFragment;
import com.andios.fragment.UserCentralFragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by YangZheWen on 2017/10/23.
 * 主界面，包含三个Fragment
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,ViewPager.OnPageChangeListener{
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initViewPager();
        initBottomNavigation();
        queryUser("http://192.168.1.138:8080/user/queryUser?");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar= (BottomNavigationBar) findViewById(R.id.buttom_bar);
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(int position) {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 初始化界面ViewPager
     */
    private void initViewPager() {

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new UserCentralFragment());
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30);
            }
        });
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }

    /**
     * 初始化BottomNavigation
     */
    private void initBottomNavigation(){
        bottomNavigationBar= (BottomNavigationBar) findViewById(R.id.buttom_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_tab_home,"HOME")).setActiveColor("#ECECEC")
                .addItem(new BottomNavigationItem(R.mipmap.ic_category,"MINE")).setActiveColor("#ECECEC")
                .addItem(new BottomNavigationItem(R.mipmap.ic_user_center,"DISC")).setActiveColor("#ECECEC")
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);//注册监听事件
    }

    /**
     * 访问网络获取用户的详细信息
     * @param url 请求的url
     */
    private void queryUser(String url){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Toast.makeText(MainActivity.this,volleyError.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("user_id","1");
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
    }
}
