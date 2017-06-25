package com.example.rookie.dailyreader.gson;

import java.util.List;

/**
 * Created by rookie on 2017/6/24.
 */

public class NewsPagerGson {
    public List<Newspaper> others;
    public class Newspaper {
        public String name;
        public String id;
        public String description;
        public String thumbnail;
    }
}
