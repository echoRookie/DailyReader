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
    private Integer count = 0;
    private String beforeNews;
    private String beforeNewsOne;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        Bundle bundle = getArguments();
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
            newsRecyclerView.addOnScrollListener(new RecyclerViewScroll(manager) {
                @Override
                public void onLoadMore() {
                    HttpUtil.sendOkHttpRequest(HttpUtil.getBeforeNews(count), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            beforeNews = response.body().string();

                        }
                    });
                    --count;
                    Log.d("gggggg", "onLoadMore: " + count);

                    if (beforeNews == null) {
                        beforeNews = beforeNewsOne;
                    }
                    Log.d("ggggggg", "onLoadMore: " + beforeNews);
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
            myviewPager = (ViewPager) view.findViewById(R.id.news_viewpager);
            linearLayout = (LinearLayout) view.findViewById(R.id.news_linear);
            myFragments = new ArrayList<>();
            for (int i = 0; i < viewPagerImageUrl.size(); i++) {
                ViewPagerItemFragment viewPagerItemFragment = ViewPagerItemFragment.newInstance(viewPagerImageUrl.get(i), viewPagerText.get(i), viewPagerId.get(i));
                myFragments.add(viewPagerItemFragment);
            }
            viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), myFragments);
            for (int i = 0; i < myFragments.size(); i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.shape_point_selector);
                int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
                if (i > 0) {
                    params.leftMargin = pointSize;
                    imageView.setSelected(false);
                } else {
                    imageView.setSelected(true);
                }
                imageView.setLayoutParams(params);
                linearLayout.addView(imageView);
            }
            myviewPager.setAdapter(viewPagerAdapter);
            myviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                int lastPosition;

                @Override
                public void onPageSelected(int position) {
                    position = position % myFragments.size();
                    linearLayout.getChildAt(position).setSelected(true);
                    linearLayout.getChildAt(lastPosition).setSelected(false);
                    lastPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int currtposition = myviewPager.getCurrentItem();
                    if (currtposition == myviewPager.getAdapter().getCount() - 1) {
                        myviewPager.setCurrentItem(0);
                    } else {
                        myviewPager.setCurrentItem(++currtposition);
                    }
                    handler.postDelayed(this, 6000);
                }
            }, 6000);
        }

        return view;
    }

}
