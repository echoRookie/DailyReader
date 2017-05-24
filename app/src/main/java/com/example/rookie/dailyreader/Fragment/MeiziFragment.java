package com.example.rookie.dailyreader.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.dailyreader.Adapter.MeiziAdapter;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.gson.MeiziGson;
import com.example.rookie.dailyreader.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by rookie on 2017/5/17.
 */

public class MeiziFragment extends Fragment {
    private ArrayList<String> mlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MeiziAdapter meiziAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mlist= getArguments().getStringArrayList("imageurl");
        View  view = inflater.inflate(R.layout.meizi_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.MeiZiRecycer);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.MeiziSwipeRefresh);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        meiziAdapter =new MeiziAdapter(mlist,getActivity().getApplicationContext());
        recyclerView.setAdapter(meiziAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_material_light_1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMeizi();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        meiziAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public void refreshMeizi(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtil.sendOkHttpRequest(HttpUtil.getUrl(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        MeiziGson meiziGson =HttpUtil.handleMeiziResponse(response.body().string());
                       if(mlist!=null){
                          mlist.clear();
                       }

                        for (int i=0;i<meiziGson.MeiziList.size();i++){
                            mlist.add(meiziGson.MeiziList.get(i).url);
                        }
                        Log.d("mmmmm",meiziGson.MeiziList.get(0).url+"长度为aaaaaa"+mlist.size());
                        Log.d("mmm","长度为"+mlist.size());
                    }
                });

            }
        }).start();
    }
}
