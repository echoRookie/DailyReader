package com.example.rookie.dailyreader.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by rookie on 2017/6/22.
 */

public class CollectionDb extends DataSupport {
    private int id;
    private Date saveDate;
    private int cateId;
    private int contextId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }
}
