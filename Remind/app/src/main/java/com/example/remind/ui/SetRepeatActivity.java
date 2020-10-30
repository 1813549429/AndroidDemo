package com.example.remind.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.remind.R;
import com.example.remind.adapter.RepeatAdapter;
import com.example.remind.view.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SetRepeatActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> list;
    private RepeatAdapter mRepeatAdapter;
    private String none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_repeat);

        initData();
        initUI();
    }

    private void initData() {
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

    private void initUI() {
        RecyclerView mRvRepeat = findViewById(R.id.rv_repeat);
        mRepeatAdapter = new RepeatAdapter(getApplicationContext(), list);
        mRepeatAdapter.setCheckedStr(none);
        String repeatStr = getIntent().getStringExtra("repeat");
        if (!TextUtils.isEmpty(repeatStr)) {
           mRepeatAdapter.setCheckedStr(repeatStr);
        }

        mRvRepeat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvRepeat.setAdapter(mRepeatAdapter);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_save).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();

                break;
            case R.id.tv_save:
                Intent intent = new Intent();
                intent.putExtra("repeat", mRepeatAdapter.getCheckedStr());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}