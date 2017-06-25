package com.example.rookie.dailyreader.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.NewspaperItemAdapter;
import com.example.rookie.dailyreader.bean.ViewPagerItem;
import com.example.rookie.dailyreader.gson.NewsRecyclerGson;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewspaperItemActivity extends AppCompatActivity {
    private String paperName;
    private String paperId;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private Toolbar toolbar;
    private NewsRecyclerGson newsRecyclerGson;
    private NewspaperItemAdapter myAdapter;
    private ArrayList<ViewPagerItem> myLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_section_main);
          /*得到intent传递的值*/
        paperName = getIntent().getStringExtra("paperName");
        paperId = getIntent().getStringExtra("paperId");
        Log.d("vvv", "onCreate: "+paperName);
        Log.d("vvv", "onCreate: "+paperId);
        recyclerView = (RecyclerView) findViewById(R.id.news_section_recycler);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        /*toolbar标题和返回键的设置*/
        toolbar = (Toolbar) findViewById(R.id.news_section_toolbar);
        toolbar.setTitle(paperName);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //网络请求数据并解析
        HttpUtil.sendOkHttpRequest(HttpUtil.getThemeApi(paperId), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                newsRecyclerGson = new Gson().fromJson(response.body().string(),NewsRecyclerGson.class);
                for (int i=0;i<newsRecyclerGson.Storys.size();i++){
                    ViewPagerItem viewPagerItem = new ViewPagerItem();
                    Log.d("vvvv", "onResponse: "+newsRecyclerGson.Storys.get(i).id);
                    viewPagerItem.setId(newsRecyclerGson.Storys.get(i).id);
                    viewPagerItem.setTitle(newsRecyclerGson.Storys.get(i).title);
                    if(newsRecyclerGson.Storys.get(i).images!=null){
                    viewPagerItem.setImageUrl(newsRecyclerGson.Storys.get(i).images.get(0));}
                    else {
                        viewPagerItem.setImageUrl(null);
                    }
                    myLists.add(viewPagerItem);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myAdapter = new NewspaperItemAdapter(myLists,getApplicationContext());
                            recyclerView.setAdapter(myAdapter);
                            Log.d("vvvv", "onCreate: "+myAdapter.getItemCount());
                        }
                    });

                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*处理系统默认返回键的点击逻辑*/
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
