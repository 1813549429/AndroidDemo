package com.example.remind.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.remind.R;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvTime;
    private TextView mTvRemind;
    private TextView mTvRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initUI();

    }

    private void initUI() {
        //设置EditText显示
        EditText editText = findViewById(R.id.et_input_name);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        TextView tv_set_time = findViewById(R.id.tv_set_time);
        tv_set_time.setOnClickListener(this);

        ConstraintLayout constraintLayout = findViewById(R.id.cl_add_data);
        mTvTime = findViewById(R.id.tv_add_time);
        mTvRemind = findViewById(R.id.tv_add_remind);
        mTvRepeat = findViewById(R.id.tv_add_repeat);


        ImageButton imageButton = findViewById(R.id.ib_add_commit);
        imageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_time:
                Intent intent = new Intent(AddActivity.this, SetTimeActivity.class);
                intent.putExtra("isDialog", true);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;

            case R.id.ib_add_commit:

                break;
        }
    }
}