package com.example.reminddemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reminddemo.db.RemindBefore;
import com.example.reminddemo.db.RepeatStrategy;

import java.util.List;

@Dao
public interface RemindBeforeDao {

    @Query("SELECT * FROM remind_before WHERE item_key = :item_key")
    List<RemindBefore> findByItemKey(long item_key);

    @Insert
    void insertRemindBefore(RemindBefore remindBefore);

    @Delete
    void deleteRemindBefore(RemindBefore remindBefore);

    @Update
    void updateRemindBefore(RemindBefore remindBefore);

    @Query("DELETE FROM remind_before WHERE item_key = :item_key")
    void deleteRemindBeforeByItemKey(long item_key);
}
