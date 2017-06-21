package com.example.rookie.dailyreader.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rookie.dailyreader.R;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_about);
        toolbar = (Toolbar) findViewById(R.id.about_toolbar);
       /* 设置toobar的标题*/
        toolbar.setTitle(R.string.about_title);
        setSupportActionBar(toolbar);
        /*显示toolbar自带返回键设置标题*/
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* 处理返回键的点击事件*/
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
