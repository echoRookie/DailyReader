package com.example.rookie.dailyreader.bean;

/**
 * Created by rookie on 2017/6/20.
 * 专栏内容封装类
 */

public class SectionInfo {
    //文章图片
    private String imageUrl;
    //文章id
    private String id;
    //文章标题
    private String title;
    //文章发布日期
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
