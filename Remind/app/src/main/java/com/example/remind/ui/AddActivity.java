package com.example.remind.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remind.R;
import com.example.remind.app.AppContext;
import com.example.remind.db.AppDataBase;
import com.example.remind.db.entity.Remind;
import com.example.remind.db.repository.MainItemRepository;
import com.example.remind.utils.AppExecutors;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SET_TIME = 0;
    private TextView mTvTime;
    private TextView mTvRemind;
    private TextView mTvRepeat;
    private Remind resultRemind;
    private EditText editText;
    private AppExecutors executors = AppContext.executors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initUI();

    }

    private void initUI() {
        //设置EditText显示
        editText = findViewById(R.id.et_input_name);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        TextView tv_set_time = findViewById(R.id.tv_set_time);
        tv_set_time.setOnClickListener(this);

        initSetTimeData();

        ConstraintLayout constraintLayout = findViewById(R.id.cl_add_data);
        mTvTime = findViewById(R.id.tv_add_time);
        mTvRemind = findViewById(R.id.tv_add_remind);
        mTvRepeat = findViewById(R.id.tv_add_repeat);


        ImageButton imageButton = findViewById(R.id.ib_add_commit);
        imageButton.setOnClickListener(this);

    }

    private void initSetTimeData() {
        resultRemind = new Remind();
        resultRemind.setTime(System.currentTimeMillis());
        resultRemind.setSetting(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_time:
                Intent intent = new Intent(AddActivity.this, SetTimeActivity.class);
                intent.putExtra("isDialog", true);
                startActivityForResult(intent, SET_TIME);
                overridePendingTransition(0, 0);
                break;

            case R.id.ib_add_commit:
                if(TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();
                } else {
                    resultRemind.setTitle(editText.getText().toString());
                    MainItemRepository repository = MainItemRepository.getInstance(AppDataBase.getInstance(getApplicationContext()));
                    executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            repository.insertRemind(resultRemind);
                        }
                    });
                    Intent intent1 = new Intent();
                    intent1.putExtra("remind", resultRemind);
                    setResult(RESULT_OK, intent1);
                    finish();
                }


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SET_TIME) {
            if(data != null) {
                resultRemind = data.getParcelableExtra("setTime");
            }
        }
    }
}