package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rookie.dailyreader.adapter.MeiziAdapter;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.gson.MeiziGson;
import com.example.rookie.dailyreader.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;

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
    private ArrayList<String> imageUrl = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         HttpUtil.sendOkHttpRequest(HttpUtil.getUrl(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MeiziGson meiziGson =HttpUtil.handleMeiziResponse(response.body().string());
                for (int i=0;i<meiziGson.MeiziList.size();i++){
                    imageUrl.add(meiziGson.MeiziList.get(i).url);
                }
                Log.d("hhhhh",meiziGson.MeiziList.get(0).url+"长度为"+imageUrl.size());
                Log.d("hhhhhhhh","长度为"+imageUrl.size());
            }
        });
        View  view = inflater.inflate(R.layout.meizi_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.MeiZiRecycer);
        meiziAdapter =new MeiziAdapter(imageUrl,getContext());
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(meiziAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.MeiziSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_material_light_1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMeizi();
               recyclerView.post(new Runnable() {
                   @Override
                   public void run() {
                       swipeRefreshLayout.setRefreshing(false);
                       meiziAdapter.notifyDataSetChanged();
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
    /*刷新事件*/
    public void refreshMeizi(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtil.sendOkHttpRequest(HttpUtil.getUrl(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"刷新失败,请检查网络环境",Toast.LENGTH_SHORT).show();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        MeiziGson meiziGson =HttpUtil.handleMeiziResponse(response.body().string());
                       if(imageUrl!=null){
                          imageUrl.clear();
                       }

                        for (int i=0;i<meiziGson.MeiziList.size();i++){
                            imageUrl.add(meiziGson.MeiziList.get(i).url);
                        }

                    }
                });

            }
        }).start();
    }
}
