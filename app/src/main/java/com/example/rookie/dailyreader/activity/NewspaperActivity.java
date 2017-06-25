package com.example.rookie.dailyreader.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.rookie.dailyreader.MainActivity;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.NewspaperApater;
import com.example.rookie.dailyreader.adapter.PopupWindowAdapter;
import com.example.rookie.dailyreader.adapter.PopupWindowAdapterOne;
import com.example.rookie.dailyreader.adapter.SectionAdapter;
import com.example.rookie.dailyreader.bean.NewspaperInfo;
import com.example.rookie.dailyreader.bean.SectionInfo;
import com.example.rookie.dailyreader.db.MypaperDb;
import com.example.rookie.dailyreader.gson.NewsPagerGson;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewspaperActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView add;
    private RecyclerView recyclerView;
    private RecyclerView recycler;
    private RecyclerView recyclerselect;
    private Window myWindow;
    private NewspaperApater myAdapter;
    private ArrayList<NewspaperInfo> mylists = new ArrayList<>();
    private ArrayList<NewspaperInfo> mylistsOne = new ArrayList<>();
    private ArrayList<NewspaperInfo> mySelectlists = new ArrayList<>();
    private PopupWindow window;
    private View view;
    private PopupWindowAdapter popupWindowAdapter;
    private PopupWindowAdapterOne popupWindowAdapterOne;
    private ImageView popupWindowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_section_main);
        //查找数据中存储的订阅的日报,初始化recyclerview
        List<MypaperDb> mypaperDbs = DataSupport.findAll(MypaperDb.class);
        for(int i= 0;i<mypaperDbs.size();i++){
            NewspaperInfo info = new NewspaperInfo();
            info.setTitle(mypaperDbs.get(i).getTitle());
            info.setPaperId(mypaperDbs.get(i).getPaperId());
            info.setPaperName(mypaperDbs.get(i).getPaperName());
            info.setImageUrl(mypaperDbs.get(i).getImageUrl());
            mylists.add(info);
            mylistsOne.add(info);
        }
        mySelectlists = HttpUtil.getNewspaper();
        //去掉用户已经选择的日报
        for(int i = 0;i<mySelectlists.size();i++){
            for (NewspaperInfo info:mylists) {
                if(mySelectlists.get(i).getPaperId().equals(info.getPaperId())){
                    mySelectlists.remove(i);
                }
            }
        }
        Log.d("kkk", "onCreate: "+mySelectlists.size());
        /*toolbar标题和返回键的设置*/
        toolbar = (Toolbar) findViewById(R.id.news_section_toolbar);
        toolbar.setTitle(R.string.nav_newspaper);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        myWindow = getWindow();
        //初始化添加按钮及点击事件
        add = (ImageView) findViewById(R.id.newspaper_add);
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(getApplicationContext(),"diandin",Toast.LENGTH_SHORT).show();*/
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.popupwindow, null);


                // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

                        window = new PopupWindow(view,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);

                // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
                window.setTouchable(true);
                window.setFocusable(true);
                window.setOutsideTouchable(true);


                // 实例化一个ColorDrawable颜色
                ColorDrawable dw = new ColorDrawable(0xffffffff);
                window.setBackgroundDrawable(dw);

                //popupWindow 控件的初始化
                recycler = (RecyclerView) window.getContentView().findViewById(R.id.mypaper_recycler);
                recyclerselect = (RecyclerView) window.getContentView().findViewById(R.id.paper_recycler);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
                GridLayoutManager gridLayoutManagerOne = new GridLayoutManager(getApplicationContext(),3);
                recycler.setLayoutManager(gridLayoutManager);
                recyclerselect.setLayoutManager(gridLayoutManagerOne);
                popupWindowAdapter = new PopupWindowAdapter(mylists,getApplicationContext());
                popupWindowAdapterOne = new PopupWindowAdapterOne(mySelectlists,getApplicationContext());
                popupWindowAdapter.setPopupWindowAdapter(popupWindowAdapterOne);
                popupWindowAdapterOne.setPopupWindowAdapter(popupWindowAdapter);
                popupWindowAdapter.setMyNewspaperApater(myAdapter);
                popupWindowAdapterOne.setMyNewspaperApater(myAdapter);
                recycler.setAdapter(popupWindowAdapter);
                recyclerselect.setAdapter(popupWindowAdapterOne);
                //点击返回退出popupwindow
                popupWindowBack = (ImageView) window.getContentView().findViewById(R.id.window_back);
                popupWindowBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                    }
                });
                // 设置popWindow的点击背景变暗和消失时的监听事件
                window.showAtLocation(add,Gravity.BOTTOM,0,0);
                WindowManager.LayoutParams params = myWindow.getAttributes();
                params.alpha = 0.5f;
                myWindow.setAttributes(params);
                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams params = myWindow.getAttributes();
                        params.alpha = 1.0f;
                        myWindow.setAttributes(params);
                    }
                });


            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.news_section_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        myAdapter = new NewspaperApater(mylistsOne,getApplicationContext());
        recyclerView.setAdapter(myAdapter);

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
