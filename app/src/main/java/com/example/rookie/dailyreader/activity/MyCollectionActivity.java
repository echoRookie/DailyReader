package com.example.rookie.dailyreader.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.CollectionAdapter;
import com.example.rookie.dailyreader.adapter.CollectionDuanziAdapter;
import com.example.rookie.dailyreader.fragment.CollectionDuanziFragment;
import com.example.rookie.dailyreader.fragment.CollectionMeiziFragment;
import com.example.rookie.dailyreader.fragment.CollectionNewsFragment;

import java.util.ArrayList;


public class MyCollectionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> myFragments;
    private ArrayList<String> mytitles;
    private CollectionAdapter collectionAdapter;
    private CollectionDuanziFragment collectionDuanziFragment;
    private CollectionNewsFragment collectionNewsFragment;
    private CollectionNewsFragment collectionNewsFragment1;
    private CollectionMeiziFragment collectionMeiziFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_main);
        /*初始化fragment*/
        myFragments = new ArrayList<>();
        collectionNewsFragment = new CollectionNewsFragment();
        collectionMeiziFragment = new CollectionMeiziFragment();
        collectionDuanziFragment = new CollectionDuanziFragment();
        myFragments.add(collectionMeiziFragment);
        myFragments.add(collectionDuanziFragment);
        myFragments.add(collectionNewsFragment);
        /*初始化Tab标签*/
        mytitles = new ArrayList<>();
        mytitles.add("图片");
        mytitles.add("段子");
        mytitles.add("趣闻");
         /*toolbar的相关设置*/
        toolbar = (Toolbar) findViewById(R.id.collection_toolbar);
        toolbar.setTitle(R.string.collection_title);
        tabLayout = (TabLayout) findViewById(R.id.collection_tablayout);
        viewPager = (ViewPager) findViewById(R.id.collection_viewpager);
        collectionAdapter = new CollectionAdapter(getSupportFragmentManager(),myFragments,mytitles);
        /*viewpager与tab的绑定*/
        viewPager.setAdapter(collectionAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(collectionAdapter);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*toolbar的home键的点击返回事件*/
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
