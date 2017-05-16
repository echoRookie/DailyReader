package com.example.rookie.dailyreader.util;

import com.example.rookie.dailyreader.gson.MeiziGson;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by rookie on 2017/5/16.
 */

public class HttpUtil {
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
}
