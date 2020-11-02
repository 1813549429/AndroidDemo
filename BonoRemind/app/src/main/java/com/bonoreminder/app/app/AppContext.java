package com.bonoreminder.app.app;

import android.app.Application;

import com.bonoreminder.app.db.AppDataBase;
import com.bonoreminder.app.db.repository.MainItemRepository;
import com.bonoreminder.app.utils.AppExecutors;


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
