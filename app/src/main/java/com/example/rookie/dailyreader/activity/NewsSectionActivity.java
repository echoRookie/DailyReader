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
import com.example.rookie.dailyreader.adapter.SectionAdapter;
import com.example.rookie.dailyreader.bean.SectionInfo;
import com.example.rookie.dailyreader.gson.SectionGson;
import com.example.rookie.dailyreader.myinterface.RecyclerViewScroll;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsSectionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String sectionName;
    private String sectionId;
    private String sectionData;
    private String timestamp;
    private SectionGson sectionGson;
    private ArrayList<SectionInfo> myLists = new ArrayList<>();
    private RecyclerView recyclerView;
    private SectionAdapter myAdapter;
    private LinearLayoutManager manage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_section_main);
        /*得到intent传递的值*/
        sectionName = getIntent().getStringExtra("sectionName");
        sectionId = getIntent().getStringExtra("sectionId");
        Log.d("vvv", "onCreate: "+sectionId);
        Log.d("vvv", "onCreate: "+sectionName);
        recyclerView = (RecyclerView) findViewById(R.id.news_section_recycler);
        manage = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manage);
        /*toolbar标题和返回键的设置*/
        toolbar = (Toolbar) findViewById(R.id.news_section_toolbar);
        toolbar.setTitle(sectionName);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /*网络请求专栏数据*/
        HttpUtil.sendOkHttpRequest(HttpUtil.getSectionApi(sectionId), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sectionData = response.body().string();
                Log.d("vvvvv", "onResponse: "+sectionData);
                sectionGson = new Gson().fromJson(sectionData,SectionGson.class);
                timestamp = sectionGson.timestamp;
                Log.d("vvv", "onResponse: "+timestamp);
                for(int i=0;i<sectionGson.Storys.size();i++){
                    SectionInfo sectionInfo = new SectionInfo();
                    sectionInfo.setDate(sectionGson.Storys.get(i).display_date);
                    Log.d("vvv", "onResponse: "+sectionGson.Storys.get(i).display_date);
                    sectionInfo.setId(sectionGson.Storys.get(i).id);
                    Log.d("vvv", "onResponse: "+sectionGson.Storys.get(i).id);
                    sectionInfo.setTitle(sectionGson.Storys.get(i).title);
                    Log.d("vvv", "onResponse: "+sectionGson.Storys.get(i).title);
                    sectionInfo.setImageUrl(sectionGson.Storys.get(i).images.get(0));
                    Log.d("vvv", "onResponse: "+sectionGson.Storys.get(i).images.get(0));
                    myLists.add(sectionInfo);
                    Log.d("vvv", "onResponse: "+myLists.size());
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           myAdapter = new SectionAdapter(myLists,getApplicationContext());
                           recyclerView.setAdapter(myAdapter);
                           Log.d("vvvv", "onCreate: "+myAdapter.getItemCount());
                       }
                   });

                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerViewScroll(manage) {
            @Override
            public void onLoadMore() {
                HttpUtil.sendOkHttpRequest(HttpUtil.getSectionBeforeApi(sectionId,timestamp), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        sectionData = response.body().string();
                        Log.d("vvv", "onResponse: "+sectionData);
                        sectionGson = new Gson().fromJson(sectionData,SectionGson.class);
                        timestamp = sectionGson.timestamp;
                        Log.d("vvv", "onResponse: "+timestamp);
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                for(int i=0;i<sectionGson.Storys.size();i++){
                                    SectionInfo sectionInfo = new SectionInfo();
                                    sectionInfo.setDate(sectionGson.Storys.get(i).display_date);
                                    sectionInfo.setId(sectionGson.Storys.get(i).id);
                                    sectionInfo.setTitle(sectionGson.Storys.get(i).title);
                                    sectionInfo.setImageUrl(sectionGson.Storys.get(i).images.get(0));
                                    myLists.add(sectionInfo);
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                });
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
