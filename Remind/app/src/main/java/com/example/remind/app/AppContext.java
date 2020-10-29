package com.example.remind.app;

import android.app.Application;

import com.example.remind.utils.AppExecutors;

public class AppContext extends Application {

    private static AppContext instance;
    //初始化一个全局的线程池
    public static AppExecutors executors = new AppExecutors();

    public static AppContext getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
