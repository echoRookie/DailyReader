package com.example.rookie.dailyreader.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2017/5/16.
 * 图片数据解析
 */

public class MeiziGson {
    @SerializedName("results")
    public List<Meizi> MeiziList;
    public  class  Meizi {
        public String url;
    }
}
