package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.NewsAdapter;
import com.example.rookie.dailyreader.adapter.ViewPagerAdapter;
import com.example.rookie.dailyreader.bean.*;
import com.example.rookie.dailyreader.gson.NewsRecyclerGson;
import com.example.rookie.dailyreader.gson.NewsViewPagerGson;
import com.example.rookie.dailyreader.myinterface.RecyclerViewScroll;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by rookie on 2017/6/2.
 */

public class NewsFragment extends Fragment {
    private ViewPager myviewPager;
    private LinearLayout linearLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> myFragments;
    private Handler handler;
    private ArrayList<String> viewPagerImageUrl = new ArrayList<>();
    private ArrayList<String> viewPagerText = new ArrayList<>();
    private ArrayList<String> viewPagerId = new ArrayList<>();
    private ArrayList<ViewPagerItem> recyclerList = new ArrayList<>();
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    //请求第几天数据的标识符
    private Integer count = 0;
    private String beforeNews;
    private String beforeNewsOne;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        Bundle bundle = getArguments();
        //获取并解析文章内容
        String newsData = bundle.getString("recyclerData");
        viewPagerImageUrl = bundle.getStringArrayList("imageUrl");
        viewPagerText = bundle.getStringArrayList("title");
        viewPagerId = bundle.getStringArrayList("id");
        if (newsData != null && viewPagerImageUrl != null && viewPagerText != null) {
            NewsRecyclerGson newsRecyclerGson = new Gson().fromJson(newsData, NewsRecyclerGson.class);
            for (int i = 0; i < newsRecyclerGson.Storys.size(); i++) {
                ViewPagerItem viewPagerItem = new ViewPagerItem();
                viewPagerItem.setId(newsRecyclerGson.Storys.get(i).id);
                viewPagerItem.setImageUrl(newsRecyclerGson.Storys.get(i).images.get(0));
                viewPagerItem.setTitle(newsRecyclerGson.Storys.get(i).title);
                recyclerList.add(viewPagerItem);
            }

            newsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recycler);
            newsAdapter = new NewsAdapter(getContext(), recyclerList);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            newsRecyclerView.setLayoutManager(manager);
            newsRecyclerView.setAdapter(newsAdapter);
            HttpUtil.sendOkHttpRequest(HttpUtil.getBeforeNews(count), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    beforeNewsOne = response.body().string();

                }
            });
            --count;
            //实现下拉加载更多
            newsRecyclerView.addOnScrollListener(new RecyclerViewScroll(manager) {
                @Override
                public void onLoadMore() {
                    //请求前一天的数据
                    HttpUtil.sendOkHttpRequest(HttpUtil.getBeforeNews(count), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            beforeNews = response.body().string();

                        }
                    });
                    //标识符减一
                    --count;
                    Log.d("gggggg", "onLoadMore: " + count);

                    if (beforeNews == null) {
                        beforeNews = beforeNewsOne;
                    }
                    Log.d("ggggggg", "onLoadMore: " + beforeNews);
                    //recyclerView的通知adapter更新
                    newsRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            NewsRecyclerGson newsRecyclerGsonBefore = new Gson().fromJson(beforeNews, NewsRecyclerGson.class);
                            for (int i = 0; i < newsRecyclerGsonBefore.Storys.size(); i++) {
                                ViewPagerItem viewPagerItem = new ViewPagerItem();
                                viewPagerItem.setId(newsRecyclerGsonBefore.Storys.get(i).id);
                                viewPagerItem.setImageUrl(newsRecyclerGsonBefore.Storys.get(i).images.get(0));
                                viewPagerItem.setTitle(newsRecyclerGsonBefore.Storys.get(i).title);
                                recyclerList.add(viewPagerItem);
                            }
                            Log.d("ggggggggggg", "run: " + recyclerList.size());
                            newsAdapter.notifyDataSetChanged();
                        }
                    });

                }
            });
            //初始化轮播图的viewpager布局
            myviewPager = (ViewPager) view.findViewById(R.id.news_viewpager);
            //初始化轮播图的指示器布局
            linearLayout = (LinearLayout) view.findViewById(R.id.news_linear);
            //轮播图viewpager的fragment初始化
            myFragments = new ArrayList<>();
            for (int i = 0; i < viewPagerImageUrl.size(); i++) {
                ViewPagerItemFragment viewPagerItemFragment = ViewPagerItemFragment.newInstance(viewPagerImageUrl.get(i), viewPagerText.get(i), viewPagerId.get(i));
                myFragments.add(viewPagerItemFragment);
            }
            //viewpager适配器初始化
            viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), myFragments);
            //轮播图指示器的初始化
            for (int i = 0; i < myFragments.size(); i++) {
                //初始化圆点图片
                ImageView imageView = new ImageView(getContext());
                //圆点背景色，图片为颜色选择器
                imageView.setImageResource(R.drawable.shape_point_selector);
                //圆点大小
                int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
                //圆点属性设置
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
                if (i > 0) {
                    params.leftMargin = pointSize;
                    imageView.setSelected(false);
                } else {
                    imageView.setSelected(true);
                }
                imageView.setLayoutParams(params);
                //动态添加指示器图标
                linearLayout.addView(imageView);
            }
            myviewPager.setAdapter(viewPagerAdapter);
            //viewpager滑动事件的监听
            myviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                int lastPosition;

                @Override
                public void onPageSelected(int position) {
                    //当前界面的位置
                    position = position % myFragments.size();
                    //设置指示器颜色为选中
                    linearLayout.getChildAt(position).setSelected(true);
                    //设置指示器前一页颜色为未选中
                    linearLayout.getChildAt(lastPosition).setSelected(false);
                    lastPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            handler = new Handler();
            //实例化handler不断给自己发送消息最，时间间隔为2秒
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //获得viewpager当前选中页
                    int currtposition = myviewPager.getCurrentItem();
                    //最后一页则跳转到第一页
                    if (currtposition == myviewPager.getAdapter().getCount() - 1) {
                        myviewPager.setCurrentItem(0);
                    }
                    //否则当前页加一
                    else {
                        myviewPager.setCurrentItem(++currtposition);
                    }
                    handler.postDelayed(this, 6000);
                }
            }, 6000);
        }

        return view;
    }

}
