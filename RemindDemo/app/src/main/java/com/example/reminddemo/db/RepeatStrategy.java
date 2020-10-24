package com.example.reminddemo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "repeat_strategy", foreignKeys = @ForeignKey(entity = RemindItem.class,
        parentColumns = "key", childColumns = "item_key"))
public class RepeatStrategy {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    /**
     * 频率，0代表一次性即不重复，1代表年   2代表月    3代表周    4代表日  不能为null
     */
    @ColumnInfo(name = "frequency", typeAffinity = ColumnInfo.INTEGER)
    public int frequency;

    /**
     * 间隔，以frequency设置的值为标准，例frequency为1，interval为2则代表每隔两年
     */
    @ColumnInfo(name = "interval", typeAffinity = ColumnInfo.INTEGER)
    public int interval;

    /**
     * 仅在frequency为1时可设置值，范围为1-12，，多选用逗号分隔
     */
    @ColumnInfo(name = "month", typeAffinity = ColumnInfo.TEXT)
    public String month;

    /**
     * 仅在frequency为3时可设置值，范围为0-6  表示周日到周六，多选用逗号分隔
     */
    @ColumnInfo(name = "week", typeAffinity = ColumnInfo.TEXT)
    public String week;

    /**
     * 仅在frequency为2时可设置，范围为1-31，多选用逗号分隔
     */
    @ColumnInfo(name = "day", typeAffinity = ColumnInfo.TEXT)
    public String day;

    /**
     * 外键，对应与remind_item表中的id字段
     */
    @ColumnInfo(name = "item_key", typeAffinity = ColumnInfo.INTEGER, index = true)
    public long item_key;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getItem_key() {
        return item_key;
    }

    public void setItem_key(long item_key) {
        this.item_key = item_key;
    }
}
