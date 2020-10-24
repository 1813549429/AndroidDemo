package com.example.remind.app;

import android.app.Application;

public class AppContext extends Application {

    private static AppContext instance;

    public static AppContext getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
