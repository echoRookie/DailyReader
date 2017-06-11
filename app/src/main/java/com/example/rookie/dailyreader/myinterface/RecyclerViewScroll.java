package com.example.rookie.dailyreader.myinterface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by rookie on 2017/6/11.
 */

public abstract class RecyclerViewScroll extends RecyclerView.OnScrollListener {
    private LinearLayoutManager myManager;
    private int totalItem;
    private int visibleItem;
    private int lastItem;
    public RecyclerViewScroll (LinearLayoutManager manager){
        myManager = manager;

    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItem = myManager.getItemCount();
        visibleItem = myManager.getChildCount();
        lastItem = myManager.findLastCompletelyVisibleItemPosition();
        if(visibleItem>0 && dy>0 && lastItem>=totalItem-1){
              onLoadMore();
        }
    }
    public abstract void onLoadMore();
}
