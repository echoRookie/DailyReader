package com.example.rookie.dailyreader.bean;

/**
 * Created by rookie on 2017/5/23.
 * 段子数据封装类
 */

public class DuanziInfo {
    //段子内容
    private String duanzitext;
    //用户名
    private String username;
    //用户头像
    private String usericon;

    public String getDuanzitext() {
        return duanzitext;
    }

    public void setDuanzitext(String duanzitext) {
        this.duanzitext = duanzitext;
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
