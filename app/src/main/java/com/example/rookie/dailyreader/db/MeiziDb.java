package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

/**
 * Created by rookie on 2017/5/16.
 */

public class MeiziDb extends DataSupport{
    private int id;
    private String iamgeUrl;

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
}
