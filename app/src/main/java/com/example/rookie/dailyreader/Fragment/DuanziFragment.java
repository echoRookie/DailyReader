package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rookie.dailyreader.adapter.DuanziAdapter;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.gson.DuanziGson;
import com.example.rookie.dailyreader.bean.DuanziData;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by rookie on 2017/5/23.
 */

public class DuanziFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DuanziAdapter duanziAdapter;
    private ArrayList<DuanziData> DuanziList = new ArrayList<>();
    private int dataLenght =0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String data = bundle.getString("duanzidata");
        Log.d("uuu", ""+data);
        if(data!=null) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                DuanziGson duanziGson = new Gson().fromJson(jsonObject1.toString(), DuanziGson.class);

                int length = duanziGson.dataInfos.size();
                Log.d("zzzz", "onResponse: " + length);
                int i = 0;
                while (i < length) {


                    if (null == duanziGson.dataInfos.get(i).groupData) {
                        i++;

                    } else {
                        DuanziData duanziData = new DuanziData();
                        duanziData.setDuanzitext(duanziGson.dataInfos.get(i).groupData.text);
                        duanziData.setUsername(duanziGson.dataInfos.get(i).groupData.user.username);
                        duanziData.setUsericon(duanziGson.dataInfos.get(i).groupData.user.icon);
                        DuanziList.add(duanziData);
                        Log.d("zzzzzzz", "onResponse: " + i + duanziGson.dataInfos.get(i).groupData.text);
                        i++;
                    }

                }
                Log.d("zzzzzzzzzzzzz", "onResponse: " + DuanziList.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        View view = inflater.inflate(R.layout.duanzi_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.DuanziRecycer);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.DuanziSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_material_light_1);
        duanziAdapter = new DuanziAdapter(DuanziList,getContext());
        recyclerView.setAdapter(duanziAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                   Refresh();
                if(dataLenght>0) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            Boolean[] flags = new Boolean[DuanziList.size()];
                            for(int i=0;i<DuanziList.size();i++){
                                flags[i] = false;
                            }
                            duanziAdapter.setFlags(flags);
                            duanziAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    dataLenght=0;
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return view;

    }
    public void Refresh(){
            HttpUtil.sendOkHttpRequest("http://is.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-102&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1489226058493&count=30&min_time=1489205901&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"刷新失败，请检查网络环境",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  datainfo = response.body().string();
                if (datainfo!=null) {
                    try {
                        JSONObject jsonObject = new JSONObject(datainfo);
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                        DuanziGson duanziGson = new Gson().fromJson(jsonObject1.toString(), DuanziGson.class);

                        int length = duanziGson.dataInfos.size();
                        dataLenght = length;
                        Log.d("zzzz", "onResponse: " + length);
                        int i = 0;
                        DuanziList.clear();
                        while (i < length) {


                            if (null == duanziGson.dataInfos.get(i).groupData) {
                                i++;

                            } else {
                                DuanziData duanziData = new DuanziData();
                                duanziData.setDuanzitext(duanziGson.dataInfos.get(i).groupData.text);
                                duanziData.setUsername(duanziGson.dataInfos.get(i).groupData.user.username);
                                duanziData.setUsericon(duanziGson.dataInfos.get(i).groupData.user.icon);
                                DuanziList.add(duanziData);
                                Log.d("zzzzzzz", "onResponse: " + i + duanziGson.dataInfos.get(i).groupData.text);
                                i++;
                            }

                        }
                        Log.d("zzzzzzzzzzzzz", "onResponse: " + DuanziList.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
}
