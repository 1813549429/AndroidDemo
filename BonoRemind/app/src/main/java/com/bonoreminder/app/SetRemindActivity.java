package com.bonoreminder.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonoreminder.app.adapter.RemindAdapter;
import com.bonoreminder.app.base.BaseActivity;
import com.bonoreminder.app.databinding.ActivitySetRemindBinding;
import com.bonoreminder.app.db.entity.Remind;
import com.bonoreminder.app.utils.DateUtil;
import com.bonoreminder.app.utils.JsonUtil;
import com.bonoreminder.app.vm.SetRemindViewModel;

import java.util.ArrayList;
import java.util.List;

public class SetRemindActivity extends BaseActivity<SetRemindViewModel, ActivitySetRemindBinding> {

    public static final int CANCEL = 0;
    public static final int SAVE = 1;
    private List<String> list;
    private List<String> checkedList;
    private long preClickTime;
    private RemindAdapter mRemindAdapter;
    private Remind resultRemind;
    public static String noSetRemindText = " (9:00AM)";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_remind;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView.setHolder(this);
        bindingView.setVm(viewModel);

        initData();
        initUI();
    }

    private void initData() {
        list = new ArrayList<>();
        checkedList = new ArrayList<>();

        //初始化数据
        resultRemind = getIntent().getParcelableExtra("remind");
        //提前提醒的Json字符串,存储的是时间戳，需要转换格式
        String remindStr = DateUtil.advanceJsonToStr(getApplicationContext(), resultRemind.getAdvance());
        Log.d("TAG", remindStr);
        if (!TextUtils.isEmpty(remindStr)) {
            //如果不为空，将字符串转化成List
            checkedList = strToList(remindStr);
        }else {
            //设置默认选中
            checkedList.add(getString(R.string.none));
        }

        int[] remindDate = DateUtil.getDay(resultRemind.getTime());

        String initRemindTime = noSetRemindText;
        //如果已经设置remind时间，则不需要 9：00AM
        if(resultRemind.isSetting()) {
            initRemindTime = "";
        }

        String none = getString(R.string.none);
        String oneTime = getString(R.string.one_time) + initRemindTime;
        String fiveMinsEarly = getString(R.string.five_mins_early) + initRemindTime;
        String thirtyMinsEarly = getString(R.string.thirty_mins_early) + initRemindTime;
        String oneHourEarly = getString(R.string.one_hour_early) + initRemindTime;
        String oneDayEarly = getString(R.string.one_day_early) + initRemindTime;
        String twoDayEarly = getString(R.string.two_day_early) + initRemindTime;
        String threeDayEarly = getString(R.string.three_day_early) + initRemindTime;
        String oneWeekEarly = getString(R.string.one_week_early) + initRemindTime;

        list.add(none);
        list.add(oneTime);
        list.add(fiveMinsEarly);
        list.add(thirtyMinsEarly);
        list.add(oneHourEarly);
        list.add(oneDayEarly);
        list.add(twoDayEarly);
        list.add(threeDayEarly);
        list.add(oneWeekEarly);
    }

    private void initUI() {

        RecyclerView mRvReminder = findViewById(R.id.rv_reminder);
        mRemindAdapter = new RemindAdapter(getApplicationContext(), list);

        //设置选中的条目
        mRemindAdapter.setCheckedList(checkedList);

        mRvReminder.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvReminder.setAdapter(mRemindAdapter);

    }

    @Override
    public void click(int mode) {
        //重复点击的容错处理
        if (System.currentTimeMillis() - preClickTime < 600) {
            return;
        }
        preClickTime = System.currentTimeMillis();

        switch (mode) {
            case CANCEL:
                finish();
                break;
            case SAVE:
                //首先拿到选中的
                checkedList = mRemindAdapter.getCheckedList();
                //先将List转化成以逗号分割的字符产
                String str = listToStr(checkedList);
                //再将这个字符串List转化成advance的Json字符串 TODO 这里需要设置的是以时间戳格式的
                resultRemind.setAdvance(JsonUtil.strRemindToJson(str));
                Intent intent = new Intent();
                intent.putExtra("remind", resultRemind);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    //将一个list数组转化成以逗号分隔的字符串
    private String listToStr(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            //去掉  （9:00AM）这几个字样
            if (str.contains(noSetRemindText)) {
                str = str.substring(0,str.indexOf(noSetRemindText));
            }
            builder.append(str + ",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length()-1);
        }
        return builder.toString();
    }

    //将以逗号分隔的字符串分割成一个List
    private List<String> strToList(String remindStr) {
        List<String> remindList = new ArrayList<>();
        String[] strs = remindStr.split(",");
        for (String s: strs) {
            if (!resultRemind.isSetting()) {
                s += noSetRemindText;
            }
            remindList.add(s);
        }
        return remindList;
    }
}
