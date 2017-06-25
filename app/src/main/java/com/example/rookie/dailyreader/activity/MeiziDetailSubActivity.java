package com.example.rookie.dailyreader.activity;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.adapter.MeiziPagerAdapter;
import com.example.rookie.dailyreader.adapter.ViewPagerAdapter;
import com.example.rookie.dailyreader.fragment.MeiziViewPagerFragment;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import uk.co.senab.photoview.PhotoView;

public class MeiziDetailSubActivity extends AppCompatActivity {
    private ImageView background;
    private ViewPager viewPager;
    private TextView textView;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> myFragments = new ArrayList<>();
    private int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int position = getIntent().getIntExtra("position", 0);
        final ArrayList<String> myList = getIntent().getStringArrayListExtra("list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_detail_substitution);
        background = (ImageView) findViewById(R.id.meizi_detail_background);
        viewPager = (ViewPager) findViewById(R.id.meizi_viewpager);
        textView = (TextView) findViewById(R.id.meizi_position);
        length = myList.size();
        //初始化fragment
        for (int i = 0; i < myList.size(); i++) {
            MeiziViewPagerFragment fragment = MeiziViewPagerFragment.newInstance(myList.get(i));
            myFragments.add(fragment);
        }
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), myFragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);
        Glide.with(getApplicationContext()).load(myList.get(position)).bitmapTransform(new BlurTransformation(getApplicationContext(), 20), new CenterCrop(getApplicationContext())).into(background);
        int nowPosition = (position + 1);
        //初始化位置指示器
        textView.setText("" + nowPosition + "/" + length);
        //初始化背景
        Glide.with(this)
             .load(R.drawable.nav_header_icon)
             .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
             .into(background);
        //viewpager的滑动监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getApplicationContext()).load(myList.get(position)).bitmapTransform(new BlurTransformation(getApplicationContext(), 25), new CenterCrop(getApplicationContext())).into(background);
                        int nowPosition = (position + 1);
                        textView.setText("" + nowPosition + "/" + length);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
