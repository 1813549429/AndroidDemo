package com.example.reminddemo.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reminddemo.R;
import com.example.reminddemo.base.BaseActivity;
import com.example.reminddemo.data.RemindBeforeListBean;
import com.example.reminddemo.databinding.ActivityEditBinding;
import com.example.reminddemo.db.RemindBefore;
import com.example.reminddemo.db.RepeatStrategy;
import com.example.reminddemo.ui.vm.EditViewModel;
import com.example.reminddemo.utils.CustomDatePicker;
import com.example.reminddemo.dao.DaoUtil;
import com.example.reminddemo.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends BaseActivity<EditViewModel, ActivityEditBinding> {

    public static final int CLICK_START = 0;
    public static final int CLICK_REPEAT = 1;
    public static final int CLICK_REMIND = 2;
    public static final int CLICK_CANCEL = 3;
    public static final int CLICK_SAVE = 4;
    private EditText mTitleEt;
    private EditText mRemarkEt;
    private CustomDatePicker customDatePicker;
    private String nowTime;
//    private TextView mStartTv;
//    private TextView mRepeatTv;
//    private TextView mRemindTv;

    private int[] allDates;

    /**
     * 在开始时间右侧显示的时间，
     */
    private String dateRemark;
    private long dateLong;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingView.setHolder(this);
        bindingView.setVm(viewModel);

        initUI();
    }


    private void initUI() {
        mTitleEt = findViewById(R.id.et_title);
        mRemarkEt = findViewById(R.id.et_remark);

//        mStartTv = findViewById(R.id.tv_start_remark);
//        mRepeatTv = findViewById(R.id.tv_repeat_remark);
//        mRemindTv = findViewById(R.id.tv_remind_remark);

        //初始化时间选择器
        DatePicker();

        dateLong = System.currentTimeMillis();
        initStartTime(dateLong);
        initRepeat();
        initRemindBefore();
    }

    //初始化提醒，默认刚开始提醒
    private void initRemindBefore() {
        RemindBeforeListBean remindBeforeListBean = new RemindBeforeListBean();
        remindBeforeListBean.setRemindBeforeRemark(getString(R.string.start));
        List<RemindBefore> remindBeforeList = new ArrayList<>();
        RemindBefore remindBefore = new RemindBefore();
        remindBefore.setType(0);
        remindBefore.setRemark(getString(R.string.start));
        remindBeforeList.add(remindBefore);
        remindBeforeListBean.setRemindBeforeList(remindBeforeList);
        viewModel.remindBefore.setValue(remindBeforeListBean);
    }

    private void initRepeat() {
        String repeatStr = getString(R.string.ont_time);
        viewModel.repeatRemark.setValue(repeatStr);
        RepeatStrategy repeatStrategy = new RepeatStrategy();
        repeatStrategy.setFrequency(0);
        viewModel.repeatData.setValue(repeatStrategy);
    }

    private void initStartTime(long nowDate) {

        allDates = DateUtil.getDay(nowDate);

        dateRemark = allDates[0] + "年" + allDates[1] + "月" + allDates[2] +
                "日" + DateUtil.numberToWeek(allDates[5]) + allDates[3] + ":" + allDates[4];

        nowTime = allDates[0] + "-" + allDates[1] + "-" +
                allDates[2] + " " + allDates[3] + ":" + allDates[4];

        viewModel.startTimeRemark.setValue(dateRemark);

    }


    public void onClick(int mode) {
        switch (mode) {
            case CLICK_START:
                customDatePicker.show(nowTime);
                break;
            case CLICK_REPEAT:
                startActivity(new Intent(EditActivity.this, RepeatActivity.class));
                break;
            case CLICK_REMIND:
                startActivity(new Intent(EditActivity.this, RemindActivity.class));
                break;
            case CLICK_CANCEL:
                finish();
                break;
            case CLICK_SAVE:
                //保存更新到数据库
                //标题
                String title = mTitleEt.getText().toString();
                if(TextUtils.isEmpty(title)) {
                    title = "无标题";
                }
                //备注
                String remark = mRemarkEt.getText().toString();

                DaoUtil daoUtil = DaoUtil.getInstance(getApplicationContext());

                daoUtil.insertItem(title, remark, System.currentTimeMillis(),dateLong,
                        viewModel.repeatData.getValue(),
                        viewModel.remindBefore.getValue().getRemindBeforeList());
                Toast.makeText(getApplicationContext(), R.string.activity_created, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    /**
     * 显示时间
     */
    private void DatePicker() {

        //tvElectricalTime.setText(now.split(" ")[0]);
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                Log.d("yyyyy", time);
                dateLong = DateUtil.strToLong(time, "yyyy-MM-dd HH:mm");

                initStartTime(dateLong);
            }
        }, "1990-01-01 00:00", "2200-12-31 11:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker.showSpecificTime(true); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }
}