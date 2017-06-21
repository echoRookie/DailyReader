package com.example.rookie.dailyreader.bean;

/**
 * Created by rookie on 2017/6/2.
 * 文章信息解析类
 */

public class ViewPagerItem {
    //文章图片
    private String imageUrl;
    //文章id
    private String id;
    //文章标题
    private String title;

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
