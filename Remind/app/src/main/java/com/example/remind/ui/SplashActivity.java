package com.example.remind.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.remind.R;
import com.example.remind.app.AppContext;
import com.example.remind.db.AppDataBase;
import com.example.remind.db.entity.Remind;
import com.example.remind.db.repository.MainItemRepository;
import com.example.remind.generated.callback.OnClickListener;
import com.example.remind.utils.AppExecutors;
import com.example.remind.utils.StatusBarUtil;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    private Intent intent;
    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.activity_bg));
        intent = new Intent(SplashActivity.this, MainActivity.class);

        AppContext.executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    if(!isFinishing()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        });
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        initData();
    }

    //初始化数据
    private void initData() {

        Observable.create(new ObservableOnSubscribe<List<Remind>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Remind>> emitter) throws Exception {
                //获取所有事件的数据
                MainItemRepository mainItemRepository = MainItemRepository.getInstance(AppDataBase.getInstance(getApplicationContext()));

                List<Remind> remindList = mainItemRepository.getRemindsSync();
                Log.d(TAG, "提醒任务的总数目为：" + remindList.size());
                emitter.onNext(remindList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Remind>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Remind> listData) {
                        if(listData == null) {
                            listData = new ArrayList<>();
                        }
                        ArrayList<Remind> remindList = (ArrayList<Remind>) listData;
                        intent.putExtra("allData", remindList);
                        Log.d(TAG, "提醒任务的总数目为：" + remindList.size());
                        findViewById(R.id.jump).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public void jump(View view) {
        startActivity(intent);
    }
}