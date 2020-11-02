package com.bonoreminder.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bonoreminder.app.app.AppContext;
import com.bonoreminder.app.base.BaseActivity;
import com.bonoreminder.app.databinding.ActivityAddBinding;
import com.bonoreminder.app.db.entity.Remind;
import com.bonoreminder.app.utils.DateUtil;
import com.bonoreminder.app.vm.AddViewModel;

public class AddActivity extends BaseActivity<AddViewModel, ActivityAddBinding> {

    public static final int SET_TIME = 0;
    public static final int SAVE = 1;
    private Remind resultRemind;
    private EditText mEtName;
    private ConstraintLayout mClAddData;
    private MutableLiveData<Remind> remindLiveData;
    private MutableLiveData<String> timeTextLiveData;
    private MutableLiveData<String> advanceTextLiveData;
    private MutableLiveData<String> repeatTextLiveData;

    private TextView mTvSetTime;
    private long preClickTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView.setHolder(this);
        bindingView.setVm(viewModel);

        //初始化数据
        initData();
        initMainView();
        initViewModelVal();
    }

    private void initViewModelVal() {
        remindLiveData = viewModel.getRemindLiveData();
        timeTextLiveData = viewModel.getTimeTextLiveData();
        advanceTextLiveData = viewModel.getAdvanceTextLiveData();
        repeatTextLiveData = viewModel.getRepeatTextLiveData();

        remindLiveData.setValue(resultRemind);

        remindLiveData.observe(this, new Observer<Remind>() {
            @Override
            public void onChanged(Remind remind) {
                //判断是否已经设置提前提醒,判断依据就是提前提醒有没有设置
                if (!TextUtils.isEmpty(remind.getAdvance())) {
                    mClAddData.setVisibility(View.VISIBLE);
                    mTvSetTime.setVisibility(View.GONE);
                    //显示数据
                    timeTextLiveData.setValue(DateUtil.timeToStr(remind.getTime()));
                    advanceTextLiveData.setValue(DateUtil.advanceJsonToStr(getApplicationContext(), remind.getAdvance()));
                    repeatTextLiveData.setValue(DateUtil.repeatToStr(getApplicationContext(), remind));
                }else {
                    mClAddData.setVisibility(View.GONE);
                    mTvSetTime.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void initMainView() {
        mEtName = findViewById(R.id.et_input_name);
        mTvSetTime = findViewById(R.id.tv_set_time);
        mClAddData = findViewById(R.id.cl_add_data);
        //设置EditText显示
        mEtName.setFocusable(true);
        mEtName.setFocusableInTouchMode(true);
        mEtName.requestFocus();
//        显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


    }

    private void initData() {
        //首先初始化resultRemind
        resultRemind = new Remind();
        //为减少非空判断，每一项都应先初始化
        resultRemind.setTitle("");
        resultRemind.setAdvance("");
        resultRemind.setComplete(false);
        resultRemind.setSetting(false);
        resultRemind.setRemark("");
        resultRemind.setRepeatInterval(0);
        resultRemind.setRepeatType(0);
        resultRemind.setRepeatValue("");
        //将当前时间转化成今天早上9点
        resultRemind.setTime(DateUtil.currentToNine(System.currentTimeMillis()));
    }

    public void click(int mode) {

        //无论是点击哪个都应该保存title的状态
        resultRemind.setTitle(mEtName.getText().toString());
        //重复点击的容错处理
        if (System.currentTimeMillis() - preClickTime < 600) {
            return;
        }
        preClickTime = System.currentTimeMillis();

        switch (mode) {
            case SET_TIME:
                //跳转到SetTimeActivity设置数据
                Intent activityIntent = new Intent(this, SetTimeDialogActivity.class);
                activityIntent.putExtra("remind", resultRemind);
                activityIntent.putExtra("isDialog", true);
                startActivityForResult(activityIntent, SET_TIME);
                overridePendingTransition(0,0);
                break;
            case SAVE:
                if(TextUtils.isEmpty(mEtName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "请输入任务标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存到数据库
                AppContext.executors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppContext.mainItemRepository.insertRemind(resultRemind);
                    }
                });
                //返回数据到首页，更新条目
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
        if (requestCode == SET_TIME) {
            if (data != null) {
                //Set time之后返回的数据
                resultRemind = data.getParcelableExtra("remind");
                Log.d("AddActivity", "有返回值");
                remindLiveData.setValue(resultRemind);
            }
        }
    }
}
