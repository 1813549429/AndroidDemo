package com.example.reminddemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.reminddemo.R;
import com.example.reminddemo.adapter.RemindAdapter;
import com.example.reminddemo.data.LiveDataBus;
import com.example.reminddemo.data.RemindBeforeListBean;
import com.example.reminddemo.db.RemindBefore;

import java.util.ArrayList;
import java.util.List;

public class RemindActivity extends AppCompatActivity {

    private List<RemindBefore> recycleViewList;
    private RemindAdapter mRemindAdapter;
    private RemindBefore remindBefore;
    private MutableLiveData<RemindBeforeListBean> remindBeforeData;
    private RemindBeforeListBean remindBeforeListBean;
    private SwitchCompat mRemindSwitch;
    private RecyclerView mRvRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        getSupportActionBar().hide();

        initData();
        initUI();
    }

    private void initData() {
        LiveDataBus liveDataBus = LiveDataBus.get();
        remindBeforeData = liveDataBus.getChannel("remindBefore", RemindBeforeListBean.class);
        remindBeforeListBean = remindBeforeData.getValue();

        recycleViewList = new ArrayList<>();
        remindBefore = new RemindBefore();
        remindBefore.setRemark(getString(R.string.start));
        remindBefore.setType(0);
        recycleViewList.add(remindBefore);

        remindBefore = new RemindBefore();
        remindBefore.setRemark(getString(R.string.remind5m));
        remindBefore.setType(1);
        remindBefore.setMinute(5);
        recycleViewList.add(remindBefore);

        remindBefore = new RemindBefore();
        remindBefore.setRemark(getString(R.string.remind10m));
        remindBefore.setType(1);
        remindBefore.setMinute(10);
        recycleViewList.add(remindBefore);

        remindBefore = new RemindBefore();
        remindBefore.setRemark(getString(R.string.remind30m));
        remindBefore.setType(1);
        remindBefore.setMinute(30);
        recycleViewList.add(remindBefore);

        remindBefore = new RemindBefore();
        remindBefore.setRemark(getString(R.string.remind1h));
        remindBefore.setType(2);
        remindBefore.setMinute(1);
        recycleViewList.add(remindBefore);

        remindBefore = new RemindBefore();
        remindBefore.setRemark(getString(R.string.remind1d));
        remindBefore.setType(3);
        remindBefore.setMinute(1);
        recycleViewList.add(remindBefore);
    }

    private void initUI() {


        mRemindSwitch = findViewById(R.id.switch_remind);
        mRvRemind = findViewById(R.id.rv_remind);
        TextView mTvCustom = findViewById(R.id.tv_custom);

        mRemindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remindBeforeListBean.setRemindBeforeRemark("无提醒");
                    remindBeforeListBean.getRemindBeforeList().clear();
                }else {
                    List<RemindBefore> remindBeforeList = mRemindAdapter.checkedList;
                    remindBeforeListBean.setRemindBeforeList(remindBeforeList);
                    remindBeforeListBean.setRemindBeforeRemark(mRemindAdapter.getRemark(remindBeforeList));
                }
                remindBeforeData.setValue(remindBeforeListBean);
            }
        });

        remindBeforeData.observe(this, new Observer<RemindBeforeListBean>() {
            @Override
            public void onChanged(RemindBeforeListBean remindBeforeListBean) {
                if(remindBeforeListBean.getRemindBeforeList().size() == 0) {
                    mRemindSwitch.setChecked(true);
                }else {
                    mRemindSwitch.setChecked(false);
                }
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvRemind.setLayoutManager(manager);
        mRemindAdapter = new RemindAdapter(getApplicationContext(), recycleViewList);
        mRvRemind.setAdapter(mRemindAdapter);

        initDataUI();

    }

    /**
     * 刚打开Activity的时候通过LiveData的数据来绘制UI
     */
    private void initDataUI() {

        List<RemindBefore> remindBeforeList = remindBeforeListBean.getRemindBeforeList();
        if(remindBeforeList.size() == 0) {
            mRemindSwitch.setChecked(true);
        }else {
            mRemindSwitch.setChecked(false);
            mRemindAdapter.checkedList = remindBeforeList;
        }


    }

    public void back(View v) {
        finish();
    }
}