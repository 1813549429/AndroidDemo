package com.bonoreminder.app.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 全局线程池
 */
public class AppExecutors {

    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final ScheduledExecutorService mScheduleIO;

    private final Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread, ScheduledExecutorService scheduleThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
        this.mScheduleIO = scheduleThread;
    }

    public AppExecutors() {
        this(Executors.newCachedThreadPool(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor(), Executors.newScheduledThreadPool(3));
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    public ScheduledExecutorService scheduleIO() {
        return mScheduleIO;
    }



    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
