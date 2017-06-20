package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.CollectionMeiziAdapter;
import com.example.rookie.dailyreader.db.CollectionMeiziDb;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by rookie on 2017/6/19.
 */

public class CollectionMeiziFragment extends Fragment{
    private RecyclerView recyclerView;
    private CollectionMeiziAdapter myAdapter;
    private List<CollectionMeiziDb> myLists;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collction_recyclerview,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.collection_recycler);
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        myLists = DataSupport.findAll(CollectionMeiziDb.class);
        myAdapter = new CollectionMeiziAdapter(myLists,getContext());
        recyclerView.setAdapter(myAdapter);
        return view;
    }
}
