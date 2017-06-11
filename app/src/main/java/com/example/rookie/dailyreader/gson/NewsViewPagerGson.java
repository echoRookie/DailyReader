package com.example.rookie.dailyreader.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2017/6/2.
 */

public class NewsViewPagerGson {
    @SerializedName("top_stories")
    public List<TopStory> myStorys;
    public class TopStory{
        public String image;
        public String id;
        public String title;
    }
}
