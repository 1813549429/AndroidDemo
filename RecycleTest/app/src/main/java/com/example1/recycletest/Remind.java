package com.example1.recycletest;

public class Remind {
    private String title;
    private boolean isCompleted;
    private long time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Remind(String title, boolean isCompleted, long time) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.time = time;
    }
}
