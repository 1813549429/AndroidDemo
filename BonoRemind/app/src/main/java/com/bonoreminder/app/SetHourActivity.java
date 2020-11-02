package com.bonoreminder.app;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bonoreminder.app.base.BaseActivity;
import com.bonoreminder.app.databinding.ActivitySetHourBinding;
import com.bonoreminder.app.utils.DateUtil;
import com.bonoreminder.app.view.DatePickerView;
import com.bonoreminder.app.vm.SetHourViewModel;

import java.util.ArrayList;
import java.util.List;

public class SetHourActivity extends BaseActivity<SetHourViewModel, ActivitySetHourBinding> {

    public static final int CANCEL = 0;
    public static final int SAVE = 1;
    /**
     * 保存日期时间，分别代表年月日时分周
     */
    private int[] remindTime;
    private List<String> hour;
    private List<String> minute;
    private List<String> interval;
    private long preClickTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_hour;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView.setHolder(this);
        bindingView.setVm(viewModel);

        initData();
        initUI();

    }

    private void initUI() {
        DatePickerView pv_hour = findViewById(R.id.pv_hour);
        DatePickerView pv_minute = findViewById(R.id.pv_minute);
        DatePickerView pv_interval = findViewById(R.id.pv_interval);

        //设置时间选择器的总范围
        pv_hour.setData(hour);
        pv_minute.setData(minute);
        pv_interval.setData(interval);

        executeAnimator(pv_hour);
        executeAnimator(pv_minute);
        executeAnimator(pv_interval);

        pv_hour.setIsLoop(false);
        pv_interval.setIsLoop(false);

        //设置时间选择器的初始值
        if(remindTime[3] >= 12) {
            pv_hour.setSelected(DateUtil.formatTimeUnit(remindTime[3] - 12));
            pv_interval.setSelected(getString(R.string.pm));
        }else {
            pv_hour.setSelected(DateUtil.formatTimeUnit(remindTime[3]));
            pv_interval.setSelected(getString(R.string.am));
        }
        pv_minute.setSelected(remindTime[4]);

        //设置时间选择器的监听
        pv_hour.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                remindTime[3] = Integer.parseInt(text);
            }
        });
        pv_minute.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                remindTime[4] = Integer.parseInt(text);
            }
        });
        pv_interval.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                if (text.equals(getString(R.string.pm))) {
                    remindTime[3] += 12;
                }
            }
        });
    }

    private void initData() {
        remindTime = getIntent().getIntArrayExtra("hours");
        //设置时间选择器的数值
        hour = new ArrayList<>();
        minute = new ArrayList<>();
        interval = new ArrayList<>();

        for (int i=0; i<12; i++) {
            hour.add(DateUtil.formatTimeUnit(i));
        }
        for (int i=0; i<60; i++) {
            minute.add(DateUtil.formatTimeUnit(i));
        }
        interval.add(getString(R.string.pm));
        interval.add(getString(R.string.am));
    }

    @Override
    public void click(int mode) {

        //重复点击的容错处理
        if (System.currentTimeMillis() - preClickTime < 300) {
            return;
        }
        preClickTime = System.currentTimeMillis();

        switch (mode) {
            case CANCEL:
                finish();
                break;
            case SAVE:
                Intent intent = new Intent();
                intent.putExtra("hours", remindTime);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }
}
