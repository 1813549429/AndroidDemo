package com.example.bonoremind;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bonoremind.adapter.RepeatAdapter;
import com.example.bonoremind.base.BaseActivity;
import com.example.bonoremind.databinding.ActivitySetRepeatBinding;
import com.example.bonoremind.db.entity.Remind;
import com.example.bonoremind.utils.DateUtil;
import com.example.bonoremind.utils.JsonUtil;
import com.example.bonoremind.vm.SetRepeatViewModel;

import java.util.ArrayList;
import java.util.List;

public class SetRepeatActivity extends BaseActivity<SetRepeatViewModel, ActivitySetRepeatBinding> {

    public static final int CANCEL = 0;
    public static final int SAVE = 1;
    private Remind resultRemind;
    private String repeatStr;
    private List<String> list;
    private String none;
    private RepeatAdapter mRepeatAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_repeat;
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
        RecyclerView mRvRepeat = findViewById(R.id.rv_repeat);
        mRepeatAdapter = new RepeatAdapter(getApplicationContext(), list);
        mRepeatAdapter.setCheckedStr(none);
        if (!TextUtils.isEmpty(repeatStr)) {
            mRepeatAdapter.setCheckedStr(repeatStr);
        }

        mRvRepeat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvRepeat.setAdapter(mRepeatAdapter);
    }

    private void initData() {
        resultRemind = getIntent().getParcelableExtra("remind");
        repeatStr = DateUtil.repeatToStr(getApplicationContext(), resultRemind);

        list = new ArrayList<>();
        none = getString(R.string.none);
        String daily = getString(R.string.daily);
        String every_weekday = getString(R.string.every_weekday);
        String weekly = getString(R.string.weekly);
        String monthly = getString(R.string.monthly);
        String yearly = getString(R.string.yearly);

        list.add(none);
        list.add(daily);
        list.add(every_weekday);
        list.add(weekly);
        list.add(monthly);
        list.add(yearly);

    }

    @Override
    public void click(int mode) {
        switch (mode) {
            case CANCEL:
                finish();
                break;
            case SAVE:
                //将选中的文字保存到Remind对象里
                setRepeat(mRepeatAdapter.getCheckedStr());
                Intent intent = new Intent();
                intent.putExtra("remind", resultRemind);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void setRepeat(String repeatData) {
        if (repeatData.equals(getString(R.string.none))) {
            resultRemind.setRepeatType(0);
        } else if (repeatData.equals(getString(R.string.daily))) {
            resultRemind.setRepeatType(4);
            resultRemind.setRepeatInterval(1);
        } else if (repeatData.equals(getString(R.string.every_weekday))) {
            resultRemind.setRepeatType(3);
            resultRemind.setRepeatInterval(1);
            resultRemind.setRepeatValue(JsonUtil.strToJson("1,2,3,4,5"));
        } else if (repeatData.equals(getString(R.string.weekly))) {
            resultRemind.setRepeatType(3);
            resultRemind.setRepeatInterval(1);
            resultRemind.setRepeatValue(JsonUtil.strToJson("2"));
        } else if (repeatData.equals(getString(R.string.monthly))) {
            resultRemind.setRepeatType(2);
            resultRemind.setRepeatInterval(1);
            resultRemind.setRepeatValue(JsonUtil.strToJson("20"));
        } else if (repeatData.equals(getString(R.string.yearly))) {
            resultRemind.setRepeatType(1);
            resultRemind.setRepeatInterval(1);
            resultRemind.setRepeatValue(JsonUtil.strToJson("10"));
        }
    }
}
