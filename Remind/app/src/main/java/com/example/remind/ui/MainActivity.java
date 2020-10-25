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
import com.example.remind.enums.MainItemType;
import com.example.remind.vm.MainViewModel;
import com.example.remind.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    private NavigationView mNavigation;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvMain;
    private NodeTreeAdapter mAdapter = new NodeTreeAdapter();
    private List<BaseNode> firstNodeList;


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
        firstNodeList = new ArrayList<>();
        //设置二级Item的数据,
        List<BaseNode> secondNodeListOverdue = new ArrayList<>();
        List<BaseNode> secondNodeListToday = new ArrayList<>();
        List<BaseNode> secondNodeListNextDays = new ArrayList<>();
        List<BaseNode> secondNodeListNotScheduled = new ArrayList<>();
        List<BaseNode> secondNodeListCompleted = new ArrayList<>();

        FirstNode firstNodeOverdue = new FirstNode(secondNodeListOverdue,getString(R.string.overdue),secondNodeListOverdue.size(), MainItemType.Overdue);
        FirstNode firstNodeToday = new FirstNode(secondNodeListToday,getString(R.string.today),secondNodeListToday.size(), MainItemType.Today);
        FirstNode firstNodeNextDays = new FirstNode(secondNodeListNextDays,getString(R.string.next_seven_days),secondNodeListNextDays.size(), MainItemType.NextSevenDays);
        FirstNode firstNodeNotScheduled = new FirstNode(secondNodeListNotScheduled,getString(R.string.not_scheduled),secondNodeListNotScheduled.size(), MainItemType.NotScheduled);
        FirstNode firstNodeCompleted = new FirstNode(secondNodeListCompleted,getString(R.string.completed),secondNodeListCompleted.size(), MainItemType.Completed);

        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeNextDays);
        firstNodeList.add(firstNodeNotScheduled);
        firstNodeList.add(firstNodeCompleted);

    }

    private void initMainView() {

        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(this));
        mRvMain.setAdapter(mAdapter);
        mAdapter.setList(firstNodeList);
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