package com.example.bonoremind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.bonoremind.base.BaseActivity;
import com.example.bonoremind.databinding.ActivitySetTimeBinding;
import com.example.bonoremind.db.entity.Remind;
import com.example.bonoremind.vm.SetTimeViewModel;
import com.necer.calendar.MonthCalendar;
import com.necer.enumeration.CheckModel;

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
    private Remind resultRemind;
    private TextView mTvSetTime;
    private TextView mTvSetRemind;
    private TextView mTvSetRepeat;
    private ImageButton mIbClearTime;
    private ImageButton mIbClearRemind;
    private ImageButton mIbClearRepeat;
    private long remindTime;
    private MutableLiveData<String> monthTextLiveData;
    private MutableLiveData<String> remindTextLiveData;
    private MutableLiveData<String> repeatTextLiveData;
    private MutableLiveData<String> timeTextLiveData;

    @Override
    protected int getLayoutId() {
        boolean isDialog = getIntent().getBooleanExtra("isDialog", false);
        if (isDialog) {
           return R.layout.activity_set_time_dialog;
        }
        return R.layout.activity_set_time;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initUI();
        initViewModelVar();
    }

    private void initViewModelVar() {
        monthTextLiveData = viewModel.getMonthTextLiveData();
        remindTextLiveData = viewModel.getRemindTextLiveData();
        repeatTextLiveData = viewModel.getRepeatTextLiveData();
        timeTextLiveData = viewModel.getTimeTextLiveData();

    }

    private void initUI() {
        mTvSetTime = findViewById(R.id.tv_set_time);
        mTvSetRemind = findViewById(R.id.tv_set_remind);
        mTvSetRepeat = findViewById(R.id.tv_set_repeat);
        mIbClearTime = findViewById(R.id.ib_clear_time);
        mIbClearRemind = findViewById(R.id.ib_clear_remind);
        mIbClearRepeat = findViewById(R.id.ib_clear_repeat);

        //初始化月历
        MonthCalendar monthCalendar = findViewById(R.id.monthCalendar);
        monthCalendar.setCheckMode(CheckModel.SINGLE_DEFAULT_UNCHECKED);
        //一定不为空，默认是今天上午9点
        remindTime

    }

    private void initData() {
        //上一级一定是有数据的，可不用判空
        resultRemind = getIntent().getParcelableExtra("remind");
        remindTime = resultRemind.getTime();

    }

    @Override
    public void click(int mode) {
        switch (mode) {
            case SET_HOUR:

                break;
            case SET_REMINDER:

                break;
            case SET_REPEAT:

                break;
            case CLEAR_HOUR:

                break;
            case CLEAR_REMINDER:

                break;
            case CLEAR_REPEAT:

                break;
            case LAST_MONTH:

                break;
            case NEXT_MONTH:

                break;
            case CANCEL:

                break;
            case SAVE:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_HOUR) {

        } else if (requestCode == SET_REMINDER) {

        } else if (requestCode == SET_REPEAT) {

        }
    }
}
