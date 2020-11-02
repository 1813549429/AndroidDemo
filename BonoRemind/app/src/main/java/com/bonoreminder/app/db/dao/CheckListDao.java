package com.bonoreminder.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.bonoreminder.app.db.entity.CheckListEntity;

import java.util.List;

@Dao
public interface CheckListDao {
    @Query("SELECT * FROM checkList")
    LiveData<List<CheckListEntity>> loadAllCheckLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCheckList(CheckListEntity checkListEntity);

    @Query("SELECT * FROM checkList WHERE id = :checkListId")
    LiveData<CheckListEntity> loadCheckList(int checkListId);

    @Delete
    void delete(CheckListEntity checkListEntity);

}
