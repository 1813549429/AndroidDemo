package com.example.remind.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.remind.db.entity.CheckList;
import com.example.remind.db.entity.Remind;


import java.util.List;

public class SecondNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private Remind remindData;
    private CheckList checkListData;


    public SecondNode(Remind remindData, CheckList checkListData) {
        this.remindData = remindData;
        this.checkListData = checkListData;

    }

    public CheckList getCheckListData() {
        return checkListData;
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
