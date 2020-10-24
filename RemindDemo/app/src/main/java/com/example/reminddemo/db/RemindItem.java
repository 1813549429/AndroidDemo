package com.example.reminddemo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "remind_item")
public class RemindItem {

    @PrimaryKey
    @ColumnInfo(name = "key", typeAffinity = ColumnInfo.INTEGER)
    public long key;

    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    public String title;

    @ColumnInfo(name = "remark", typeAffinity = ColumnInfo.TEXT)
    public String remark;

    /**
     * 开始提醒的时间，，单位：年月周日时分
     */
    @ColumnInfo(name = "start_date", typeAffinity = ColumnInfo.INTEGER)
    public long startDate;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }
}
