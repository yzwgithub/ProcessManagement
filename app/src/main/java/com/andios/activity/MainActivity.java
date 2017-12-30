package com.andios.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.andios.adapter.SectionsPagerAdapter;
import com.andios.fragment.DiscoverFragment;
import com.andios.fragment.HomeFragment;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangZheWen on 2017/10/23.
 * 主界面，包含三个Fragment
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,ViewPager.OnPageChangeListener {
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private static SectionsPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initViewPager();
        initBottomNavigation();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar= (BottomNavigationBar) findViewById(R.id.buttom_bar);
        bottomNavigationBar.selectTab(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(int position) {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setCurrentItem(position);
        adapter.notifyDataSetChanged();
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
        fragments.add(new DiscoverFragment());
        fragments.add(new HomeFragment());
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30);
            }
        });
        adapter=new SectionsPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
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
                .addItem(new BottomNavigationItem(R.mipmap.ic_user_center,"DISC")).setActiveColor("#ECECEC")
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);//注册监听事件
    }
    public static void fragmentUpdate(){
        adapter.notifyDataSetChanged();
    }
}