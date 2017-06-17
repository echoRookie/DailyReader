package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by rookie on 2017/6/15.
 */

public class CollectionNewsDb extends DataSupport{
    private int id;
    private String newsId;
    private String title;
    private String imageUrl;
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

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
