package com.example.remind.app;

import android.app.Application;

import com.example.remind.db.AppDataBase;
import com.example.remind.db.repository.MainItemRepository;
import com.example.remind.utils.AppExecutors;

public class AppContext extends Application {

    private static AppContext instance;
    //初始化一个全局的线程池
    public static AppExecutors executors = new AppExecutors();

    public static AppContext getContext() {
        return instance;
    }

    public static MainItemRepository mainItemRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mainItemRepository = MainItemRepository.getInstance(AppDataBase.getInstance(this));
    }
}
