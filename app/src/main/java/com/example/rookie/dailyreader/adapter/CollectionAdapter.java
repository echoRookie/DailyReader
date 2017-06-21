package com.example.rookie.dailyreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/16.
 * 收藏界面viewpager的主适配器
 */

public class CollectionAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> myFragment;
    private ArrayList<String> mytitle;

    public CollectionAdapter(FragmentManager fm,ArrayList<Fragment> fragment,ArrayList<String> title) {
        super(fm);
        myFragment = fragment;
        mytitle = title;
    }

    @Override
    public Fragment getItem(int position) {
        return myFragment.get(position);
    }

    @Override
    public int getCount() {
        return myFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mytitle.get(position);
    }
}
