package com.bonoreminder.app.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bonoreminder.app.db.dao.CheckListDao;
import com.bonoreminder.app.db.dao.RemindDao;
import com.bonoreminder.app.db.entity.CheckListEntity;
import com.bonoreminder.app.db.entity.Remind;


@Database(entities = {Remind.class, CheckListEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "remind_db";

    public abstract CheckListDao checkListDao();

    public abstract RemindDao remindDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDataBase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDataBase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }



}
