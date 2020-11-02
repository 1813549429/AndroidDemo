package com.example1.recycletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AdapterBean> adapterBeans;
    private RecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        adapterBeans = new ArrayList<>();
        for (int i=0; i<10; i++) {
            List<Remind> reminds = new ArrayList<>();
            for (int j=0; j<3; j++) {
                Remind remind = new Remind("二级列表" + i + ":" + j, false, j);
                reminds.add(remind);
            }
            AdapterBean adapterBean = new AdapterBean("卧槽了" + i, reminds, true, Color.parseColor("#E3E3E3"));
            adapterBeans.add(adapterBean);
        }

        adapter = new RecycleAdapter(this, adapterBeans, "Inbox");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


    }

    public void button(View view) {
        adapter.setHeaderStr("Today");
        adapter.notifyItemChanged(0);
    }
}