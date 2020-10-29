package com.example.remind.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.remind.R;
import com.example.remind.utils.StatusBarUtil;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().hide();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.activity_bg));

    }
}