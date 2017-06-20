package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.CollectionDuanziAdapter;
import com.example.rookie.dailyreader.db.CollectionDuanziDb;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by rookie on 2017/6/19.
 */

public class CollectionDuanziFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<CollectionDuanziDb> myLists;
    private CollectionDuanziAdapter myAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collction_recyclerview,container,false);
       /* 初始化recyclerview*/
        recyclerView = (RecyclerView) view.findViewById(R.id.collection_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        myLists = DataSupport.findAll(CollectionDuanziDb.class);
        myAdapter = new CollectionDuanziAdapter(myLists,getContext());
        recyclerView.setAdapter(myAdapter);
        return view;
    }
}
