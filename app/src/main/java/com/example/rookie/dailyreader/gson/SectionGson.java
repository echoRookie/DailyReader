package com.example.rookie.dailyreader.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2017/6/20.
 * 文章专栏部分的数据解析
 */

public class SectionGson {
    public String timestamp;
    @SerializedName("stories")
    public List<Story> Storys;
    public class Story{
        public List<String> images ;
        public String display_date;
        public String id;
        public String title;

    }
}
