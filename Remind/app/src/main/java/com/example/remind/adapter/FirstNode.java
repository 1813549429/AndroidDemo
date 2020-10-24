package com.example.remind.adapter;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;


import java.util.List;

public class FirstNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String title;
    private int itemCount;
    private int type;

    public FirstNode(List<BaseNode> childNode, String title, int itemCount, int type) {
        this.childNode = childNode;
        this.title = title;
        this.itemCount = itemCount;
        this.type = type;
        setExpanded(false);
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


    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
