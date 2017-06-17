package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

/**
 * Created by rookie on 2017/5/23.
 */

public class DuanziDb extends DataSupport{
    private int id;
    private String text;
    private String username;
    private String usericon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }
}
