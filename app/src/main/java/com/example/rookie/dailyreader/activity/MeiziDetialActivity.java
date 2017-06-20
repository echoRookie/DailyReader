package com.example.rookie.dailyreader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.db.CollectionMeiziDb;
import com.example.rookie.dailyreader.db.CollectionNewsDb;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class MeiziDetialActivity extends AppCompatActivity {
    private PhotoView photoView;
    private TextView collectionText;
    private Boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_layout_item_detail);
        final String imageUrl = getIntent().getStringExtra("imageUrl");
        List<CollectionMeiziDb> myLists = DataSupport.where("iamgeUrl = ?",imageUrl).find(CollectionMeiziDb.class);
        photoView = (PhotoView) findViewById(R.id.item_detial);
        collectionText = (TextView) findViewById(R.id.meizi_collection);
        Glide.with(this).load(imageUrl).into(photoView);
        if(myLists.size()>0){
            collectionText.setText(R.string.meizi_detail_text_select);
            flag = true;
        }
        else {
            collectionText.setText(R.string.meizi_detail_text);
            flag = false;
        }
        collectionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false){
                    collectionText.setText(R.string.meizi_detail_text_select);
                    flag = true;
                    CollectionMeiziDb db = new CollectionMeiziDb();
                    db.setSaveDate(new Date());
                    db.setIamgeUrl(imageUrl);
                    db.save();
                    Toast.makeText(MeiziDetialActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                }
                else {
                    collectionText.setText(R.string.meizi_detail_text);
                    flag = false;
                    DataSupport.deleteAll(CollectionMeiziDb.class,"iamgeUrl = ?",imageUrl);
                    Toast.makeText(MeiziDetialActivity.this,"收藏已取消",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
