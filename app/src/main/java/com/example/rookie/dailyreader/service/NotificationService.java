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
                title = newsGson.myStorys.get(0).title;
                Log.d("yyyyyyy", "onResponse: "+title);
                newsId = newsGson.myStorys.get(0).id;
                Log.d("yyyyyyy", "onResponse: "+newsId);
                /*开启消息通知*/
                Intent intentNotification=new Intent(getApplicationContext(), NewsDetialActivity.class);
                intentNotification.putExtra("newsId",newsId);
                intentNotification.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,intentNotification,0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setContentTitle("DailyReader");
                builder.setContentIntent(pi);
                builder.setPriority(NotificationCompat.PRIORITY_MAX);
                builder.setStyle(new NotificationCompat.BigTextStyle(builder).bigText(title));
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setShowWhen(true);
                builder.setWhen(System.currentTimeMillis());
                builder.setSubText("time");
                builder.setAutoCancel(true);
                builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                Notification notification = builder.build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(1,notification);

            }
        });

        /*定时任务唤醒服务*/
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int hour = 1*60*60*1000;
        long triggerHour = SystemClock.elapsedRealtime() + hour;
        Intent intentService = new Intent(this,NotificationService.class);
        PendingIntent piService = PendingIntent.getService(this,0,intentService,0);
        alarmManager.cancel(piService);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerHour,piService);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
