package com.example.rookie.dailyreader.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2017/6/10.
 */

public class NewsRecyclerGson {
    @SerializedName("stories")
    public List<Story> Storys;
    public class Story{
        public List<String> images ;
        public String id;
        public String title;

    }
}
