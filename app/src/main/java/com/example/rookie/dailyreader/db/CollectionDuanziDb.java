package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by rookie on 2017/6/15.
 * 数据库段子收藏信息对应的映射类
 */

public class CollectionDuanziDb extends DataSupport{
    //id 段子信息表的主键，默认自增长
    private int id;
    //段子内容
    private String text;
    //发布段子用户名
    private String username;
    //用户头像
    private String usericon;
    //存储时间
    private Date saveDate;

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

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
