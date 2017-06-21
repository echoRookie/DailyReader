package com.example.rookie.dailyreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/2.
 * 新闻模块轮播图的适配器
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> myFragments;
    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        myFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return myFragments.get(position);
    }

    @Override
    public int getCount() {
        return myFragments.size();
    }
}
