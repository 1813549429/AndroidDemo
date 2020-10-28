package com.example.remind.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.remind.R;
import com.example.remind.adapter.FirstNode;
import com.example.remind.adapter.NodeTreeAdapter;
import com.example.remind.adapter.SecondNode;
import com.example.remind.base.BaseActivity;
import com.example.remind.db.AppDataBase;
import com.example.remind.db.entity.Remind;
import com.example.remind.db.repository.MainItemRepository;
import com.example.remind.enums.MainItemType;
import com.example.remind.utils.AppExecutors;
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
    public static final int NAV_CLICK = 0;
    public static final int MORE_CLICK = 1;
    public static final int ADD_CLICK = 2;
    private FirstNode firstNodeCompleted;
    boolean isHide = false;
    private PopupWindow popupWindow;

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
        MainItemRepository mainItemRepository = MainItemRepository.getInstance(AppDataBase.getInstance(getApplicationContext()));
        AppExecutors appExecutors = new AppExecutors();
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mainItemRepository.getReminds();
            }
        });

        //设置一级Item的数据
        firstNodeList = new ArrayList<>();
        //设置二级Item的数据,
        List<BaseNode> secondNodeListOverdue = new ArrayList<>();
        List<BaseNode> secondNodeListToday = new ArrayList<>();
        List<BaseNode> secondNodeListNextDays = new ArrayList<>();
        List<BaseNode> secondNodeListNotScheduled = new ArrayList<>();
        List<BaseNode> secondNodeListCompleted = new ArrayList<>();

        Remind remind = new Remind();
        remind.setTitle("sadasd");
        remind.setTime(System.currentTimeMillis());
        remind.setComplete(true);
        SecondNode secondNode = new SecondNode(remind, null);
        secondNodeListCompleted.add(secondNode);

        FirstNode firstNodeOverdue = new FirstNode(secondNodeListOverdue,getString(R.string.overdue),secondNodeListOverdue.size(), MainItemType.Overdue, "#FEEEEE");
        FirstNode firstNodeToday = new FirstNode(secondNodeListToday,getString(R.string.today),secondNodeListToday.size(), MainItemType.Today, "#E6F0FC");
        FirstNode firstNodeNextDays = new FirstNode(secondNodeListNextDays,getString(R.string.next_seven_days),secondNodeListNextDays.size(), MainItemType.NextSevenDays, "#F4F4F4");
        FirstNode firstNodeNotScheduled = new FirstNode(secondNodeListNotScheduled,getString(R.string.not_scheduled),secondNodeListNotScheduled.size(), MainItemType.NotScheduled, "#F4F4F4");
        firstNodeCompleted = new FirstNode(secondNodeListCompleted,getString(R.string.completed),secondNodeListCompleted.size(), MainItemType.Completed,"#2088CBDB");

        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeNextDays);
        firstNodeList.add(firstNodeNotScheduled);
        firstNodeList.add(firstNodeCompleted);

    }

    private void initMainView() {

        ImageButton ib_left = findViewById(R.id.ib_left);
        ImageButton ib_right = findViewById(R.id.ib_right);
        TextView mTvTitle = findViewById(R.id.tv_title);
        ib_left.setBackground(getResources().getDrawable(R.mipmap.sidebar));
        ib_right.setBackground(getResources().getDrawable(R.mipmap.more));
        mTvTitle.setVisibility(View.GONE);

        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(this));
        mRvMain.setAdapter(mAdapter);

        mAdapter.setHeaderView(View.inflate(getApplicationContext(), R.layout.item_header, null));

        mAdapter.setList(firstNodeList);

        mRvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager)layoutManager;
                    //获取第一个可见view的位置
                    int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if(firstItemPosition == 0) {
                        mTvTitle.setVisibility(View.GONE);
                    }else {
                        mTvTitle.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mNavigation = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        initNavigation();
    }

    private void initNavigation() {
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header_main, mNavigation);

    }

    private void hiddenDrawer() {
        mDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(mNavigation);
            }
        }, 500);
    }

    public void click(int mode) {
        if (mode == ADD_CLICK) {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
            overridePendingTransition(0, 0);
        }
    }

    public void left(View view) {
        mDrawerLayout.openDrawer(mNavigation);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void right(View view) {

        View popupView = View.inflate(getApplicationContext(), R.layout.popupwindow_main, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        TextView tv_hide = popupView.findViewById(R.id.tv_hide);

        if(isHide) {
            tv_hide.setText(R.string.show_completed);
        }else {
            tv_hide.setText(R.string.hide_completed);
        }

        popupWindow.showAsDropDown(view, 32 - popupWindow.getContentView().getMeasuredWidth(), 0);
        tv_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHide = !isHide;
                if(isHide) {
                    firstNodeList.remove(firstNodeCompleted);
                }else {
                    firstNodeList.add(firstNodeCompleted);
                }
                mAdapter.setList(firstNodeList);
                popupWindow.dismiss();
            }
        });
    }

}