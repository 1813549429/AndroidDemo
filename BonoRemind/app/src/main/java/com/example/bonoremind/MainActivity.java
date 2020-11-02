package com.example.bonoremind;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.example.bonoremind.adapter.FirstNode;
import com.example.bonoremind.adapter.NodeTreeAdapter;
import com.example.bonoremind.adapter.SecondNode;
import com.example.bonoremind.adapter.SecondProvider;
import com.example.bonoremind.app.AppContext;
import com.example.bonoremind.base.BaseActivity;
import com.example.bonoremind.databinding.ActivityMainBinding;
import com.example.bonoremind.db.AppDataBase;
import com.example.bonoremind.db.entity.Remind;
import com.example.bonoremind.db.repository.MainItemRepository;
import com.example.bonoremind.utils.DateUtil;
import com.example.bonoremind.utils.JsonUtil;
import com.example.bonoremind.view.MyLinearLayoutManager;
import com.example.bonoremind.vm.MainViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    public static final int ADD_CLICK = 0;
    public static final int NAV_CLICK = 1;
    public static final int MORE_CLICK = 2;
    private static final int EDIT_REMIND = 6;
    private static final int NOTIFICATION = 5;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigation;
    private MutableLiveData<String> mTitleLiveData;
    private RecyclerView mRvMain;
    private long preClickTime;
    public static final int OVERDUE = 0;
    public static final int TODAY = 1;
    public static final int NEXT_SEVEN_DAYS = 2;
    public static final int NOT_SCHEDULED = 3;
    public static final int COMPLETED = 4;
    public static final int DELETE = 7;
    public static final int SELECT_ALL = 8;

    private List<Remind> remindList;
    long recentlyMinutes = Long.MAX_VALUE;
    private Remind recentlyRemind;
    private List<BaseNode> firstNodeList;
    private List<BaseNode> secondNodeListCompleted;
    private List<BaseNode> secondNodeListNotScheduled;
    private List<BaseNode> secondNodeListNextDays;
    private List<BaseNode> secondNodeListOverdue;
    private List<BaseNode> secondNodeListToday;
    private FirstNode firstNodeOverdue;
    private FirstNode firstNodeToday;
    private FirstNode firstNodeNextDays;
    private FirstNode firstNodeNotScheduled;
    private FirstNode firstNodeCompleted;
    private NodeTreeAdapter mAdapter;
    boolean isHide = true;
    private ImageButton mIbMore;
    private TextView headerTextView;
    private TextView mTvTitle;
    private ConstraintLayout long_select;
    private boolean isLongSelect = false;
    private List<Remind> checkedRemindList = new ArrayList<>();
    private SecondNode editingNode;
    private ImageButton mIbLeft;
    private ImageButton mIbAdd;
    private MainItemRepository repository;
    private String currentText;

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
        initMainData();
        //初始化View
        initMainView();

    }

    private void initMainData() {

        repository = MainItemRepository.getInstance(AppDataBase.getInstance(getApplicationContext()));


        //拿到所有数据
        remindList = getIntent().getParcelableArrayListExtra("allData");

        initNotification();


        //拿到所有数据
        remindList = getIntent().getParcelableArrayListExtra("allData");


        //设置二级Item的数据,
        secondNodeListCompleted = new ArrayList<>();
        secondNodeListNotScheduled = new ArrayList<>();
        secondNodeListNextDays = new ArrayList<>();
        secondNodeListOverdue = new ArrayList<>();
        secondNodeListToday = new ArrayList<>();

        for (Remind remind : remindList) {
            addNode(remind);
            //计算所有Remind中最近提醒的时间
            measureRemindTime(remind);
        }

        //设置一级Item的数据
        firstNodeList = new ArrayList<>();

        firstNodeOverdue = new FirstNode(secondNodeListOverdue,getString(R.string.overdue),secondNodeListOverdue.size(), OVERDUE, "#FEEEEE");
        firstNodeToday = new FirstNode(secondNodeListToday,getString(R.string.today),secondNodeListToday.size(), TODAY, "#E6F0FC");
        firstNodeNextDays = new FirstNode(secondNodeListNextDays,getString(R.string.next_seven_days),secondNodeListNextDays.size(), NEXT_SEVEN_DAYS, "#F4F4F4");
        firstNodeNotScheduled = new FirstNode(secondNodeListNotScheduled,getString(R.string.not_scheduled),secondNodeListNotScheduled.size(), NOT_SCHEDULED, "#F4F4F4");
        firstNodeCompleted = new FirstNode(secondNodeListCompleted,getString(R.string.completed),secondNodeListCompleted.size(), COMPLETED,"#2088CBDB");

        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeNextDays);
        firstNodeList.add(firstNodeNotScheduled);
//        firstNodeList.add(firstNodeCompleted);


    }

    private void reloadToday() {
        firstNodeList.clear();

        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        if(!isHide) {
            firstNodeList.add(firstNodeCompleted);
        }
        mAdapter.setList(firstNodeList);
    }

    private void reloadNextSevenDays() {
        firstNodeList.clear();
        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeNextDays);
        if(!isHide) {
            firstNodeList.add(firstNodeCompleted);
        }
        mAdapter.setList(firstNodeList);
    }

    private void reloadInbox() {
        firstNodeList.clear();

        firstNodeList.add(firstNodeOverdue);
        firstNodeList.add(firstNodeToday);
        firstNodeList.add(firstNodeNextDays);
        firstNodeList.add(firstNodeNotScheduled);
        if(!isHide) {
            firstNodeList.add(firstNodeCompleted);
        }
        mAdapter.setList(firstNodeList);

    }


    private void initNotification() {

        AppContext.executors.scheduleIO().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currentMinutes = DateUtil.intArrayToLong(DateUtil.getDay(System.currentTimeMillis()));
                Log.d("NotificationText", "currentMinutes:"+currentMinutes+"");
                Log.d("NotificationText", "recentlyMinutes:"+recentlyMinutes+"");
                if (currentMinutes == recentlyMinutes) {
                    //开启通知，主线程
                    Log.d("NotificationText", "recentlyMinutes:"+currentMinutes+"");
                    AppContext.executors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            PowerManager powerManager = (PowerManager) MainActivity.this
                                    .getSystemService(Context.POWER_SERVICE);
                            boolean isOpen = powerManager.isScreenOn();

                            if (isOpen) {
                                //发送通知

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    NotificationChannel notificationChannel=new NotificationChannel("CHANNEL_ID","CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
                                    notificationChannel.setDescription("CHANNEL_DESCRIPTION");
                                    NotificationManager notificationManager=getSystemService(NotificationManager.class);
                                    notificationManager.createNotificationChannel(notificationChannel);
                                }

                                NotificationManager manager=
                                        (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

                                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
                                remoteViews.setTextViewText(R.id.tv_title, DateUtil.timeToStr(recentlyRemind.getTime())
                                        + DateUtil.longToStr(recentlyRemind.getTime()));
                                remoteViews.setTextViewText(R.id.tv_text, recentlyRemind.getTitle());
                                Intent intent1 = new Intent();
                                intent1.putExtra("remind", recentlyRemind);
                                PendingIntent intent = PendingIntent.getActivity(MainActivity.this, NOTIFICATION, intent1, 0);
                                remoteViews.setOnClickPendingIntent(R.id.rl_notification, intent);
                                Notification notification = new NotificationCompat.Builder(MainActivity.this,"CHANNEL_ID")
                                        .setContent(remoteViews)
                                        .setWhen(System.currentTimeMillis())
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .setPriority(Notification.PRIORITY_MAX)
                                        .setSmallIcon(R.mipmap.icon)
                                        .build();
                                notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                long[] vibrates = { 0, 1000, 1000, 1000 };
                                notification.vibrate = vibrates;
                                manager.notify(1,notification);

                            } else {
                                //打开一个锁屏的界面

                            }
                            recentlyMinutes = Long.MAX_VALUE;
                            //重置所有
                            for (Remind remind : remindList) {
                                measureRemindTime(remind);
                            }

                        }
                    });
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    //计算最近提醒的时间，每一次启动应用、通知过一次的时候计算
    public void measureRemindTime(Remind remind) {

            if (TextUtils.isEmpty(remind.getAdvance()) || remind.getAdvance().contains("-1")) {
                //代表尚未设置完成的，就不用管了
                return;
            }

            //先拿到系统时间,忽略秒数
            int[] currentDays = DateUtil.getDay(System.currentTimeMillis());
            long currentMinutes = DateUtil.intArrayToLong(currentDays);
            //计算每个提醒的时间
            int[] remindDays = DateUtil.getDay(remind.getTime());
            //提醒的开始时间，忽略秒数，如果重复提醒满足，应当重新设置这个值
            long remindMinutes = DateUtil.intArrayToLong(remindDays);

            //根据有没有重复来计算,
            //只有当前时间大于第一次开始提醒的时间才开始重复
            if (currentMinutes > remindMinutes) {
                int repeatType = remind.getRepeatType();
                int repeatInterval = remind.getRepeatInterval();
                List<String> repeatValueList = JsonUtil.jsonToStrList(remind.getRepeatValue());
                if (repeatType == 0) {
                    //不重复

                } else if (repeatType == 1){
                    //按年重复

                    //因为后续不需要用到currentDays，所以直接修改这个值应该没有问题
                    //得到最近的符合的年份(去除今年)
                    while (currentDays[0] != remindDays[0] && (currentDays[0] - remindDays[0]) % repeatInterval != 0) {
                        currentDays[0]++;
                    }

                    for (String repeatValueStr:repeatValueList) {
                        //计算月份
                        int monthValue = Integer.parseInt(repeatValueStr);
                        currentDays[1] = monthValue;
                        //日期暂时定死为20
                        currentDays[2] = 20;
                        long repeatRemindTime = DateUtil.intArrayToLong(currentDays);
                        remindMinutes = repeatRemindTime;
                        if (repeatRemindTime <= recentlyMinutes) {
                            if (recentlyMinutes > System.currentTimeMillis()) {
                                recentlyMinutes = repeatRemindTime;
                                recentlyRemind = remind;
                            }
                        }
                    }

                } else if (repeatType == 2) {
                    //按月重复
                    //得到最近的符合的月份(去除今月)
                    while (currentDays[1] != remindDays[1] && (currentDays[1] - remindDays[1]) % repeatInterval != 0) {
                        currentDays[1]++;
                    }
                    //如果月份大于12
                    if (currentDays[1] > 12) {
                        currentDays[0]++;
                        currentDays[1] -= 12;
                    }
                    //如果当前年份减去提醒开始的年份取余以多少年重复一次的时间 等于 0则代表该年份可以
                    for (String repeatValueStr:repeatValueList) {
                        //计算日期
                        int dayValue = Integer.parseInt(repeatValueStr);
                        currentDays[2] = dayValue;
                        //如果日期相等才可以继续
                        long repeatRemindTime = DateUtil.intArrayToLong(currentDays);
                        remindMinutes = repeatRemindTime;
                        if (repeatRemindTime <= recentlyMinutes) {
                            if (repeatRemindTime > System.currentTimeMillis()) {
                                recentlyMinutes = repeatRemindTime;
                                recentlyRemind = remind;
                            }
                        }

                    }
                } else if (repeatType == 3) {
                    //按日重复
                    //得到最近的符合的天数(去除今天)
                    while (currentDays[2] != remindDays[2] && (currentDays[2] - remindDays[2]) % repeatInterval != 0) {
                        currentDays[2]++;
                    }
                    //如果日期大于30号，跳到下个月
                    if (currentDays[2] > 30) {
                        currentDays[1]++;
                        currentDays[2] -= 30;
                    }
                    //如果月份大于12
                    if (currentDays[1] > 12) {
                        currentDays[0]++;
                        currentDays[1] -= 12;
                    }
                    long repeatRemindTime = DateUtil.intArrayToLong(currentDays);
                    remindMinutes = repeatRemindTime;
                    if (repeatRemindTime <= recentlyMinutes) {
                        if (repeatRemindTime > System.currentTimeMillis()) {
                            recentlyMinutes = repeatRemindTime;
                            recentlyRemind = remind;
                        }
                    }

                } else if (repeatType == 4) {
                    //按周重复
                    long oneWeekTime = 7 * 24 * 60 * 60 * 1000;

                }
            }

            //必须先计算完重复的时间才能再计算提前提醒的时间，因为重复的也需要提前提醒
            //拿到提前提醒的时间
            List<String> advanceList = JsonUtil.jsonToStrList(remind.getAdvance());

            for (String advanceTimeStr : advanceList) {
                long advanceTime = Long.parseLong(advanceTimeStr);
                if (remindMinutes - advanceTime < recentlyMinutes) {
                    if (remindMinutes - advanceTime > System.currentTimeMillis()) {
                        recentlyMinutes = remindMinutes - advanceTime;
                        recentlyRemind = remind;
                    }

                }
            }

    }

    private void addNode(Remind remind) {
        SecondNode secondNode = new SecondNode(remind, null);
        if (isCompletedData(remind)) {
            secondNodeListCompleted.add(secondNode);
        }
        if (isTodayData(remind)) {
            secondNodeListToday.add(secondNode);
        }
        if (isNextDaysData(remind)) {
            secondNodeListNextDays.add(secondNode);
        }
        if (isNotScheduledData(remind)) {
            secondNodeListNotScheduled.add(secondNode);
        }
        if (isOverdueData(remind)) {
            secondNodeListOverdue.add(secondNode);
        }
    }


    private boolean isCompletedData(Remind remind)  {
        if(remind.isComplete()) {
            return true;
        }
        return false;
    }

    private boolean isNotScheduledData(Remind remind)  {
        if((TextUtils.isEmpty(remind.getAdvance()) || remind.getAdvance().contains("-1")) && !isCompletedData(remind)) {
            return true;
        }
        return false;
    }

    private boolean isNextDaysData(Remind remind)  {
        int currentDay = DateUtil.getYearMonthDay(System.currentTimeMillis());
        int remindDay = DateUtil.getYearMonthDay(remind.getTime());
        if(remindDay - currentDay > 0 && remindDay-currentDay <= 7 && !isNotScheduledData(remind) && !isCompletedData(remind)) {
            return true;
        }
        return false;
    }

    private boolean isTodayData(Remind remind)  {
        int currentDay = DateUtil.getYearMonthDay(System.currentTimeMillis());
        int remindDay = DateUtil.getYearMonthDay(remind.getTime());
        if(currentDay == remindDay && !isNotScheduledData(remind) && !isCompletedData(remind)) {
            return true;
        }
        return false;
    }

    private boolean isOverdueData(Remind remind)  {
        if(remind.getTime() < System.currentTimeMillis() && !remind.isComplete() && !isNotScheduledData(remind)) {
            return true;
        }
        return false;
    }

    private void initMainView() {
        //侧边栏
        mNavigation = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        initNavigation();

        currentText = getString(R.string.inbox);

        mIbAdd = findViewById(R.id.ib_main_add);
        mTvTitle = findViewById(R.id.tv_title);
        mIbLeft = findViewById(R.id.ib_left);
        mIbMore = findViewById(R.id.ib_right);
        long_select = findViewById(R.id.long_select);

        //RecycleView
        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new MyLinearLayoutManager(this));
        mAdapter = new NodeTreeAdapter();

        mRvMain.setAdapter(mAdapter);
        View headerView = View.inflate(getApplicationContext(), R.layout.item_header, null);
        headerTextView = headerView.findViewById(R.id.tv_title_center);
        mAdapter.setHeaderView(headerView);

        mAdapter.setList(firstNodeList);

        setAdapterClickEvent();

        mRvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见view的位置
                    int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if (firstItemPosition == 0) {
                        mTvTitle.setVisibility(View.GONE);
                    } else {
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


    private void setAdapterClickEvent() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //点击展开，这里使用payload进行增量刷新（避免整个item刷新导致的闪烁，不自然）
//                mRvMain.getRecycledViewPool().clear();
                mAdapter.expandOrCollapse(position, true, true, NodeTreeAdapter.EXPAND_COLLAPSE_PAYLOAD);
                //同样需要屏蔽一级菜单
                if (view.findViewById(R.id.item_count) == null) {
                    SecondNode secondNode = ((SecondNode)adapter.getItem(position));
                    Remind remind = secondNode.getRemindData();
                    if(isLongSelect) {
                        CheckedTextView checkedTextView = view.findViewById(R.id.cb_item_complete);
                        checkedTextView.setBackground(getResources().getDrawable(R.drawable.item_second_checkbox));
                        checkedTextView.setChecked(!checkedTextView.isChecked());
                        if(checkedTextView.isChecked()) {
                            checkedRemindList.add(remind);
                        }else {
                            checkedRemindList.remove(remind);
                        }
                        mTvTitle.setText(checkedRemindList.size() + " " + getString(R.string.select));
                        mTvTitle.setVisibility(View.VISIBLE);
                    } else {
                        editingNode = secondNode;
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("remind", remind);
                        startActivityForResult(intent, EDIT_REMIND);
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
                    checkedTextView.setBackground(getDrawable(R.drawable.item_second_checkbox));
                    checkedTextView.setChecked(!checkedTextView.isChecked());
                    isLongSelect = true;
                    if(checkedTextView.isChecked()) {
                        checkedRemindList.add(((SecondNode)adapter.getItem(position)).getRemindData());
                    }else {
                        checkedRemindList.remove(((SecondNode)adapter.getItem(position)).getRemindData());
                    }
                    mIbAdd.setVisibility(View.GONE);
                    mIbLeft.setImageDrawable(getResources().getDrawable(R.mipmap.close));
                    mTvTitle.setText(checkedRemindList.size() + " " + getString(R.string.select));
                    mTvTitle.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });
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
        currentText = getString(R.string.next_seven_days);
        mTitleLiveData.setValue(getString(R.string.next_seven_days));
        reloadNextSevenDays();
        headerTextView.setText(getString(R.string.next_seven_days));
    }

    //切换到Today下
    private void changeTodayView() {
        currentText = getString(R.string.today);
        mTitleLiveData.setValue(getString(R.string.today));
        reloadToday();
        headerTextView.setText(getString(R.string.today));

    }

    //切换到Inbox下
    private void changeInboxView() {
        currentText = getString(R.string.inbox);
        mTitleLiveData.setValue(getString(R.string.inbox));
        reloadInbox();
        headerTextView.setText(getString(R.string.inbox));

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
                if (isLongSelect) {
                    exitLongClick();
                }else {
                    mDrawerLayout.openDrawer(mNavigation);
                }

                break;
            case ADD_CLICK:

                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_CLICK);
                overridePendingTransition(0,0);
                break;
            case MORE_CLICK:

                View popupView = View.inflate(getApplicationContext(), R.layout.popupwindow_main, null);
                PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                TextView tv_hide = popupView.findViewById(R.id.tv_hide);

                if(isHide) {
                    tv_hide.setText(R.string.show_completed);
                }else {
                    tv_hide.setText(R.string.hide_completed);
                }

                popupWindow.showAsDropDown(mIbMore, 32 - popupWindow.getContentView().getMeasuredWidth(), 0);
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

                break;

            case SELECT_ALL:
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

            case DELETE:
                for (Remind checkedRemind : checkedRemindList) {
                    if (remindList.contains(checkedRemind)) {
                        remindList.remove(checkedRemind);
                    }
                    AppContext.executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            repository.deleteReminds(checkedRemind);
                        }
                    });
                    SecondNode secondNode = new SecondNode(checkedRemind, null);
                    mAdapter.remove(secondNode);
                }
                exitLongClick();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CLICK) {
            if (data != null) {
                Remind remind = data.getParcelableExtra("remind");
                remindList.add(remind);
                measureRemindTime(remind);
                SecondNode secondNode = new SecondNode(remind, null);
                firstNodeList.clear();
                if (isOverdueData(remind) && !isCompletedData(remind)) {
                    secondNodeListOverdue.add(secondNode);
                    firstNodeOverdue.setChildNode(secondNodeListOverdue);
                }
                firstNodeList.add(firstNodeOverdue);
                if (isTodayData(remind) && !isCompletedData(remind)) {
                    secondNodeListToday.add(secondNode);
                    firstNodeToday.setChildNode(secondNodeListToday);
                }
                firstNodeList.add(firstNodeToday);
                if (isNextDaysData(remind) && !isCompletedData(remind)) {
                    secondNodeListNextDays.add(secondNode);
                    firstNodeNextDays.setChildNode(secondNodeListNextDays);
                }
                firstNodeList.add(firstNodeNextDays);
                if (isNotScheduledData(remind) && !isCompletedData(remind)) {
                    secondNodeListNotScheduled.add(secondNode);
                    firstNodeNotScheduled.setChildNode(secondNodeListNotScheduled);
                }
                firstNodeList.add(firstNodeNotScheduled);
                if (isCompletedData(remind)) {
                    secondNodeListCompleted.add(secondNode);
                    firstNodeCompleted.setChildNode(secondNodeListCompleted);
                }
                if (!isHide) {
                    firstNodeList.add(firstNodeCompleted);
                }

                mAdapter.setList(firstNodeList);
            }
        } else if (requestCode == EDIT_REMIND) {
            if (data != null) {
                //更新数据
                Remind remind = data.getParcelableExtra("remind");
                boolean isDelete = data.getBooleanExtra("isDelete", false);
                if (isDelete) {
                    //删除

                } {
                    //保存

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
        mIbAdd.setVisibility(View.VISIBLE);
        long_select.setVisibility(View.INVISIBLE);
        SecondProvider.isSelectAll = false;
        mAdapter.setList(firstNodeList);
        checkedRemindList.clear();
        mIbLeft.setImageDrawable(getResources().getDrawable(R.mipmap.sidebar));
        mTvTitle.setText(currentText);
        mTvTitle.setVisibility(View.INVISIBLE);
    }

}