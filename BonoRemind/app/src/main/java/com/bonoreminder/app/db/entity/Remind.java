package com.bonoreminder.app.db.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

//foreignKeys = {
//            @ForeignKey(entity = CheckListEntity.class,
//                parentColumns = "id",
//                childColumns = "checkListId",
//                onDelete = ForeignKey.CASCADE)},
//        indices = {@Index(value = "checkListId")}
@Entity(tableName = "remind")
public class Remind implements Parcelable {

    /**
     * 自增长的id
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    /**
     * 任务的标题
     */
    @NonNull
    private String title;
    /**
     * 任务的备注
     */
    @NonNull
    private String remark;
    /**
     * 任务的执行时间
     */
    @NonNull
    private long time;
    @NonNull
    private boolean isSetting;
    /**
     * 任务的重复类型
     * 0代表不重复；
     * 1代表年；
     * 2代表月；
     * 3代表周；
     * 4代表日；
     */
    @NonNull
    private int repeatType;
    /**
     * 重复的间隔，以repeatType单位
     * 例如：若repeatType为1，，repeatInterval为2则代表每隔两年重复
     * 若repeatType不为0，则repeatInterval应该有默认值1。
     */
    @NonNull
    private int repeatInterval;
    /**
     * 重复的值，以repeatType为准。
     * 例：当repeatType为1则代表年，此时的repeatValue可设置的范围应该仅为1-12，代表1月到12月
     * TODO 以Json形式存储，因为可能会有多选
     */
    @NonNull
    private String repeatValue;
    /**
     * 任务是否已经完成。
     *
     */
    @NonNull
    private boolean isComplete;

    /**
     * 保存有提前提醒的数据，以时间戳的形式保存，例如提前五分钟就可表示为5分钟对应的毫秒值,单次提醒用0表示,不提醒用空字符串表示
     * TODO 以Json形式存储，因为可能会有多选
     */
    @NonNull
    private String advance;
    /**
     * 保存子任务，子任务仅用字符串来标示，例如 "学习英语"，"打游戏"
     * TODO 以Json形式存储，因为可能会有多选
     */
    private String subTasks;
    /**
     * 任务对应的iconId
     */
    private int iconId;
    /**
     * 外键，对应于清单表中的键，主要用于确定这个任务属于哪个自定义清单中。
     */
//    private int checkListId;

    public Remind() {

    }

    @Ignore
    protected Remind(Parcel in) {
        id = in.readInt();
        title = in.readString();
        remark = in.readString();
        time = in.readLong();
        repeatType = in.readInt();
        repeatInterval = in.readInt();
        repeatValue = in.readString();
        isComplete = in.readByte() != 0;
        isSetting = in.readByte() != 0;
        advance = in.readString();
        subTasks = in.readString();
        iconId = in.readInt();
//        checkListId = in.readInt();
    }

    @Ignore
    public static final Creator<Remind> CREATOR = new Creator<Remind>() {
        @Override
        public Remind createFromParcel(Parcel in) {
            return new Remind(in);
        }

        @Override
        public Remind[] newArray(int size) {
            return new Remind[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getRepeatValue() {
        return repeatValue;
    }

    public void setRepeatValue(String repeatValue) {
        this.repeatValue = repeatValue;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(String subTasks) {
        this.subTasks = subTasks;
    }

    public boolean isSetting() {
        return isSetting;
    }

    public void setSetting(boolean setting) {
        isSetting = setting;
    }
    //    public int getCheckListId() {
//        return checkListId;
//    }
//
//    public void setCheckListId(int checkListId) {
//        this.checkListId = checkListId;
//    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }


    @Ignore
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Remind remind = (Remind) o;
        return time == remind.time &&
                repeatType == remind.repeatType &&
                repeatInterval == remind.repeatInterval &&
                isComplete == remind.isComplete &&
                iconId == remind.iconId &&
//                checkListId == remind.checkListId &&
                Objects.equals(title, remind.title) &&
                Objects.equals(remark, remind.remark) &&
                Objects.equals(repeatValue, remind.repeatValue) &&
                Objects.equals(advance, remind.advance) &&
                Objects.equals(subTasks, remind.subTasks);
    }

    @Ignore
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(title, remark, time, repeatType, repeatInterval, isComplete, repeatValue, advance, subTasks, iconId);
    }

    @Ignore
    @Override
    public String toString() {
        return "Remind{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", time=" + time +
                ", repeatType=" + repeatType +
                ", repeatInterval=" + repeatInterval +
                ", isComplete=" + isComplete +
                ", repeatValue='" + repeatValue + '\'' +
                ", advance='" + advance + '\'' +
                ", subTasks='" + subTasks + '\'' +
                ", iconId=" + iconId +
//                ", checkListKey=" + checkListId +
                '}';
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(remark);
        dest.writeLong(time);
        dest.writeInt(repeatType);
        dest.writeInt(repeatInterval);
        dest.writeString(repeatValue);
        dest.writeByte((byte) (isComplete ? 1 : 0));
        dest.writeByte((byte) (isSetting ? 1 : 0));
        dest.writeString(advance);
        dest.writeString(subTasks);
        dest.writeInt(iconId);
//        dest.writeInt(checkListId);
    }
}
