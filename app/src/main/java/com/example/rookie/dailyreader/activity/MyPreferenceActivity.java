package com.example.rookie.dailyreader.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.fragment.MyPreferenceFragment;

public class MyPreferenceActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        /*设置toobar标题及默认返回键*/
        toolbar = (Toolbar) findViewById(R.id.preference_toolbar);
        toolbar.setTitle(R.string.nav_setting);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /*Fragment替换当前布局*/
        getFragmentManager().beginTransaction().replace(R.id.preference_main,new MyPreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*系统默认返回键的点击事件*/
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
