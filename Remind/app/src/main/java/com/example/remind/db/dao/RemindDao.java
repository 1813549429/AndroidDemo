package com.example.remind.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.remind.db.entity.Remind;

import java.util.List;

@Dao
public interface RemindDao {

//    @Query("SELECT * FROM Remind WHERE checkListId = :checkListId")
//    LiveData<List<Remind>> loadReminds(int checkListId);

    @Query("SELECT * FROM Remind")
    LiveData<List<Remind>> loadReminds();

//    @Query("SELECT * FROM Remind WHERE checkListId = :checkListId")
//    List<Remind> loadRemindsSync(int checkListId);

    @Query("SELECT * FROM Remind")
    List<Remind> loadRemindsSync();

    @Delete
    void deleteRemind(Remind remind);

    @Delete
    void deleteRemindList(List<Remind> reminds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRemind(Remind remind);
}
