package com.example.remind.db.repository;

import androidx.lifecycle.LiveData;

import com.chad.library.adapter.base.diff.ListChangeListener;
import com.example.remind.db.AppDataBase;
import com.example.remind.db.entity.CheckListEntity;
import com.example.remind.db.entity.Remind;

import java.util.List;

/**
 * 获取首页item数据的仓库
 */
public class MainItemRepository {
    private final AppDataBase mDatabase;
    private static MainItemRepository sInstance;

    private MainItemRepository(final AppDataBase dataBase) {
        mDatabase = dataBase;
    }

    public static MainItemRepository getInstance(final AppDataBase appDataBase) {
        if(sInstance == null) {
            synchronized (MainItemRepository.class) {
                if(sInstance == null) {
                    sInstance = new MainItemRepository(appDataBase);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Remind>> getReminds() {
        return mDatabase.remindDao().loadReminds();
    }

    public LiveData<List<CheckListEntity>> getCheckLists() {
        return mDatabase.checkListDao().loadAllCheckLists();
    }

    public void deleteReminds(Remind remind) {
        mDatabase.remindDao().deleteRemind(remind);
    }

    public void deleteCheckList(CheckListEntity checkListEntity) {
        mDatabase.checkListDao().delete(checkListEntity);
    }

    public void insertRemind(Remind remind) {
        mDatabase.remindDao().insertRemind(remind);
    }

    public void insertCheckList(CheckListEntity checkListEntity) {
        mDatabase.checkListDao().insertCheckList(checkListEntity);
    }

}
