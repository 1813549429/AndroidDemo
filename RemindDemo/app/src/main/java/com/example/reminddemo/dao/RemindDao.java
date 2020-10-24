package com.example.reminddemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reminddemo.db.RemindItem;

import java.util.List;

@Dao
public interface RemindDao {

    @Query("SELECT * FROM remind_item")
    List<RemindItem> getAll();

    @Query("SELECT * FROM remind_item WHERE `key` = (:key)")
    RemindItem findByKey(long key);

    @Query("SELECT * FROM remind_item WHERE start_date > (:startDate) AND start_date < (:endDate)")
    List<RemindItem> getAllDate(long startDate, long endDate);

    @Insert
    void insertRemindItem(RemindItem remindItem);

    @Delete
    void deleteRemindItem(RemindItem remindItem);

    @Update
    void updateRemindItem(RemindItem remindItem);
}
