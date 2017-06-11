package com.example.rookie.dailyreader.util;

import android.util.Log;

import com.example.rookie.dailyreader.gson.DuanziGson;
import com.example.rookie.dailyreader.gson.MeiziGson;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rookie on 2017/5/16.
 */

public class HttpUtil {

    public static String getUrl(){
        StringBuilder meiziApi = new StringBuilder();
        Random random = new Random();
        int page=random.nextInt(20)+1;
        meiziApi.append("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/").append("20").append("/" +page);

        return String.valueOf(meiziApi);
    }


    /*连接地址*/
    public  static void  sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
   /* 解析妹子图片数据*/
    public  static MeiziGson handleMeiziResponse(String response){
        try {
            return  new Gson().fromJson(response,MeiziGson.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*生成文章详情页的地址链接*/
    public static String getNewsUrl(String id){
        StringBuilder newsDetailApi = new StringBuilder();

        newsDetailApi.append("http://news-at.zhihu.com/api/4/news/").append(id);

        return String.valueOf(newsDetailApi);
    }
    /*获取前几天的知乎日报消息*/
    public static  String getBeforeNews(Integer count){
        StringBuilder newsBeforeApi = new StringBuilder();
        newsBeforeApi.append("http://news.at.zhihu.com/api/4/news/before/").append(DateUtil.getDateNow(count));
        return String.valueOf(newsBeforeApi);
    }

}
