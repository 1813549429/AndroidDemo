package com.example.bonoremind;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bonoremind.base.BaseActivity;
import com.example.bonoremind.databinding.ActivityMainBinding;
import com.example.bonoremind.vm.MainViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    public static final int ADD_CLICK = 0;
    public static final int NAV_CLICK = 1;
    public static final int MORE_CLICK = 2;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigation;
    private MutableLiveData<String> mTitleLiveData;
    private RecyclerView mRvMain;
    private long preClickTime;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView.setHolder(this);
        bindingView.setVm(viewModel);


        //初始化ViewModel的值
        initViewModelVar();
        //初始化View
        initMainView();

    }

    private void initMainView() {
        //侧边栏
        mNavigation = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        initNavigation();

        //RecycleView
        mRvMain = findViewById(R.id.rv_main);

    }

    private void initViewModelVar() {
        mTitleLiveData = viewModel.getTitleLiveData();
    }

    private void initNavigation() {
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header_main, mNavigation);
        view.findViewById(R.id.tv_nav_inbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenDrawer();
                //切换到Inbox视图下
                changeInboxView();
            }
        });
        view.findViewById(R.id.tv_nav_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenDrawer();
                //切换到Today视图下
                changeTodayView();

            }
        });
        view.findViewById(R.id.tv_nav_next_seven_days).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenDrawer();
                //切换到Next Seven Days视图下
                changeNextSevenDaysView();

            }
        });
    }

    //切换到Next Seven Days下
    private void changeNextSevenDaysView() {
        mTitleLiveData.setValue(getString(R.string.next_seven_days));
    }

    //切换到Today下
    private void changeTodayView() {
        mTitleLiveData.setValue(getString(R.string.today));
    }

    //切换到Inbox下
    private void changeInboxView() {
        mTitleLiveData.setValue(getString(R.string.inbox));
    }


    private void hiddenDrawer() {
        mDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(mNavigation);
            }
        }, 200);
    }


    //点击事件
    public void click(int mode) {

        //重复点击的容错处理
        if (System.currentTimeMillis() - preClickTime < 300) {
            return;
        }
        preClickTime = System.currentTimeMillis();
        switch (mode) {
            case NAV_CLICK:

                mDrawerLayout.openDrawer(mNavigation);

                break;
            case ADD_CLICK:

                Intent intent = new Intent(MainActivity.this, AddActivity.class);

                break;
            case MORE_CLICK:
                int j = 1;
                break;
        }
    }
}