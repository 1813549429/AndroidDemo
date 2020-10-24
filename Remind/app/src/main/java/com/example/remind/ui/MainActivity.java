package com.example.remind.ui;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.remind.R;
import com.example.remind.adapter.FirstNode;
import com.example.remind.adapter.NodeTreeAdapter;
import com.example.remind.base.BaseActivity;
import com.example.remind.databinding.ActivityMainBinding;
import com.example.remind.vm.MainViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    private NavigationView mNavigation;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvMain;
    private NodeTreeAdapter mAdapter = new NodeTreeAdapter();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView.setHolder(this);
        bindingView.setVm(viewModel);

        initMainData();
        initMainView();
    }

    private void initMainData() {
        //获取所有事件的数据
        List<BaseNode> allEventList = new ArrayList<>();
        //从数据库查数据

        //设置一级Item的数据
        List<BaseNode> firstNodeList = new ArrayList<>();
        FirstNode firstNode = new FirstNode();
        firstNodeList.add()
    }

    private void initMainView() {

        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(this));
        mRvMain.setAdapter(mAdapter);

        mNavigation = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        initNavigation();
    }

    private void initNavigation() {
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header_main, mNavigation);

        findViewById(R.id.icNav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mNavigation);
            }
        });

    }

    private void hiddenDrawer() {
        mDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(mNavigation);
            }
        }, 500);
    }

}