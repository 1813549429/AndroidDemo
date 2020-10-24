package com.example.reminddemo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.reminddemo.dao.RemindBeforeDao;
import com.example.reminddemo.dao.RemindDao;
import com.example.reminddemo.dao.RepeatDao;

@Database(entities = {RemindItem.class, RepeatStrategy.class, RemindBefore.class}, version = 1)
public abstract class RemindDB extends RoomDatabase {

    private static final String DATABASE_NAME = "remind_db";

    private static RemindDB databaseInstance;

    public static synchronized RemindDB getInstance(Context context) {
        if(databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    RemindDB.class, DATABASE_NAME)
                    .build();
        }
        return databaseInstance;
    }

    public abstract RemindDao remindDao();
    public abstract RepeatDao repeatDao();
    public abstract RemindBeforeDao remindBeforeDao();
}
