package com.example1.recycletest;


import java.util.List;

public class AdapterBean {
    private String title;
    private List<Remind> childNodes;
    private boolean isExpend;
    private int bgColor;

    public AdapterBean(String title, List<Remind> childNodes, boolean isExpend, int bgColor) {
        this.title = title;
        this.childNodes = childNodes;
        this.isExpend = isExpend;
        this.bgColor = bgColor;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Remind> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<Remind> childNodes) {
        this.childNodes = childNodes;
    }

    public boolean isExpend() {
        return isExpend;
    }

    public void setExpend(boolean expend) {
        isExpend = expend;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
