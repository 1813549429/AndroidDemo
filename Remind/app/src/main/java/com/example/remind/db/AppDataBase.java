package com.example.remind.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Remind.class, CheckList.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {



}
