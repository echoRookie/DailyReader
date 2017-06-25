package com.example.rookie.dailyreader.util;

import android.util.Log;

import com.example.rookie.dailyreader.bean.NewspaperInfo;
import com.example.rookie.dailyreader.gson.DuanziGson;
import com.example.rookie.dailyreader.gson.MeiziGson;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    /*生成知乎日报专栏api*/
    public static String getSectionApi(String id){
        StringBuilder sectionApi = new StringBuilder();

        sectionApi.append("http://news-at.zhihu.com/api/3/section/").append(id);

        return String.valueOf(sectionApi);
    }
    /*获取知乎日报专栏之前的消息*/
    public static String getSectionBeforeApi(String id,String timestamp){
        /*timestamp时时间戳，请求之前的数据时需要加上前一个返回数据的时间戳，为json对应的属性*/
        StringBuilder sectionBeforeApi = new StringBuilder();

        sectionBeforeApi.append("http://news-at.zhihu.com/api/3/section/").append(id).append("/before/").append(timestamp);

        return String.valueOf(sectionBeforeApi);
    }

   /*解析主题日报返回链表*/
    public static ArrayList<NewspaperInfo> getNewspaper(){
        ArrayList<NewspaperInfo> myList = new ArrayList<>();
        NewspaperInfo info1 = new NewspaperInfo();
        info1.setPaperId("12");
        info1.setPaperName("用户推荐日报");
        info1.setTitle("内容由知乎用户推荐，海纳主题百万，趣味上天入地");
        info1.setImageUrl("http://pic4.zhimg.com/2c38a96e84b5cc8331a901920a87ea71.jpg");
        NewspaperInfo info2 = new NewspaperInfo();
        info2.setPaperId("13");
        info2.setPaperName("日常心理学");
        info2.setTitle("了解自己和别人，了解彼此的欲望和局限。");
        info2.setImageUrl("http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg");
        NewspaperInfo info3 = new NewspaperInfo();
        info3.setPaperId("3");
        info3.setPaperName("电影日报");
        info3.setTitle("除了经典和新片，我们还关注技术和产业");
        info3.setImageUrl("http://pic3.zhimg.com/00eba01080138a5ac861d581a64ff9bd.jpg");
        NewspaperInfo info4 = new NewspaperInfo();
        info4.setPaperId("11");
        info4.setPaperName("不许无聊");
        info4.setTitle("为你发现最有趣的新鲜事，建议在 WiFi 下查看");
        info4.setImageUrl("http://pic4.zhimg.com/4aa8400ba46d3d46e34a9836744ea232.jpg");
        NewspaperInfo info5 = new NewspaperInfo();
        info5.setPaperId("4");
        info5.setPaperName("设计日报");
        info5.setTitle("好设计需要打磨和研习，我们分享灵感和路径");
        info5.setImageUrl("http://p1.zhimg.com/d3/7b/d37b38d5c82b4345ccd7e50c4ae997da.jpg");
        NewspaperInfo info6 = new NewspaperInfo();
        info6.setPaperId("5");
        info6.setPaperName("大公司日报");
        info6.setTitle("商业世界变化越来越快，就是这些家伙干的");
        info6.setImageUrl("http://pic4.zhimg.com/aa94e197491fb9c44d384c4747773810.jpg");
        NewspaperInfo info7 = new NewspaperInfo();
        info7.setPaperId("6");
        info7.setPaperName("财经日报");
        info7.setTitle("从业者推荐的财经金融资讯");
        info7.setImageUrl("http://pic2.zhimg.com/f2e97ff073e5cf9e79c7ed498727ebd6.jpg");
        NewspaperInfo info8 = new NewspaperInfo();
        info8.setPaperId("10");
        info8.setPaperName("互联网安全");
        info8.setTitle("把黑客知识科普到你的面前");
        info8.setImageUrl("http://pic2.zhimg.com/98d7b4f8169c596efb6ee8487a30c8ee.jpg");
        NewspaperInfo info9 = new NewspaperInfo();
        info9.setPaperId("2");
        info9.setPaperName("开始游戏");
        info9.setTitle("如果你喜欢游戏，就从这里开始");
        info9.setImageUrl("http://pic3.zhimg.com/2f214a4ca51855670668530f7d333fd8.jpg");
        NewspaperInfo info10 = new NewspaperInfo();
        info10.setPaperId("7");
        info10.setPaperName("音乐日报");
        info10.setTitle("有音乐就很好");
        info10.setImageUrl("http://pic4.zhimg.com/eac535117ed895983bd2721f35d6e8dc.jpg");
        NewspaperInfo info11 = new NewspaperInfo();
        info11.setPaperId("9");
        info11.setPaperName("动漫日报");
        info11.setTitle("用技术的眼睛仔细看懂每一部动画和漫画");
        info11.setImageUrl("http://pic1.zhimg.com/a0f97c523c64e749c700b2ddc96cfd7c.jpg");
        NewspaperInfo info12 = new NewspaperInfo();
        info12.setPaperId("8");
        info12.setPaperName("体育日报");
        info12.setTitle("关注体育，不吵架。");
        info12.setImageUrl("http://pic1.zhimg.com/bcf7d594f126e5ceb22691be32b2650a.jpg");
        myList.add(info1);
        myList.add(info2);
        myList.add(info3);
        myList.add(info4);
        myList.add(info5);
        myList.add(info6);
        myList.add(info7);
        myList.add(info8);
        myList.add(info9);
        myList.add(info10);
        myList.add(info11);
        myList.add(info12);
        return myList;
    }
}
