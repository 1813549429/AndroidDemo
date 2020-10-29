package com.example.remind.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.remind.db.entity.CheckListEntity;
import com.example.remind.db.entity.Remind;
import com.example.remind.enums.MainItemType;


import java.util.List;
import java.util.Objects;

public class SecondNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private Remind remindData;
    private CheckListEntity checkListEntityData;
    private int mainItemType;

    public SecondNode(Remind remindData, CheckListEntity checkListEntityData) {
        this.remindData = remindData;
        this.checkListEntityData = checkListEntityData;

    }

    public CheckListEntity getCheckListEntityData() {
        return checkListEntityData;
    }

    public Remind getRemindData() {
        return remindData;
    }

    public int getMainItemType() {
        return mainItemType;
    }

    public void setMainItemType(int mainItemType) {
        this.mainItemType = mainItemType;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }

    @Override
    public void setChildNode(List<BaseNode> childNode) {
        this.childNode = childNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondNode that = (SecondNode) o;
        return Objects.equals(remindData, that.remindData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remindData);
    }
}
