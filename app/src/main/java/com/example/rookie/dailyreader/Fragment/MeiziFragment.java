package com.example.rookie.dailyreader.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.dailyreader.Adapter.MeiziAdapter;
import com.example.rookie.dailyreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2017/5/17.
 */

public class MeiziFragment extends Fragment {
    private ArrayList<String> mlist;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mlist= getArguments().getStringArrayList("imageurl");
        View  view = inflater.inflate(R.layout.meizi_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.MeiZiRecycer);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MeiziAdapter meiziAdapter =new MeiziAdapter(mlist,getActivity().getBaseContext());
        recyclerView.setAdapter(meiziAdapter);

    }
}
