package com.example.rookie.dailyreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.BoringLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rookie.dailyreader.activity.AboutActivity;
import com.example.rookie.dailyreader.activity.MyCollectionActivity;
import com.example.rookie.dailyreader.activity.MyPreferenceActivity;
import com.example.rookie.dailyreader.db.CollectionMeiziDb;
import com.example.rookie.dailyreader.fragment.DuanziFragment;
import com.example.rookie.dailyreader.fragment.MeiziFragment;
import com.example.rookie.dailyreader.fragment.NewsFragment;
import com.example.rookie.dailyreader.gson.DuanziGson;
import com.example.rookie.dailyreader.gson.NewsRecyclerGson;
import com.example.rookie.dailyreader.gson.NewsViewPagerGson;
import com.example.rookie.dailyreader.service.NotificationService;
import com.example.rookie.dailyreader.util.DateUtil;
import com.example.rookie.dailyreader.bean.DuanziData;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String TabTextList[] = new String[]{"妹子", "段子", "趣闻"};
    private Integer TabImage[] = new Integer[]{R.drawable.tab_meizi, R.drawable.tab_news, R.drawable.tab_joker};
    private Integer TabImageSelect[] = new Integer[]{R.drawable.tab_meizi_select, R.drawable.tab_news_select, R.drawable.tab_joker_select};
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ArrayList<DuanziData> DuanziList = new ArrayList<>();
    private String DuanziData;
    private String newsData;
    private MeiziFragment meiziFragment;
    private DuanziFragment duanziFragment;
    private NewsFragment newsFragment;
    private ArrayList<String> viewPagerImageUrl;
    private ArrayList<String> viewPagerText;
    private ArrayList<String> viewPagerId;
    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*检查默认值确定是否开启通知*/
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        Boolean notification = preferences.getBoolean("switch_notification",false);
        Log.d("yyyyyyyyyy", "onCreate: "+notification);
        if (notification == true){
            Intent intent = new Intent(this, NotificationService.class);
            startService(intent);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.MyDrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.MyNavView);
        /*初始化toolbar并设置标题和图标*/
        toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("DailyReader");
        toolbar.setNavigationIcon(R.drawable.home_menu);
        /*navigationView的点击事件*/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId()==R.id.nav_setting){
                   drawerLayout.closeDrawers();
                   Intent intent = new Intent(MainActivity.this, MyPreferenceActivity.class);
                   startActivity(intent);
               }
                if(item.getItemId()==R.id.nav_collection){
                    drawerLayout.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, MyCollectionActivity.class);
                    startActivity(intent);
               }
                if(item.getItemId()==R.id.nav_about) {
                    drawerLayout.closeDrawers();
                   Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                   startActivity(intent);

               }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        /*请求知乎日报的最新消息*/
        HttpUtil.sendOkHttpRequest("http://news-at.zhihu.com/api/4/news/latest", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                newsData = response.body().string();
                Log.d("bbbb", "onResponse: " + newsData);
                NewsRecyclerGson newsRecyclerGson = new Gson().fromJson(newsData, NewsRecyclerGson.class);
                Log.d("bbbbbbbbbbbbb", "onResponse: " + newsRecyclerGson.Storys.get(0).images.get(0));
                Log.d("bbbbbbbbbbbbb", "onResponse: " + newsRecyclerGson.Storys.get(0).title);
                Log.d("bbbbbbbbbbbbb", "onResponse: " + newsRecyclerGson.Storys.get(0).id);
                NewsViewPagerGson newsGson = new Gson().fromJson(newsData, NewsViewPagerGson.class);
                Log.d("bbbbb", "onResponse: " + newsGson.myStorys.size());
                Log.d("bbbbbb", "onResponse: " + newsGson.myStorys.get(4).image);
                Log.d("bbbbbbb", "onResponse: " + newsGson.myStorys.get(4).id);
                Log.d("bbbbbbbb", "onResponse: " + newsGson.myStorys.get(4).title);
                viewPagerImageUrl = new ArrayList<String>();
                viewPagerText = new ArrayList<String>();
                viewPagerId = new ArrayList<String>();
                for (int i = 0; i < newsGson.myStorys.size(); i++) {
                    viewPagerImageUrl.add(newsGson.myStorys.get(i).image);
                    viewPagerText.add(newsGson.myStorys.get(i).title);
                    viewPagerId.add(newsGson.myStorys.get(i).id);
                }

            }
        });
        /*请求段子的最新消息，后续会封装*/
        HttpUtil.sendOkHttpRequest("http://is.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-102&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1489226058493&count=30&min_time=1489205901&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String datainfo = response.body().string();
                DuanziData = datainfo;
                Log.d("x", "onResponse: " + DuanziData);
                Log.d("z", "onResponse: " + datainfo);
                try {
                    JSONObject jsonObject = new JSONObject(datainfo);
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                    DuanziGson duanziGson = new Gson().fromJson(jsonObject1.toString(), DuanziGson.class);

                    int length = duanziGson.dataInfos.size();
                    Log.d("zzzz", "onResponse: " + length);
                    int i = 0;
                    while (i < length) {


                        if (null == duanziGson.dataInfos.get(i).groupData) {
                            i++;

                        } else {
                            DuanziData duanziData = new DuanziData();
                            duanziData.setDuanzitext(duanziGson.dataInfos.get(i).groupData.text);
                            duanziData.setUsername(duanziGson.dataInfos.get(i).groupData.user.username);
                            duanziData.setUsericon(duanziGson.dataInfos.get(i).groupData.user.icon);
                            DuanziList.add(duanziData);
                            Log.d("zzzzzzz", "onResponse: " + i + duanziGson.dataInfos.get(i).groupData.text);
                            i++;
                        }

                    }
                    Log.d("zzzzzzzzzzzzz", "onResponse: " + DuanziList.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        fm = getSupportFragmentManager();
        /*初始化tablayout并设置点击事件的监听*/
        tabLayout = (TabLayout) findViewById(R.id.MainTab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentTransaction = fm.beginTransaction();
                if (meiziFragment != null) {
                    fragmentTransaction.hide(meiziFragment);
                }
                if (duanziFragment != null) {
                    fragmentTransaction.hide(duanziFragment);
                }
                if (newsFragment != null) {
                    fragmentTransaction.hide(newsFragment);
                }
                if (tab.getPosition() == 0) {
                    if (meiziFragment == null) {
                        meiziFragment = new MeiziFragment();
                        fragmentTransaction.add(R.id.MainContent, meiziFragment);
                    }
                    fragmentTransaction.show(meiziFragment);
                    Log.d("select", "aaaaa");
                } else if (tab.getPosition() == 1) {
                    if (duanziFragment == null) {
                        duanziFragment = new DuanziFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("duanzidata", DuanziData);
                        duanziFragment.setArguments(bundle);
                        fragmentTransaction.add(R.id.MainContent, duanziFragment);
                    }
                    fragmentTransaction.show(duanziFragment);

                } else if (tab.getPosition() == 2) {
                    if (newsFragment == null) {
                        newsFragment = new NewsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("imageUrl", viewPagerImageUrl);
                        bundle.putStringArrayList("title", viewPagerText);
                        bundle.putStringArrayList("id", viewPagerId);
                        bundle.putString("recyclerData", newsData);
                        newsFragment.setArguments(bundle);
                        fragmentTransaction.add(R.id.MainContent, newsFragment);
                    }
                    fragmentTransaction.show(newsFragment);

                }
                fragmentTransaction.commit();
                /*点击时颜色的变化*/
                for (int i = 0; i < tabLayout.getTabCount(); i++) {

                    View view = tabLayout.getTabAt(i).getCustomView();
                    ImageView imageView = (ImageView) view.findViewById(R.id.TabImage);
                    TextView textView = (TextView) view.findViewById(R.id.TabText);
                    if (tab.getPosition() == i) {
                        imageView.setImageResource(TabImageSelect[i]);
                        textView.setTextColor(getResources().getColor(R.color.tab_text_select));
                    } else {
                        imageView.setImageResource(TabImage[i]);
                        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /*添加tab的子选项*/
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                tabLayout.addTab(tabLayout.newTab().setCustomView(getView(this, i)), true);
            } else {
                tabLayout.addTab(tabLayout.newTab().setCustomView(getView(this, i)));
            }
        }


    }

    /*初始化tab子选项的布局文件*/
    public View getView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.TabImage);
        TextView textView = (TextView) view.findViewById(R.id.TabText);
        imageView.setImageResource(TabImage[position]);
        textView.setText(TabTextList[position]);
        return view;
    }

    /*back键点击的监听实现再按一次退出程序*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
