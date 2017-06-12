package com.example.rookie.dailyreader.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rookie on 2017/6/12.
 */

public class NewsInfoGson {
    public String body;
    public String title;
    public String image;
    public String id;
    public Section section;
    public class Section{
        public String thumbnail;
        public String id;
        public String name;
    }
}
