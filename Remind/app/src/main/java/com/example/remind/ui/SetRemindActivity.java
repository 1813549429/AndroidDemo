package com.example.remind.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.remind.R;
import com.example.remind.adapter.RemindAdapter;

import java.util.ArrayList;
import java.util.List;

public class SetRemindActivity extends AppCompatActivity {

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_remind);

        initData();
        initUI();

    }

    private void initData() {
        list = new ArrayList<>();
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
        RecyclerView mRvReminder = findViewById(R.id.rv_reminder);
        RemindAdapter remindAdapter = new RemindAdapter(getApplicationContext(), list);
        mRvReminder.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvReminder.setAdapter(remindAdapter);
    }
}