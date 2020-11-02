package com.bonoreminder.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bonoreminder.app.app.AppContext;
import com.bonoreminder.app.db.entity.Remind;
import com.bonoreminder.app.utils.DateUtil;
import com.bonoreminder.app.utils.StatusBarUtil;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SET_TIME = 0;
    private Remind resultRemind;
    private String[] remindStr;
    private EditText mEtTitle;
    private TextView mTvSetTime;
    private TextView mTvSetRemind;
    private TextView mTvSetRepeat;
    private ImageButton ib_clear_time;
    private ImageButton ib_clear_remind;
    private ImageButton ib_clear_repeat;
    private EditText mEtRemark;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().hide();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.activity_bg));

        initData();

        initUI();
    }

    private void initData() {
        resultRemind = new Remind();
        remindStr = new String[5];

        Remind remind = getIntent().getParcelableExtra("remind");
        if(remind != null) {
            resultRemind = remind;
            remindStr = DateUtil.remindToStr(remind);
        }
    }

    private void initUI() {
        ImageButton leftImage = findViewById(R.id.ib_left);
        ImageButton rightImage = findViewById(R.id.ib_right);
        ib_clear_time = findViewById(R.id.ib_clear_time);
        ib_clear_remind = findViewById(R.id.ib_clear_remind);
        ib_clear_repeat = findViewById(R.id.ib_clear_repeat);
        checkBox = findViewById(R.id.checkbox);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.list_title);
        mEtTitle = findViewById(R.id.et_item_title);
        mTvSetTime = findViewById(R.id.tv_set_time);
        mTvSetRemind = findViewById(R.id.tv_set_remind);
        mTvSetRepeat = findViewById(R.id.tv_set_repeat);
        mEtRemark = findViewById(R.id.et_remark);

        checkBox.setOnClickListener(this);
        mTvSetTime.setOnClickListener(this);
        mTvSetRemind.setOnClickListener(this);
        mTvSetRepeat.setOnClickListener(this);
        ib_clear_time.setOnClickListener(this);
        ib_clear_remind.setOnClickListener(this);
        ib_clear_repeat.setOnClickListener(this);

        initComponent();

        leftImage.setBackground(getResources().getDrawable(R.mipmap.close));
        rightImage.setBackground(getResources().getDrawable(R.mipmap.deletewhite));

    }

    private void initComponent() {
        if (!TextUtils.isEmpty(remindStr[0])) {
            mEtTitle.setText(remindStr[0]);
        }
        if (!TextUtils.isEmpty(remindStr[1])) {
            mTvSetTime.setText(remindStr[1]);
            mTvSetTime.setTextColor(Color.parseColor("#000000"));
            ib_clear_time.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(remindStr[2])) {
            mTvSetRemind.setText(remindStr[2]);
            mTvSetRemind.setTextColor(Color.parseColor("#000000"));
            ib_clear_remind.setVisibility(View.VISIBLE);

        }
        if(!TextUtils.isEmpty(remindStr[3])) {
            mTvSetRepeat.setText(remindStr[3]);
            mTvSetRepeat.setTextColor(Color.parseColor("#000000"));
            ib_clear_repeat.setVisibility(View.VISIBLE);

        }
        if(!TextUtils.isEmpty(remindStr[4])) {
            mEtRemark.setText(remindStr[4]);
            mEtRemark.setTextColor(Color.parseColor("#000000"));
        }

        if (resultRemind.isComplete()) {
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
    }

    public void left(View view) {
        resultRemind.setTitle(mEtTitle.getText().toString());
        resultRemind.setRemark(mEtRemark.getText().toString());

        AppContext.executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppContext.mainItemRepository.deleteReminds(resultRemind);
                AppContext.mainItemRepository.insertRemind(resultRemind);
            }
        });
        Intent intent = new Intent();
        intent.putExtra("remind", resultRemind);
        intent.putExtra("isDelete", false);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void right(View view) {
        AppContext.executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppContext.mainItemRepository.deleteReminds(resultRemind);
            }
        });
        Intent intent = new Intent();
        intent.putExtra("remind", resultRemind);
        intent.putExtra("isDelete", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_time:
            case R.id.tv_set_repeat:
            case R.id.tv_set_remind:
                Intent intent = new Intent(EditActivity.this, SetTimeActivity.class);
                intent.putExtra("isDialog", false);
                intent.putExtra("remind", resultRemind);
                startActivityForResult(intent, SET_TIME);
                break;
            case R.id.ib_clear_time:
                resultRemind.setSetting(false);
                resultRemind.setTime(0);
                mTvSetTime.setTextColor(Color.parseColor("#50000000"));
                mTvSetTime.setText(R.string.no_time);
                ib_clear_time.setVisibility(View.GONE);
                break;
            case R.id.ib_clear_remind:
                resultRemind.setSetting(false);
                resultRemind.setAdvance(null);
                mTvSetRemind.setTextColor(Color.parseColor("#50000000"));
                mTvSetRemind.setText(R.string.no_reminder);
                ib_clear_remind.setVisibility(View.GONE);
                break;
            case R.id.ib_clear_repeat:
                resultRemind.setSetting(false);
                resultRemind.setRepeatType(0);
                resultRemind.setRepeatInterval(0);
                resultRemind.setRepeatValue(null);
                mTvSetRepeat.setTextColor(Color.parseColor("#50000000"));
                mTvSetRepeat.setText(R.string.no_repeat);
                ib_clear_repeat.setVisibility(View.GONE);
                break;
            case R.id.checkbox:
                if (checkBox.isChecked()) {
                    resultRemind.setComplete(true);
                } else {
                    resultRemind.setComplete(false);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SET_TIME) {
            if(data != null) {
                resultRemind = data.getParcelableExtra("remind");
                remindStr = DateUtil.remindToStr(resultRemind);
                initComponent();

            }
        }
    }
}