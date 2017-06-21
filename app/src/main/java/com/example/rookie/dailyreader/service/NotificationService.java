package com.example.rookie.dailyreader.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.rookie.dailyreader.activity.MyCollectionActivity;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.NewsDetialActivity;
import com.example.rookie.dailyreader.gson.NewsRecyclerGson;
import com.example.rookie.dailyreader.gson.NewsViewPagerGson;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NotificationService extends Service {
    private String newsData;
    private String title;
    private String newsId;
    private  NewsViewPagerGson newsGson;
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*请求知乎日报的最新消息*/
        HttpUtil.sendOkHttpRequest("http://news-at.zhihu.com/api/4/news/latest", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //解析网络请求数据
                newsData = response.body().string();
                Log.d("bbbb", "onResponse: " + newsData);
                NewsRecyclerGson newsRecyclerGson = new Gson().fromJson(newsData, NewsRecyclerGson.class);
                Log.d("bbbbbbbbbbbbb", "onResponse: " + newsRecyclerGson.Storys.get(0).images.get(0));
                Log.d("bbbbbbbbbbbbb", "onResponse: " + newsRecyclerGson.Storys.get(0).title);
                Log.d("bbbbbbbbbbbbb", "onResponse: " + newsRecyclerGson.Storys.get(0).id);
                newsGson = new Gson().fromJson(newsData, NewsViewPagerGson.class);
                Log.d("bbbbb", "onResponse: " + newsGson.myStorys.size());
                Log.d("bbbbbb", "onResponse: " + newsGson.myStorys.get(4).image);
                Log.d("bbbbbbb", "onResponse: " + newsGson.myStorys.get(4).id);
                Log.d("bbbbbbbb", "onResponse: " + newsGson.myStorys.get(4).title);
                //通知标题
                title = newsGson.myStorys.get(0).title;
                Log.d("yyyyyyy", "onResponse: "+title);
                //文章id
                newsId = newsGson.myStorys.get(0).id;
                Log.d("yyyyyyy", "onResponse: "+newsId);
                /*开启消息通知，点击进入文章详情页*/
                Intent intentNotification=new Intent(getApplicationContext(), NewsDetialActivity.class);
                //存储文章id
                intentNotification.putExtra("newsId",newsId);
                intentNotification.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,intentNotification,0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                //通知标题
                builder.setContentTitle("DailyReader");
                //通知对应的启动事件
                builder.setContentIntent(pi);
                //通知优先级
                builder.setPriority(NotificationCompat.PRIORITY_MAX);
                //通知内容
                builder.setContentText(title);
                //通知图标
                builder.setSmallIcon(R.mipmap.ic_launcher);
                //显示通知时间
                builder.setShowWhen(true);
                //设置通知时间为系统当前时间
                builder.setWhen(System.currentTimeMillis());
                //可以点击通知栏的删除按钮删除
                builder.setAutoCancel(true);
                //系统默认通知样式铃声振动等
                builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                Notification notification = builder.build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //发送通知
                manager.notify(1,notification);

            }
        });

        /*定时任务唤醒服务，获取实例*/
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        /*  唤醒时间间隔*/
        int hour = 1*60*60*1000;
        long triggerHour = SystemClock.elapsedRealtime() + hour;
       /* 开启intent唤醒服务*/
        Intent intentService = new Intent(this,NotificationService.class);
        PendingIntent piService = PendingIntent.getService(this,0,intentService,0);
       /* 开启定时任务*/
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerHour,piService);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
