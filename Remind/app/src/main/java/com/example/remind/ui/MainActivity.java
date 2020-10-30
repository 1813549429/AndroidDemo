package com.example.remind.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.example.remind.R;
import com.example.remind.adapter.FirstNode;
import com.example.remind.adapter.NodeTreeAdapter;
import com.example.remind.adapter.SecondNode;
import com.example.remind.adapter.SecondProvider;
import com.example.remind.app.AppContext;
import com.example.remind.base.BaseActivity;
import com.example.remind.db.AppDataBase;
import com.example.remind.db.entity.Remind;
import com.example.remind.db.repository.MainItemRepository;
import com.example.remind.utils.DateUtil;
import com.example.remind.view.MyLinearLayoutManager;
import com.example.remind.vm.MainViewModel;
import com.example.remind.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements View.OnClickListener {

    private static final int ADD_REMIND = 0;
    private static final int EDIT_REMIND = 1;
    private NavigationView mNavigation;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvMain;
    private NodeTreeAdapter mAdapter;
    private List<BaseNode> firstNodeList;
    public static final int NAV_CLICK = 0;
    public static final int MORE_CLICK = 1;
    public static final int ADD_CLICK = 2;
    private FirstNode firstNodeCompleted;
    boolean isHide = false;
    private PopupWindow popupWindow;
    private List<BaseNode> secondNodeListOverdue;
    private List<BaseNode> secondNodeListToday;
    private List<BaseNode> secondNodeListNextDays;
    private List<BaseNode> secondNodeListNotScheduled;
    private List<BaseNode> secondNodeListCompleted;
    private List<Remind> remindList;
    private FirstNode firstNodeOverdue;
    private FirstNode firstNodeToday;
    private FirstNode firstNodeNextDays;
    private FirstNode firstNodeNotScheduled;
    private TextView mTvTitle;
    private ConstraintLayout long_select;
    private boolean isLongSelect = false;
    private ImageButton ib_add;
    private List<Remind> checkedRemindList = new ArrayList<>();
    private ImageButton ib_left;
    private TextView mTvSelectAll;
    private TextView mDelete;
    private String currentText;
    private MainItemRepository repository;
    public static final int OVERDUE = 0;
    public static final int TODAY = 1;
    public static final int NEXT_SEVEN_DAYS = 2;
    public static final int NOT_SCHEDULED = 3;
    public static final int COMPLETED = 4;

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
        //拿到所有数据
        remindList = getIntent().getParcelableArrayListExtra("allData");

        //设置一级Item的数据
        firstNodeList = new ArrayList<>();
        //设置二级Item的数据,
        secondNodeListCompleted = new ArrayList<>();
        secondNodeListNotScheduled = new ArrayList<>();
        secondNodeListNextDays = new ArrayList<>();
        secondNodeListOverdue = new ArrayList<>();
        secondNodeListToday = new ArrayList<>();
        for (Remind remind : remindList) {
            addSecondNode(remind);
        }
        setFirstNode();
        //初始化数据仓库
        repository = MainItemRepository.getInstance(AppDataBase.getInstance(getApplicationContext()));

    }

    private void setFirstNode() {
        firstNodeList.clear();
        firstNodeOverdue = new FirstNode(secondNodeListOverdue,getString(R.string.overdue),secondNodeListOverdue.size(), OVERDUE, "#FEEEEE");
        firstNodeToday = new FirstNode(secondNodeListToday,getString(R.string.today),secondNodeListToday.size(), TODAY, "#E6F0FC");
        firstNodeNextDays = new FirstNode(secondNodeListNextDays,getString(R.string.next_seven_days),secondNodeListNextDays.size(), NEXT_SEVEN_DAYS, "#F4F4F4");
        firstNodeNotScheduled = new FirstNode(secondNodeListNotScheduled,getString(R.string.not_scheduled),secondNodeListNotScheduled.size(), NOT_SCHEDULED, "#F4F4F4");
        firstNodeCompleted = new FirstNode(secondNodeListCompleted,getString(R.string.completed),secondNodeListCompleted.size(), COMPLETED,"#2088CBDB");

        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeCompleted);
    }

    private List<FirstNode> addSecondNode(Remind remind) {
        SecondNode secondNode = new SecondNode(remind, null);
        List<FirstNode> addNode = new ArrayList<>();
        if(isCompletedData(remind)) {
            secondNodeListCompleted.add(secondNode);
            addNode.add(firstNodeCompleted);
            return addNode;
        }

        if(isNextDaysData(remind)) {
            secondNodeListNextDays.add(secondNode);
            addNode.add(firstNodeNextDays);
        }

        if(isOverdueData(remind)) {
            secondNodeListOverdue.add(secondNode);
            addNode.add(firstNodeOverdue);
        }

        if(isNotScheduledData(remind)) {
            secondNodeListNotScheduled.add(secondNode);
            addNode.add(firstNodeNotScheduled);
        }

        if(isTodayData(remind)) {
            secondNodeListToday.add(secondNode);
            addNode.add(firstNodeToday);
        }
        return addNode;
    }


    private boolean isCompletedData(Remind remind)  {
        if(remind.isComplete()) {
            return true;
        }
        return false;
    }

    private boolean isNotScheduledData(Remind remind)  {
        if(!remind.isSetting()) {
           return true;
        }
        return false;
    }

    private boolean isNextDaysData(Remind remind)  {
        int currentDay = DateUtil.getYearMonthDay(System.currentTimeMillis());
        int remindDay = DateUtil.getYearMonthDay(remind.getTime());
        if(remindDay - currentDay > 0 && remindDay-currentDay <= 7 && remind.isSetting()) {
            return true;
        }
        return false;
    }

    private boolean isTodayData(Remind remind)  {
        int currentDay = DateUtil.getYearMonthDay(System.currentTimeMillis());
        int remindDay = DateUtil.getYearMonthDay(remind.getTime());
        if(currentDay == remindDay && remind.isSetting()) {
            return true;
        }
        return false;
    }

    private boolean isOverdueData(Remind remind)  {
        if(remind.getTime() < System.currentTimeMillis() && !remind.isComplete() && remind.isSetting()) {
            return true;
        }
        return false;
    }


    private void initMainView() {
        currentText = getString(R.string.today);
        ImageButton ib_right = findViewById(R.id.ib_right);
        ib_add = findViewById(R.id.ib_main_add);
        mTvTitle = findViewById(R.id.tv_title);
        long_select = findViewById(R.id.long_select);
        ib_left = findViewById(R.id.ib_left);
        mTvSelectAll = findViewById(R.id.select_all);
        mDelete = findViewById(R.id.delete);

        mTvSelectAll.setOnClickListener(this);
        mDelete.setOnClickListener(this);

        ib_left.setBackground(getResources().getDrawable(R.mipmap.sidebar));
        ib_right.setBackground(getResources().getDrawable(R.mipmap.more));
        mTvTitle.setVisibility(View.GONE);

        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NodeTreeAdapter();
        mRvMain.setAdapter(mAdapter);

        mAdapter.setHeaderView(View.inflate(getApplicationContext(), R.layout.item_header, null));

        mAdapter.setList(firstNodeList);

        setAdapterClickEvent();


        mNavigation = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        initNavigation();
    }

    private void setAdapterClickEvent() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //点击展开，这里使用payload进行增量刷新（避免整个item刷新导致的闪烁，不自然）
                mAdapter.expandOrCollapse(position, true, true, NodeTreeAdapter.EXPAND_COLLAPSE_PAYLOAD);
                //同样需要屏蔽一级菜单
                if(view.findViewById(R.id.item_count) == null) {
                    Remind remind = ((SecondNode)adapter.getItem(position)).getRemindData();
                    if(isLongSelect) {
                        CheckedTextView checkedTextView = view.findViewById(R.id.cb_item_complete);
                        checkedTextView.setChecked(!checkedTextView.isChecked());
                        if(checkedTextView.isChecked()) {
                            checkedRemindList.add(remind);
                        }else {
                            checkedRemindList.remove(remind);
                        }
                        mTvTitle.setText(checkedRemindList.size() + " " + getString(R.string.select));
                        mTvTitle.setVisibility(View.VISIBLE);
                    }else {

                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("remind", remind);
                        startActivityForResult(intent, EDIT_REMIND);

//                        SecondNode secondNode = (SecondNode) mAdapter.getItem(position);
//                        Remind remind = secondNode.getRemindData();
//                        remind.setComplete(!remind.isComplete());
//                        secondNode.setRemindData(remind);
//
//                        if(remind.isComplete()) {
//                            mAdapter.nodeSetData(firstNodeCompleted, 0, secondNode);
//                        }else {
//                            List<FirstNode> baseNodes = addSecondNode(remind);
//                            for (FirstNode firstNode : baseNodes) {
//                                mAdapter.setData(firstNodeList.indexOf(firstNode), firstNode);
//                            }
//                        }
//
//                        AppContext.executors.diskIO().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                repository.insertRemind(remind);
//                            }
//                        });
                    }
                }

            }
        });

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                //是否是一级菜单，如果是就不用管了
                if(view.findViewById(R.id.item_count) == null) {
                    long_select.setVisibility(View.VISIBLE);
                    CheckedTextView checkedTextView = view.findViewById(R.id.cb_item_complete);
                    checkedTextView.setChecked(!checkedTextView.isChecked());
                    isLongSelect = true;
                    if(checkedTextView.isChecked()) {
                        checkedRemindList.add(((SecondNode)adapter.getItem(position)).getRemindData());
                    }else {
                        checkedRemindList.remove(((SecondNode)adapter.getItem(position)).getRemindData());
                    }
                    ib_add.setVisibility(View.GONE);
                    ib_left.setBackground(getResources().getDrawable(R.mipmap.close));
                    mTvTitle.setText(checkedRemindList.size() + " " + getString(R.string.select));
                    mTvTitle.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

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
    }

    private void initNavigation() {
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header_main, mNavigation);

        view.findViewById(R.id.tv_nav_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentText = getString(R.string.today);
                mTvTitle.setText(currentText);
                TextView tv_title_center = mAdapter.getHeaderLayout().findViewById(R.id.tv_title_center);
                tv_title_center.setText(currentText);
                reloadToday();
                hiddenDrawer();
            }
        });

        view.findViewById(R.id.tv_nav_next_seven_days).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentText = getString(R.string.next_seven_days);
                mTvTitle.setText(currentText);
                TextView tv_title_center = mAdapter.getHeaderLayout().findViewById(R.id.tv_title_center);
                tv_title_center.setText(currentText);
                reloadNextSevenDays();
                hiddenDrawer();
            }
        });

        view.findViewById(R.id.tv_nav_inbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentText = getString(R.string.inbox);
                mTvTitle.setText(currentText);
                TextView tv_title_center = mAdapter.getHeaderLayout().findViewById(R.id.tv_title_center);
                tv_title_center.setText(currentText);
                reloadInbox();
                hiddenDrawer();
            }
        });

    }

    private void reloadToday() {
        firstNodeList.clear();
        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeCompleted);
        mAdapter.setList(firstNodeList);
    }

    private void reloadNextSevenDays() {
        firstNodeList.clear();
        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeNextDays);
        firstNodeList.add(firstNodeCompleted);
        mAdapter.setList(firstNodeList);
    }

    private void reloadInbox() {
        firstNodeList.clear();
        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeNextDays);
        firstNodeList.add(firstNodeNotScheduled);
        firstNodeList.add(firstNodeCompleted);
        mAdapter.setList(firstNodeList);
    }

    private void hiddenDrawer() {
        mDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(mNavigation);
            }
        }, 200);
    }

    public void click(int mode) {
        if (mode == ADD_CLICK) {
            startActivityForResult(new Intent(MainActivity.this, AddActivity.class),ADD_REMIND);
            overridePendingTransition(0, 0);
        }
    }

    public void left(View view) {
        if(isLongSelect) {
            exitLongClick();
        }else {
            mDrawerLayout.openDrawer(mNavigation);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_REMIND) {
            if(data != null) {
                Remind remind = data.getParcelableExtra("remind");
                List<FirstNode> firstNodeListData = addSecondNode(remind);
                for (FirstNode firstNode :
                        firstNodeListData) {
                    int index = firstNodeList.indexOf(firstNode);
                    mAdapter.setData(index, firstNode);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isLongSelect) {
            exitLongClick();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitLongClick() {
        isLongSelect = false;
        ib_add.setVisibility(View.VISIBLE);
        long_select.setVisibility(View.INVISIBLE);
        SecondProvider.isSelectAll = false;
        mAdapter.setList(firstNodeList);
        checkedRemindList.clear();
        ib_left.setBackground(getResources().getDrawable(R.mipmap.sidebar));
        mTvTitle.setText(currentText);
        mTvTitle.setVisibility(View.INVISIBLE);
    }
    private void clearAll() {
        secondNodeListOverdue.clear();
        secondNodeListCompleted.clear();
        secondNodeListNextDays.clear();
        secondNodeListToday.clear();
        secondNodeListNotScheduled.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                SecondProvider.isSelectAll = !SecondProvider.isSelectAll;
                mAdapter.setList(firstNodeList);
                if(SecondProvider.isSelectAll) {
                    checkedRemindList.clear();
                    for (BaseNode node : firstNodeList) {
                        FirstNode firstNode = (FirstNode) node;
                        List<BaseNode> secondNodeList = firstNode.getChildNode();
                        for (BaseNode baseNode : secondNodeList) {
                            SecondNode secondNode = (SecondNode) baseNode;
                            checkedRemindList.add(secondNode.getRemindData());
                        }
                    }
                }else {
                    checkedRemindList.clear();
                }

                mTvTitle.setText(checkedRemindList.size() + " " + getString(R.string.select));
                break;
            case R.id.delete:
                for (Remind checkedRemind : checkedRemindList) {
                    if (remindList.contains(checkedRemind)) {
                        remindList.remove(checkedRemind);
                    }
                    AppContext.executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //需要上一个数据执行完成才能继续执行，所以睡100毫秒
                            repository.deleteReminds(checkedRemind);
                        }
                    });

                    SecondNode secondNode = new SecondNode(checkedRemind, null);
                    mAdapter.remove(secondNode);
                }

                clearAll();
                for (Remind remind : remindList) {
                    addSecondNode(remind);
                }
                if(currentText.equals(getString(R.string.today))) {
                    reloadToday();
                }else if(currentText.equals(getString(R.string.inbox))) {
                    reloadInbox();
                }else if (currentText.equals(getString(R.string.next_seven_days))) {
                    reloadNextSevenDays();
                }
                exitLongClick();

                break;

        }
    }

}