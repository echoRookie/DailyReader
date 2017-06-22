package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

/**
 * Created by rookie on 2017/6/22.
 */

public class CategoryDb extends DataSupport{
    private int id;
    private int cateName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCateName() {
        return cateName;
    }

    public void setCateName(int cateName) {
        this.cateName = cateName;
    }
}
