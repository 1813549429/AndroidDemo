package com.example.bonoremind;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.bonoremind.base.BaseActivity;
import com.example.bonoremind.databinding.ActivitySetTimeBinding;
import com.example.bonoremind.db.entity.Remind;
import com.example.bonoremind.utils.DateUtil;
import com.example.bonoremind.utils.JsonUtil;
import com.example.bonoremind.vm.SetTimeViewModel;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.MonthCalendar;
import com.necer.enumeration.CheckModel;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class SetTimeActivity extends BaseActivity<SetTimeViewModel, ActivitySetTimeBinding> {


    public static final int SET_HOUR = 0;
    public static final int SET_REMINDER = 1;
    public static final int SET_REPEAT = 2;
    public static final int CLEAR_HOUR = 3;
    public static final int CLEAR_REMINDER = 4;
    public static final int CLEAR_REPEAT = 5;
    public static final int LAST_MONTH = 6;
    public static final int NEXT_MONTH = 7;
    public static final int CANCEL = 8;
    public static final int SAVE = 9;
    private TextView mTvSetTime;
    private TextView mTvSetRemind;
    private TextView mTvSetRepeat;
    private ImageButton mIbClearTime;
    private ImageButton mIbClearRemind;
    private ImageButton mIbClearRepeat;
    private MutableLiveData<String> monthTextLiveData;
    private MutableLiveData<String> remindTextLiveData;
    private MutableLiveData<String> repeatTextLiveData;
    private MutableLiveData<String> timeTextLiveData;

    /**
     * 最终的结果都应保存在这里
     */
    private Remind resultRemind;

    /**
     * 保存日期时间，分别代表年月日时分周
     */
    private int[] remindTime;
    private MonthCalendar monthCalendar;
    private long preClickTime;
    private String advanceText;
    private String repeatText;

    @Override
    protected int getLayoutId() {
        boolean isDialog = getIntent().getBooleanExtra("isDialog", false);
        return R.layout.activity_set_time;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView.setHolder(this);
        bindingView.setVm(viewModel);

        initData();
        initUI();
        initViewModelVar();
    }

    private void initViewModelVar() {
        remindTextLiveData = viewModel.getRemindTextLiveData();
        repeatTextLiveData = viewModel.getRepeatTextLiveData();
        timeTextLiveData = viewModel.getTimeTextLiveData();

        timeTextLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                int[] hoursTime = null;
                if (!s.equals(getString(R.string.no_time))) {
                    hoursTime = DateUtil.strToTime(s);
                }
                if (s.equals(getString(R.string.no_time)) || (!resultRemind.isSetting() && hoursTime[0] == 9 && hoursTime[1] == 0)) {
                    //上午九点可以认为是默认的
                    mIbClearTime.setVisibility(View.GONE);
                    mTvSetTime.setTextColor(Color.parseColor("#40000000"));
                    //如果是No time,直接更改TextView即可
                    mTvSetTime.setText(getString(R.string.no_time));
                } else {
                    //其余情况就不是默认的了
                    mIbClearTime.setVisibility(View.VISIBLE);
                    mTvSetTime.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        //设置初始的时间
        timeTextLiveData.setValue(DateUtil.timeToStr(DateUtil.intArrayToLong(remindTime)));
        if (!resultRemind.isSetting() && !TextUtils.isEmpty(advanceText)) {
            advanceText += " (9:00AM)";
        }
        remindTextLiveData.setValue(advanceText);
        remindTextLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //如果没有数据
                if (TextUtils.isEmpty(s) || s.equals(getString(R.string.no_reminder))) {
                    //不管用？
                    mTvSetRemind.setText(getString(R.string.no_reminder));
                    //设置颜色
                    mTvSetRemind.setTextColor(Color.parseColor("#40000000"));
                    mIbClearRemind.setVisibility(View.GONE);
                    return;
                }
                mTvSetRemind.setTextColor(Color.parseColor("#000000"));
                mIbClearRemind.setVisibility(View.VISIBLE);

            }
        });

        repeatTextLiveData.setValue(repeatText);
        repeatTextLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //如果没有数据
                if (TextUtils.isEmpty(s) || s.equals(getString(R.string.no_repeat)) || s.equals(getString(R.string.none))) {
                    //不管用？
                    mTvSetRepeat.setText(getString(R.string.no_repeat));
                    //设置颜色
                    mTvSetRepeat.setTextColor(Color.parseColor("#40000000"));
                    mIbClearRepeat.setVisibility(View.GONE);
                    return;
                }
                mTvSetRepeat.setTextColor(Color.parseColor("#000000"));
                mIbClearRepeat.setVisibility(View.VISIBLE);

            }
        });


    }

    private void initUI() {
        mTvSetTime = findViewById(R.id.tv_set_time);
        mTvSetRemind = findViewById(R.id.tv_set_remind);
        mTvSetRepeat = findViewById(R.id.tv_set_repeat);
        mIbClearTime = findViewById(R.id.ib_clear_time);
        mIbClearRemind = findViewById(R.id.ib_clear_remind);
        mIbClearRepeat = findViewById(R.id.ib_clear_repeat);

        //初始化月历
        monthTextLiveData = viewModel.getMonthTextLiveData();
        monthCalendar = findViewById(R.id.monthCalendar);
        monthCalendar.setCheckMode(CheckModel.SINGLE_DEFAULT_UNCHECKED);
        //一定不为空，默认是今天上午9点
        List<String> monthList = new ArrayList<>();
        monthList.add(remindTime[0] + "-" + remindTime[1] + "-" + remindTime[2]);
        monthCalendar.setCheckedDates(monthList);
        monthTextLiveData.setValue(DateUtil.numberToMonth(remindTime[1]) + " " + remindTime[0]);
        monthCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                monthTextLiveData.setValue(DateUtil.numberToMonth(month) + " " + year);
                if (localDate != null) {
                    int day = localDate.getDayOfMonth();
                    remindTime[0] = year;
                    remindTime[1] = month;
                    remindTime[2] = day;
                }
            }
        });

    }

    private void initData() {
        //上一级一定是有数据的，可不用判空
        resultRemind = getIntent().getParcelableExtra("remind");
        //初始化时间，转化成年月日时分的int数组形式。TODO 最后一定记得要把这个值重新赋回resultRemind
        remindTime = DateUtil.getDay(resultRemind.getTime());
        //初始化提前提醒
        advanceText = DateUtil.advanceJsonToStr(getApplicationContext(), resultRemind.getAdvance());
        //初始化重复
        repeatText = DateUtil.repeatToStr(getApplicationContext(), resultRemind);
    }

    @Override
    public void click(int mode) {

        //重复点击的容错处理
        if (System.currentTimeMillis() - preClickTime < 300) {
            return;
        }
        preClickTime = System.currentTimeMillis();

        switch (mode) {
            case SET_HOUR:
                //跳转到SetHourActivity
                Intent timeIntent = new Intent(SetTimeActivity.this, SetHourActivity.class);
                timeIntent.putExtra("hours", remindTime);
                startActivityForResult(timeIntent, SET_HOUR);
                overridePendingTransition(0,0);
                break;
            case SET_REMINDER:
                //跳转到SetRemindActivity
                Intent remindIntent = new Intent(SetTimeActivity.this, SetRemindActivity.class);
                remindIntent.putExtra("remind", resultRemind);
                startActivityForResult(remindIntent, SET_REMINDER);
                overridePendingTransition(0,0);
                break;
            case SET_REPEAT:
                Intent repeatIntent = new Intent(SetTimeActivity.this, SetRepeatActivity.class);
                repeatIntent.putExtra("remind", resultRemind);
                startActivityForResult(repeatIntent, SET_REPEAT);
                overridePendingTransition(0,0);
                break;
            case CLEAR_HOUR:
                remindTime[3] = 9;
                remindTime[4] = 0;
                resultRemind.setSetting(false);
                timeTextLiveData.setValue(getString(R.string.no_time));
                break;
            case CLEAR_REMINDER:
                advanceText = "";
                resultRemind.setAdvance(advanceText);
                remindTextLiveData.setValue(getString(R.string.no_reminder));
                break;
            case CLEAR_REPEAT:
                repeatText = "";
                resultRemind.setRepeatType(0);
                repeatTextLiveData.setValue(getString(R.string.no_repeat));
                break;
            case LAST_MONTH:
                monthCalendar.toLastPager();
                break;
            case NEXT_MONTH:
                monthCalendar.toNextPager();
                break;
            case CANCEL:
                finish();
                break;
            case SAVE:
                //重新将remindTime赋值给resultRemind
                resultRemind.setTime(DateUtil.intArrayToLong(remindTime));
                Intent intent = new Intent();
                intent.putExtra("remind", resultRemind);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_HOUR) {
            if (data != null) {
                remindTime = data.getIntArrayExtra("hours");
                resultRemind.setSetting(true);
                String timeText = DateUtil.timeToStr(DateUtil.intArrayToLong(remindTime));
                timeTextLiveData.setValue(timeText);
            }
        } else if (requestCode == SET_REMINDER) {
            if (data != null) {
                resultRemind = data.getParcelableExtra("remind");
                //首先将字符串转化成以逗号分割的字符串
                advanceText = DateUtil.advanceJsonToStr(getApplicationContext(), resultRemind.getAdvance());
                //这里应该拆分每个字符产加上去，然后再组装
                if (!resultRemind.isSetting() && !TextUtils.isEmpty(advanceText)) {
                    advanceText += " (9:00AM)";
                }
                remindTextLiveData.setValue(advanceText);
            }
        } else if (requestCode == SET_REPEAT) {
            if (data != null) {
                resultRemind = data.getParcelableExtra("remind");
                repeatText = DateUtil.repeatToStr(getApplicationContext(), resultRemind);
                Log.d("TAG", "repeatText:" + repeatText);
                repeatTextLiveData.setValue(repeatText);

            }
        }
    }
}
