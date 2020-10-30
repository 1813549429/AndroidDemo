package com.example.remind.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remind.R;
import com.example.remind.db.entity.Remind;
import com.example.remind.utils.DateUtil;
import com.example.remind.utils.JsonUtil;
import com.example.remind.utils.StatusBarUtil;
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
    private static final int SET_REPEAT = 2;
    private MonthCalendar monthCalendar;
    private TextView mTvSetTime;
    private TextView mTvSetRemind;
    private TextView mTvSetRepeat;
    private String[] timeData;
    private String remindData;
    private String repeatData;
    private ImageButton mIbTime;
    private ImageButton mIbClearRemind;
    private ImageButton mIbClearRepeat;
    private int[] currentDate = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.activity_bg));

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

        currentDate[0] = currentDay[0];
        currentDate[1] = currentDay[1];
        currentDate[2] = currentDay[2];

        monthCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {

            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                tv_time.setText(DateUtil.numberToMonth(month) + " " + year);
                //保存日期
                if(localDate != null) {
                    int day = localDate.getDayOfMonth();
                    currentDate[0] = year;
                    currentDate[1] = month;
                    currentDate[2] = day;
                }
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

        String remindJson = JsonUtil.strRemindToJson("-1");
        Remind remind = new Remind();

        boolean isSetting = true;
        if (timeData != null) {
            int hour = Integer.parseInt(timeData[0]);
            if (timeData[2].equals(getString(R.string.pm))) {
                hour += 12;
            }
            currentDate[3] = hour;
            currentDate[4] = Integer.parseInt(timeData[1]);
        }else {
            isSetting = false;
        }
        remind.setTime(DateUtil.intArrayToLong(currentDate));

        if (remindData != null) {
            remindJson = JsonUtil.strRemindToJson(remindData);
        }else {
            isSetting = false;
        }
        remind.setAdvance(remindJson);

        remind.setRepeatType(0);
        if (repeatData != null) {
            setRepeat(remind, repeatData);
        }else {
            isSetting = false;
        }
        remind.setSetting(isSetting);

        Intent intent = new Intent();
        intent.putExtra("setTime", remind);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setRepeat(Remind remind, String repeatData) {
        if (repeatData.equals(getString(R.string.none))) {
            remind.setRepeatType(0);
        } else if (repeatData.equals(getString(R.string.daily))) {
            remind.setRepeatType(4);
            remind.setRepeatInterval(1);
        } else if (repeatData.equals(getString(R.string.every_weekday))) {
            remind.setRepeatType(3);
            remind.setRepeatInterval(1);
            remind.setRepeatValue(JsonUtil.strToJson("1,2,3,4,5"));
        } else if (repeatData.equals(getString(R.string.weekly))) {
            remind.setRepeatType(3);
            remind.setRepeatInterval(1);
            remind.setRepeatValue(JsonUtil.strToJson("2"));
        } else if (repeatData.equals(getString(R.string.daily))) {
            remind.setRepeatType(2);
            remind.setRepeatInterval(1);
            remind.setRepeatValue(JsonUtil.strToJson("20"));
        } else if (repeatData.equals(getString(R.string.daily))) {
            remind.setRepeatType(1);
            remind.setRepeatInterval(1);
            remind.setRepeatValue(JsonUtil.strToJson("10"));
        }
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
                Intent intent2 = new Intent(this, SetRepeatActivity.class);
                if(repeatData != null) {
                    intent2.putExtra("repeat", repeatData);
                }
                startActivityForResult(intent2, SET_REPEAT);
                overridePendingTransition(0 ,0);
                break;
            case R.id.ib_clear_time:
                mTvSetTime.setTextColor(Color.parseColor("#50000000"));
                mTvSetTime.setText(R.string.no_time);
                timeData = null;
                mIbTime.setVisibility(View.GONE);
                break;
            case R.id.ib_clear_remind:
                mTvSetRemind.setTextColor(Color.parseColor("#50000000"));
                mTvSetRemind.setText(R.string.no_reminder);
                remindData = null;
                mIbClearRemind.setVisibility(View.GONE);
                break;
            case R.id.ib_clear_repeat:
                mTvSetRepeat.setTextColor(Color.parseColor("#50000000"));
                mTvSetRepeat.setText(R.string.no_reminder);
                repeatData = null;
                mIbClearRepeat.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_TIME) {
            if (data != null) {
                timeData = data.getStringArrayExtra("time");
                mTvSetTime.setText(timeData[0] + ":" + timeData[1] + " " + timeData[2]);
                mTvSetTime.setTextColor(Color.parseColor("#000000"));
                mIbTime.setVisibility(View.VISIBLE);
            }
        }else if (requestCode == SET_REMIND) {
            if (data != null) {
                remindData = data.getStringExtra("remind");
                mTvSetRemind.setText(remindData);
                mTvSetRemind.setTextColor(Color.parseColor("#000000"));
                mIbClearRemind.setVisibility(View.VISIBLE);
            }
        }else if (requestCode == SET_REPEAT) {
            if(data != null) {
                repeatData = data.getStringExtra("repeat");
                mTvSetRepeat.setText(repeatData);
                mTvSetRepeat.setTextColor(Color.parseColor("#000000"));
                mIbClearRepeat.setVisibility(View.VISIBLE);
            }
        }
    }

}