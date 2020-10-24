package com.example.reminddemo.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.reminddemo.db.RemindBefore;
import com.example.reminddemo.db.RepeatStrategy;

import java.util.List;
import java.util.Objects;

public class AllDataBean {
    private String title;
    private String remark;
    private long startTime;
    private RepeatStrategy repeatStrategy;
    private List<RemindBefore> remindBeforeList;

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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public RepeatStrategy getRepeatStrategy() {
        return repeatStrategy;
    }

    public void setRepeatStrategy(RepeatStrategy repeatStrategy) {
        this.repeatStrategy = repeatStrategy;
    }

    public List<RemindBefore> getRemindBeforeList() {
        return remindBeforeList;
    }

    public void setRemindBeforeList(List<RemindBefore> remindBeforeList) {
        this.remindBeforeList = remindBeforeList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllDataBean that = (AllDataBean) o;
        return startTime == that.startTime &&
                Objects.equals(title, that.title) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(repeatStrategy, that.repeatStrategy) &&
                Objects.equals(remindBeforeList, that.remindBeforeList);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(title, remark, startTime, repeatStrategy, remindBeforeList);
    }

    @Override
    public String toString() {
        return "AllDataBean{" +
                "title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", startTime=" + startTime +
                ", repeatStrategy=" + repeatStrategy +
                ", remindBeforeList=" + remindBeforeList +
                '}';
    }

    public AllDataBean(String title, String remark, long startTime, RepeatStrategy repeatStrategy, List<RemindBefore> remindBeforeList) {
        this.title = title;
        this.remark = remark;
        this.startTime = startTime;
        this.repeatStrategy = repeatStrategy;
        this.remindBeforeList = remindBeforeList;
    }
}
