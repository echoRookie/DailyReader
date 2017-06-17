package com.example.rookie.dailyreader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;


import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class MeiziDetialActivity extends AppCompatActivity {
    private PhotoView photoView;
    private ArrayList<String> list = new ArrayList<>();
    private TextView collectionText;
    private Boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_layout_item_detail);
        photoView = (PhotoView) findViewById(R.id.item_detial);
        collectionText = (TextView) findViewById(R.id.meizi_collection);
        collectionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false){
                    collectionText.setText(R.string.meizi_detail_text_select);
                    flag = true;
                    Toast.makeText(MeiziDetialActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                }
                else {
                    collectionText.setText(R.string.meizi_detail_text);
                    flag = false;
                    Toast.makeText(MeiziDetialActivity.this,"收藏已取消",Toast.LENGTH_SHORT).show();
                }
            }
        });
        list = new ArrayList<>();
        int position= getIntent().getIntExtra("position",0);
        list = getIntent().getStringArrayListExtra("mlist");
        Glide.with(this).load(list.get(position)).into(photoView);

    }
}
