package com.example.remind.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.remind.R;
import com.example.remind.utils.DateUtil;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.MonthCalendar;
import com.necer.enumeration.CheckModel;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class SetTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SET_TIME = 0;
    private static final int SET_REMIND = 1;
    private MonthCalendar monthCalendar;
    private TextView mTvSetTime;
    private TextView mTvSetRemind;
    private TextView mTvSetRepeat;
    private String[] timeData;
    private String[] remindData;
    private ImageButton mIbTime;
    private ImageButton mIbClearRemind;
    private ImageButton mIbClearRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isDialog = getIntent().getBooleanExtra("isDialog", true);
        if(isDialog) {
            setContentView(R.layout.activity_set_time_dialog);
        }else {
            setContentView(R.layout.activity_set_time);
        }

        initUI();

    }

    private void initUI() {
        initMonth();
        initSetTime();
        initReminder();
        initRepeat();

        ImageButton ib_pre = findViewById(R.id.ib_pre);
        ImageButton ib_next = findViewById(R.id.ib_next);

        ib_pre.setOnClickListener(this);
        ib_next.setOnClickListener(this);
    }

    private void initRepeat() {
        mTvSetRepeat = findViewById(R.id.tv_set_repeat);
        mIbClearRepeat = findViewById(R.id.ib_clear_repeat);

        mTvSetRepeat.setOnClickListener(this);
        mIbClearRepeat.setOnClickListener(this);
    }

    private void initReminder() {
        mTvSetRemind = findViewById(R.id.tv_set_remind);
        mIbClearRemind = findViewById(R.id.ib_clear_remind);

        mTvSetRemind.setOnClickListener(this);
        mIbClearRemind.setOnClickListener(this);
    }

    private void initSetTime() {
        mTvSetTime = findViewById(R.id.tv_set_time);
        mIbTime = findViewById(R.id.ib_clear_time);

        mTvSetTime.setOnClickListener(this);
        mIbTime.setOnClickListener(this);
    }

    private void initMonth() {
        monthCalendar = findViewById(R.id.monthCalendar);
        TextView tv_time = findViewById(R.id.tv_time);
        monthCalendar.setCheckMode(CheckModel.SINGLE_DEFAULT_UNCHECKED);
        List<String> list = new ArrayList<>();
        //获取当前时间
        int[] currentDay = DateUtil.getDay(System.currentTimeMillis());
        list.add(currentDay[0] + "-" + currentDay[1] + "-" + currentDay[2]);
        monthCalendar.setCheckedDates(list);
        tv_time.setText(DateUtil.numberToMonth(currentDay[1]) + " " + currentDay[0]);

        monthCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {

            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                tv_time.setText(DateUtil.numberToMonth(month) + " " + year);
            }

        });
    }

    /**
     * 取消
     * @param view
     */
    public void left(View view) {
        finish();
    }

    /**
     * 保存
     * @param view
     */
    public void right(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_pre:
                monthCalendar.toLastPager();
                break;

            case R.id.ib_next:
                monthCalendar.toNextPager();
                break;
            case R.id.tv_set_time:
                Intent intent = new Intent(this, SetHourActivity.class);
                if(timeData != null) {
                    intent.putExtra("time", timeData);
                }
                startActivityForResult(intent,SET_TIME);
                overridePendingTransition(0,0);
                break;
            case R.id.tv_set_remind:
                Intent intent1 = new Intent(this, SetRemindActivity.class);
                if(remindData != null) {
                    intent1.putExtra("remind", remindData);
                }
                startActivityForResult(intent1,SET_REMIND);
                overridePendingTransition(0,0);
                break;

            case R.id.tv_set_repeat:

                break;
            case R.id.ib_clear_time:
                mTvSetTime.setTextColor(Color.parseColor("#50000000"));
                mTvSetTime.setText(R.string.no_time);
                timeData = null;
                mIbTime.setVisibility(View.GONE);
                break;
            case R.id.ib_clear_remind:

                break;
            case R.id.ib_clear_repeat:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SET_TIME) {
            if(data != null) {
                timeData = data.getStringArrayExtra("time");
                mTvSetTime.setText(timeData[0] + ":" + timeData[1] + " " + timeData[2]);
                mTvSetTime.setTextColor(Color.parseColor("#000000"));
                mIbTime.setVisibility(View.VISIBLE);
            }
        }
    }
}