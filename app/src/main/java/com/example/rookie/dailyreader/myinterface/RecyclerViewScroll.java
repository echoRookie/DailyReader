package com.example.rookie.dailyreader.myinterface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by rookie on 2017/6/11.
 * 自定义RecyclerView的滑动监听接口实现向下滑动加载更多
 */

public abstract class RecyclerViewScroll extends RecyclerView.OnScrollListener {
    //获取recyclerview布局管理器
    private LinearLayoutManager myManager;
    //适配器的总子布局数
    private int totalItem;
    //当前可见的子布局位置
    private int visibleItem;
    //当前最后一个子布局的位置
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
        //满足条件调用onLoadMore   法加载
        //当前可见子布局数大于零
        //向下滑动
        //当前可见子布局为适配器中最后一项
        if(visibleItem>0 && dy>0 && lastItem>=totalItem-1){
              onLoadMore();
        }
    }
    //自定义的加载抽象方法
    public abstract void onLoadMore();
}
