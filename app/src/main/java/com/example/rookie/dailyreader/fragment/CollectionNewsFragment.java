package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.CollectionNewAdapter;
import com.example.rookie.dailyreader.db.CollectionNewsDb;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by rookie on 2017/6/16.
 */

public class CollectionNewsFragment extends Fragment{
    private RecyclerView recyclerView;
    private List<CollectionNewsDb> myLists;
    private CollectionNewAdapter myAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collction_recyclerview,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.collection_recycler);

        myLists = DataSupport.findAll(CollectionNewsDb.class);
       /* List<NewsDb> myLists = DataSupport.findAll(NewsDb.class);*/
        Log.d("yyyyyyy", "onCreateView: "+myLists.size());
        for(int i=0;i<myLists.size();i++){
            Log.d("yyyyyyy", "onCreateView: "+myLists.get(i).getTitle());
            Log.d("yyyyyyy", "onCreateView: "+myLists.get(i).getSaveDate());
            Log.d("yyyyyyy", "onCreateView: "+myLists.get(i).getImageUrl());
        }


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        myAdapter = new CollectionNewAdapter(myLists,getContext());
        recyclerView.setAdapter(myAdapter);
        return view;
    }
}
