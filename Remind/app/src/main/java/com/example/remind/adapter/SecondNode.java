package com.example.remind.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.remind.db.entity.CheckListEntity;
import com.example.remind.db.entity.Remind;


import java.util.List;

public class SecondNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private Remind remindData;
    private CheckListEntity checkListEntityData;


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

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
