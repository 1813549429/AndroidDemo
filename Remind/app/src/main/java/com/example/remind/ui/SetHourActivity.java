package com.example.remind.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.remind.R;
import com.example.remind.utils.DateUtil;
import com.example.remind.view.DatePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetHourActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> hour, minute, interval;
    private DatePickerView mPvHour;
    private DatePickerView mPvMinute;
    private DatePickerView mPvInterval;
    private String[] resultDate = new String[3];
    private int MAX_MINUTES = 60;
    private int MIN_MINUTES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_hour);
        initArrayList();
        initUI();
        addListener();
        if(getIntent() != null && getIntent().getStringArrayExtra("time") != null) {

            resultDate = getIntent().getStringArrayExtra("time");
            mPvHour.setSelected(formatTimeUnit(Integer.parseInt(resultDate[0])));
            mPvMinute.setSelected(formatTimeUnit(Integer.parseInt(resultDate[1])));
            mPvInterval.setSelected(resultDate[2]);
        }else {
            setSelectTime(System.currentTimeMillis());
        }
    }

    /**
     * 设置默认选中的时间
     */
    private void setSelectTime(long nowLongTime) {

        int[] currentTime = DateUtil.getDay(nowLongTime);
        if(currentTime[3] >= 12) {
            mPvHour.setSelected(formatTimeUnit(currentTime[3] - 12));
            mPvInterval.setSelected(getString(R.string.pm));
            resultDate[0] = currentTime[3] - 12 + "";
            resultDate[2] = getString(R.string.pm);
        }else {
            mPvHour.setSelected(formatTimeUnit(currentTime[3]));
            mPvInterval.setSelected(getString(R.string.am));
            resultDate[0] = currentTime[3] + "";
            resultDate[2] = getString(R.string.am);
        }
        mPvMinute.setSelected(formatTimeUnit(currentTime[4]));
        resultDate[1] = currentTime[4] + "";
    }


    private void addListener() {
        mPvHour.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                resultDate[0] = text;
            }
        });
        mPvMinute.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                resultDate[1] = text;
            }
        });
        mPvInterval.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                resultDate[2] = text;
            }
        });
    }


    private void initArrayList() {
        hour = new ArrayList<>();
        minute = new ArrayList<>();
        interval = new ArrayList<>();

        for (int i=0; i<12; i++) {
            hour.add(formatTimeUnit(i));
        }
        for (int i=0; i<60; i++) {
            minute.add(formatTimeUnit(i));
        }
        Log.d("SetActivity", minute.size()+"");
        interval.add(getString(R.string.pm));
        interval.add(getString(R.string.am));

    }

    private void initUI() {
        mPvHour = findViewById(R.id.pv_hour);
        mPvMinute = findViewById(R.id.pv_minute);
        mPvInterval = findViewById(R.id.pv_interval);


        TextView tv_cancel = findViewById(R.id.tv_cancel);
        TextView tv_save = findViewById(R.id.tv_save);

        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        mPvHour.setData(hour);
        mPvMinute.setData(minute);
        mPvInterval.setData(interval);

        executeAnimator(mPvHour);
        executeAnimator(mPvMinute);
        executeAnimator(mPvInterval);

        mPvHour.setIsLoop(false);
        mPvInterval.setIsLoop(false);



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

            case R.id.tv_save:
                Intent intent = new Intent();
                intent.putExtra("time", resultDate);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }
}