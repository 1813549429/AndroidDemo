package com.example.reminddemo.db;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "remind_before", foreignKeys = @ForeignKey(entity = RemindItem.class,
        parentColumns = "key", childColumns = "item_key"))
public class RemindBefore {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    /**
     * 0代表开始时提醒
     * 1代表分钟
     * 2代表小时
     * 3代表天
     * 不能为null
     */
    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    public int type;

    @ColumnInfo(name = "minute", typeAffinity = ColumnInfo.INTEGER)
    public int minute;

    @ColumnInfo(name = "hour", typeAffinity = ColumnInfo.INTEGER)
    public int hour;

    @ColumnInfo(name = "day", typeAffinity = ColumnInfo.INTEGER)
    public int day;

    @ColumnInfo(name = "item_key", typeAffinity = ColumnInfo.INTEGER, index = true)
    public long item_key;

    @Ignore
    public String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getItem_key() {
        return item_key;
    }

    public void setItem_key(long item_key) {
        this.item_key = item_key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemindBefore that = (RemindBefore) o;
        return id == that.id &&
                type == that.type &&
                minute == that.minute &&
                hour == that.hour &&
                day == that.day &&
                item_key == that.item_key &&
                Objects.equals(remark, that.remark);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, type, minute, hour, day, item_key, remark);
    }
}
