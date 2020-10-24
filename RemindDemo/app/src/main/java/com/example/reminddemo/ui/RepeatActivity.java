package com.example.reminddemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.reminddemo.data.LiveDataBus;
import com.example.reminddemo.R;
import com.example.reminddemo.db.RepeatStrategy;
import com.example.reminddemo.dao.DaoUtil;

public class RepeatActivity extends AppCompatActivity{

    private MutableLiveData<Object> repeatLiveData;
    private MutableLiveData<Object> repeatRemarkLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        getSupportActionBar().hide();
        initLiveData();
        initUI();
    }

    private void initLiveData() {
        LiveDataBus liveDataBus = LiveDataBus.get();
        repeatLiveData = liveDataBus.getChannel("repeatData");
        repeatRemarkLiveData = liveDataBus.getChannel("repeatRemark");
    }

    private void initUI() {
        RadioGroup radioGroup = findViewById(R.id.rg_repeat);
        String repeatRemark = (String) repeatRemarkLiveData.getValue();
        if (repeatRemark.equals(getString(R.string.ont_time))) {
            radioGroup.check(R.id.rb_one_time);
        }else if (repeatRemark.equals(getString(R.string.every_day))) {
            radioGroup.check(R.id.rb_every_day);
        }else if (repeatRemark.equals(getString(R.string.week_one_five))) {
            radioGroup.check(R.id.rb_week_one_five);
        }else if (repeatRemark.equals(getString(R.string.custom))) {
            radioGroup.check(R.id.rb_custom);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one_time:
                        RepeatStrategy repeatData = DaoUtil.getRepeatStrategy(0,0,"","","");
                        repeatLiveData.setValue(repeatData);
                        repeatRemarkLiveData.setValue(getString(R.string.ont_time));
                        break;

                    case R.id.rb_every_day:
                        RepeatStrategy repeatData1 = DaoUtil.getRepeatStrategy(4,1,"","","");
                        repeatLiveData.setValue(repeatData1);
                        repeatRemarkLiveData.setValue(getString(R.string.every_day));
                        break;
                    case R.id.rb_week_one_five:
                        RepeatStrategy repeatData2 = DaoUtil.getRepeatStrategy(3,1,"","1,2,3,4,5","");
                        repeatLiveData.setValue(repeatData2);
                        repeatRemarkLiveData.setValue(getString(R.string.week_one_five));
                        break;
                    case R.id.rb_custom:
                        //默认每月
                        RepeatStrategy repeatData3 = DaoUtil.getRepeatStrategy(2,1,"","","");
                        repeatLiveData.setValue(repeatData3);
                        repeatRemarkLiveData.setValue(getString(R.string.custom));
                        break;
                }
            }
        });
    }

    public void back(View v) {
        finish();
    }
}