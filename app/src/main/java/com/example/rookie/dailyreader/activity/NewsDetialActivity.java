package com.example.rookie.dailyreader.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.gson.NewsInfoGson;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsDetialActivity extends AppCompatActivity {
    private ImageView imageView;
    private Toolbar toolbar;
    private WebView webView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NewsInfoGson newsInfoGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String newsId = getIntent().getStringExtra("newsId");
        Log.d("fffffffff", "onCreate: "+newsId);
        HttpUtil.sendOkHttpRequest(HttpUtil.getNewsUrl(newsId), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String newsData = response.body().string();
                Log.d("llllllllllll", "onResponse: "+newsData);
                newsInfoGson = new Gson().fromJson(newsData,NewsInfoGson.class);
                Log.d("llll", "onResponse: "+newsInfoGson.body);
                Log.d("lllll", "onResponse: "+newsInfoGson.id);
                Log.d("llllll", "onResponse: "+newsInfoGson.image);
                Log.d("lllllll", "onResponse: "+newsInfoGson.section.id);
                Log.d("llllllll", "onResponse: "+newsInfoGson.section.name);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        imageView = (ImageView) findViewById(R.id.news_detail_image);
        Glide.with(this).load(newsInfoGson.image).into(imageView);
        toolbar = (Toolbar) findViewById(R.id.news_detail_toolbar);
        webView = (WebView) findViewById(R.id.news_detail_webview);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.news_detail_collapsing);
        setSupportActionBar(toolbar);
        webView.setScrollbarFadingEnabled(true);

        //能够和js交互

        webView.getSettings().setJavaScriptEnabled(true);

        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标

        webView.getSettings().setBuiltInZoomControls(false);

        //缓存

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //开启DOM storage API功能

        webView.getSettings().setDomStorageEnabled(true);

        //开启application Cache功能

        webView.getSettings().setAppCacheEnabled(false);
        String result = newsInfoGson.body;
        result = result.replace("<div class=\"img-place-holder\">", "");

        result = result.replace("<div class=\"headline\">", "");


        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        result = "<!DOCTYPE html>\n"

                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"

                + "<head>\n"

                + "\t<meta charset=\"utf-8\" />"

                + css

                + "\n</head>\n"

                + theme

                + result

                + "</body></html>";



        webView.loadDataWithBaseURL("x-data://base", result,"text/html","utf-8",null);
    }
}
