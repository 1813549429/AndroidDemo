package com.example.reminddemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reminddemo.db.RepeatStrategy;

@Dao
public interface RepeatDao {

    @Query("select * from repeat_strategy where item_key = :item_key")
    RepeatStrategy findByItemKey(long item_key);

    @Insert
    void insertRepeat(RepeatStrategy repeatStrategy);

    @Delete
    void deleteRepeat(RepeatStrategy repeatStrategy);

    @Query("DELETE FROM repeat_strategy where item_key = :item_key")
    void deleteRepeatByItemKey(long item_key);

    @Update
    void updateRepeat(RepeatStrategy repeatStrategy);

}
