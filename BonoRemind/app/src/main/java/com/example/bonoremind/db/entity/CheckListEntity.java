package com.example.bonoremind.db.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "checkList")
public class CheckListEntity {

    /**
     * 自增长id
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * 清单的名字
     */
    private String title;

    /**
     * 清单所处的位置，清单的位置根据这个position的大小排序决定
     */
    private int position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckListEntity checkListEntity = (CheckListEntity) o;
        return id == checkListEntity.id &&
                position == checkListEntity.position &&
                Objects.equals(title, checkListEntity.title);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, title, position);
    }

    @Override
    public String toString() {
        return "CheckList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", position=" + position +
                '}';
    }
}
