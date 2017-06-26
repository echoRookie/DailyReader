package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

/**
 * Created by rookie on 2017/6/24.
 * 用户已订阅日报表
 */

public class MypaperDb extends DataSupport {
    //id 主键
    private int id;
    //日报id
    private String paperId;
    //名称
    private String paperName;
    //对应图片
    private String imageUrl;
    //日报描述
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
