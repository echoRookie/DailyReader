package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by rookie on 2017/6/15.
 * 数据库美图收藏信息对应的映射类
 */

public class CollectionMeiziDb extends DataSupport {
    //图片表主键 ，默认自增长
    private int id;
    //图片下载地址
    private String iamgeUrl;
    //存储时间
    private Date saveDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }
}
