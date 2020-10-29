package com.example.remind.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.remind.R;
import com.example.remind.adapter.RemindAdapter;
import com.example.remind.view.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SetRemindActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> list;
    private RemindAdapter mRemindAdapter;
    private List<String> checkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_remind);

        initData();
        initUI();

    }

    private void initData() {
        list = new ArrayList<>();
        checkedList = new ArrayList<>();
        String none = getString(R.string.none);
        String oneTime = getString(R.string.one_time);
        String fiveMinsEarly = getString(R.string.five_mins_early);
        String thirtyMinsEarly = getString(R.string.thirty_mins_early);
        String oneHourEarly = getString(R.string.one_hour_early);
        String oneDayEarly = getString(R.string.one_day_early);
        String twoDayEarly = getString(R.string.two_day_early);
        String threeDayEarly = getString(R.string.three_day_early);
        String oneWeekEarly = getString(R.string.one_week_early);

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
        //初始化RecycleView
        RecyclerView mRvReminder = findViewById(R.id.rv_reminder);
        mRemindAdapter = new RemindAdapter(getApplicationContext(), list);

        //初始化数据
        String remindStr = getIntent().getStringExtra("remind");
        if (!TextUtils.isEmpty(remindStr)) {
            checkedList = strToList(remindStr);
        }else {
            checkedList.add(getString(R.string.none));
        }
        mRemindAdapter.setCheckedList(checkedList);

        mRvReminder.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvReminder.setAdapter(mRemindAdapter);

        TextView tv_cancel = findViewById(R.id.tv_cancel);
        TextView tv_save = findViewById(R.id.tv_save);

        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                Intent intent = new Intent();
                intent.putExtra("remind", listToStr(mRemindAdapter.getCheckedList()));
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    private String listToStr(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(str + ",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length()-1);
        }
        return builder.toString();
    }

    private List<String> strToList(String remindStr) {
        List<String> remindList = new ArrayList<>();
        String[] strs = remindStr.split(",");
        for (String s: strs) {
            remindList.add(s);
        }
        return remindList;
    }


}