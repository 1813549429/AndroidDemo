package com.example.remind.adapter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.util.List;
import java.util.Objects;

public class FirstNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String title;
    private int itemCount;
    private int type;
    private String bgColor;

    public FirstNode(List<BaseNode> childNode, String title, int itemCount, int type, String bgColor) {

        for (BaseNode baseNode : childNode) {
            SecondNode secondNode = (SecondNode) baseNode;
            secondNode.setMainItemType(type);
        }
        this.childNode = childNode;

        this.title = title;
        this.itemCount = itemCount;
        this.type = type;
        this.bgColor = bgColor;
        setExpanded(true);
    }

    public String getTitle() {
        return title;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getType() {
        return type;
    }

    public String getBgColor() {return bgColor;}



    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        return o.equals(title);
    }


    @Override
    public void setChildNode(List<BaseNode> childNode) {
        this.childNode = childNode;
    }
}
