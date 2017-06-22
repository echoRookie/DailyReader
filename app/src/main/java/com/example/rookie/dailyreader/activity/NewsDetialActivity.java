package com.example.rookie.dailyreader.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.db.CollectionDb;
import com.example.rookie.dailyreader.db.CollectionMeiziDb;
import com.example.rookie.dailyreader.db.CollectionNewsDb;
import com.example.rookie.dailyreader.db.MeiziDb;
import com.example.rookie.dailyreader.db.NewsDb;
import com.example.rookie.dailyreader.gson.NewsInfoGson;
import com.example.rookie.dailyreader.util.DateUtil;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsDetialActivity extends AppCompatActivity {
    private ImageView imageView;
    private NewsInfoGson newsInfoGson;
    private WebView webView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private boolean flag = false;
    private String title;
    private String imageUrl;
    private LinearLayout linearLayout;
    private ImageView smallImage;
    private TextView section;
    private ImageView goSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
//      接收intent的值传递，并去数据库查询是否存在，设置flag标志的值
//        true为已收藏，false未收藏
        final String newsId = getIntent().getStringExtra("newsId");
        List<CollectionNewsDb> myLists = DataSupport.where("newsId = ?", newsId).find(CollectionNewsDb.class);
        Log.d("ppp", "onCreate: " + newsId);
       /* List<NewsDb> myLists = DataSupport.where("newsId = ?", newsId).find(NewsDb.class);*/
        /*floatingActionButton初始化及默认状态的设置*/
        floatingActionButton = (FloatingActionButton) findViewById(R.id.news_detail_floatbutton);
        if (myLists.size() > 0) {
           /* 如果数据库里查询到已存在，则设置为已收藏。否则相反*/
            flag = true;
            floatingActionButton.setImageResource(R.drawable.collection_selected);
        } else {
            flag = false;
            floatingActionButton.setImageResource(R.drawable.news_collection);
        }
//        文章合集布局的初始化
        linearLayout = (LinearLayout) findViewById(R.id.news_detail_section);
        smallImage = (ImageView) findViewById(R.id.news_detail_smallImage);
        section = (TextView) findViewById(R.id.news_detail_newsFrom);
        goSection = (ImageView) findViewById(R.id.news_detail_goSection);
        imageView = (ImageView) findViewById(R.id.news_detail_image);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.news_detail_collapsing);
//        toolbar初始化及默认返回键的设置
        toolbar = (Toolbar) findViewById(R.id.news_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        webView = (WebView) findViewById(R.id.news_detail_webview);
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
        //文章详细信息的网络请求
        HttpUtil.sendOkHttpRequest(HttpUtil.getNewsUrl(newsId), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                newsInfoGson = new Gson().fromJson(data, NewsInfoGson.class);
                String result = newsInfoGson.body;
                //判断文章是否来自某个专栏，如果有，则显示相应布局，默认为不显示
                if (newsInfoGson.section != null) {
                    Log.d("section", "onResponse: " + newsInfoGson.section.name);
                    Log.d("section", "onResponse: " + newsInfoGson.section.id);
                    Log.d("section", "onResponse: " + newsInfoGson.section.thumbnail);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            linearLayout.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext()).load(newsInfoGson.image).into(smallImage);
                            section.setText("  本文来自:  " + newsInfoGson.section.name + "  -- 合集");
                            goSection.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(v.getContext(), NewsSectionActivity.class);
                                    intent.putExtra("sectionName", newsInfoGson.section.name);
                                    intent.putExtra("sectionId", newsInfoGson.section.id);
                                    startActivity(intent);
                                }
                            });
                        }
                    });

                }
                title = newsInfoGson.title;
                imageUrl = newsInfoGson.image;
                Log.d("yyy", "onCreate: " + title);
                Log.d("yyyyy", "onCreate: " + imageUrl);
                // 将文章内容组装成 html网页
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


                final String finalResult = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        collapsingToolbarLayout.setTitle(newsInfoGson.title);
                        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_color));
                        Glide.with(getApplicationContext()).load(newsInfoGson.image).into(imageView);
                        webView.loadDataWithBaseURL("x-data://base", finalResult, "text/html", "utf-8", null);
                    }
                });

            }
        });

       /*floatbutton的按钮点击事件*/
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false) {
                    floatingActionButton.setImageResource(R.drawable.collection_selected);
                    CollectionNewsDb db = new CollectionNewsDb();
                    db.setNewsId(newsId);
                    db.setSaveDate(new Date());
                    db.setTitle(title);
                    db.setImageUrl(imageUrl);
                    db.save();
                   /* NewsDb newsDb = new NewsDb();
                    newsDb.setNewsId(newsId);
                    newsDb.setTitle(title);
                    newsDb.setImageUrl(imageUrl);
                    newsDb.save();
                    CollectionDb collectionDb = new CollectionDb();
                    collectionDb.setSaveDate(new Date());
                    collectionDb.setCateId(3);
                    List<NewsDb> newsDbList = DataSupport.where("newsId = ?",newsId).find(NewsDb.class);
                    collectionDb.setContextId(newsDbList.get(0).getId());
                    collectionDb.save();*/
                    flag = true;
                    Toast.makeText(NewsDetialActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                } else {

                    floatingActionButton.setImageResource(R.drawable.news_collection);
                    flag = false;
                    DataSupport.deleteAll(CollectionNewsDb.class, "newsId = ?", newsId);
                   /* DataSupport.deleteAll(MeiziDb.class,"newsId = ?", newsId);
                    List<NewsDb> newsDbList = DataSupport.where("newsId = ?",newsId).find(NewsDb.class);
                    String contextId =String.valueOf(newsDbList.get(0).getId()) ;
                    DataSupport.deleteAll(CollectionDb.class,"cateId = ? and contextId = ?","3",contextId);*/
                    Toast.makeText(NewsDetialActivity.this, "收藏已取消", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
