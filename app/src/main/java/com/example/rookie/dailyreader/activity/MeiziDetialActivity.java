package com.example.rookie.dailyreader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;


import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class MeiziDetialActivity extends AppCompatActivity {
    private PhotoView photoView;
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_layout_item_detail);
        photoView = (PhotoView) findViewById(R.id.item_detial);
        list = new ArrayList<>();
        int position= getIntent().getIntExtra("position",0);
        list = getIntent().getStringArrayListExtra("mlist");
        Glide.with(this).load(list.get(position)).into(photoView);

    }
}
