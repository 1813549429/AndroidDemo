package com.example.remind.adapter;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.remind.enums.MainItemType;


import java.util.List;

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

    @Override
    public void setChildNode(List<BaseNode> childNode) {
        this.childNode = childNode;
    }
}
