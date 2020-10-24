package com.example.reminddemo.data;

import com.example.reminddemo.db.RemindBefore;

import java.util.List;

public class RemindBeforeListBean {

    List<RemindBefore> remindBeforeList;
    String remindBeforeRemark;

    public List<RemindBefore> getRemindBeforeList() {
        return remindBeforeList;
    }

    public void setRemindBeforeList(List<RemindBefore> remindBeforeList) {
        this.remindBeforeList = remindBeforeList;
    }

    public String getRemindBeforeRemark() {
        return remindBeforeRemark;
    }

    public void setRemindBeforeRemark(String remindBeforeRemark) {
        this.remindBeforeRemark = remindBeforeRemark;
    }
}
