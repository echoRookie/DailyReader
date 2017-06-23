package com.example.rookie.dailyreader.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by rookie on 2017/6/23.
 */

public class MeiziPagerAdapter extends PagerAdapter{
    private ArrayList<PhotoView> myList;
    public MeiziPagerAdapter(ArrayList<PhotoView> list){
        myList = list;
    }
    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       container.addView(myList.get(position));
        return  myList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(myList.get(position));
    }
}
