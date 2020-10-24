package com.example.reminddemo.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.reminddemo.R;
import com.example.reminddemo.base.BaseActivity;
import com.example.reminddemo.databinding.ActivityMainBinding;
import com.example.reminddemo.ui.vm.MainViewModel;
import com.example.reminddemo.utils.DateUtil;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding>{

    public static final int CLICK_MORE = 0;
    public static final int CLICK_add = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView.setHolder(this);
        bindingView.setVm(viewModel);
        initUI();
    }

    private void initUI() {
        initAdd();
        initTitle();
    }

    private void initTitle() {
        long nowTime = System.currentTimeMillis();
        int[] allDates = DateUtil.getDay(nowTime);
        String s = getString(R.string.main_date,allDates[0]+"", allDates[1]+"");
        viewModel.nowDate.setValue(s);
    }

    private void initAdd() {

    }

    public void click(int mode) {
        if(mode == CLICK_MORE) {

        }else if(mode == CLICK_add) {
            startActivity(new Intent(MainActivity.this, EditActivity.class));
        }
    }

}