package com.example.rookie.dailyreader;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rookie.dailyreader.Fragment.DuanziFragment;
import com.example.rookie.dailyreader.Fragment.MeiziFragment;
import com.example.rookie.dailyreader.gson.DuanziGson;
import com.example.rookie.dailyreader.gson.MeiziGson;
import com.example.rookie.dailyreader.util.DuanziData;
import com.example.rookie.dailyreader.util.DuanziInfo;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import uk.co.senab.photoview.log.LoggerDefault;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String url="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
    private TabLayout tabLayout;
    private List<Fragment> mfragment;
    private String TabTextList[] = new String[]{"妹子","段子","趣闻"};
    private Integer TabImage[] = new Integer[]{R.drawable.tab_meizi,R.drawable.tab_news,R.drawable.tab_joker};
    private Integer TabImageSelect[] = new Integer[]{R.drawable.tab_meizi_select,R.drawable.tab_news_select,R.drawable.tab_joker_select};
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ArrayList<DuanziData> DuanziList = new ArrayList<>();
    private String DuanziData;
    private MeiziFragment meiziFragment;
    private DuanziFragment duanziFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("DailyReader");
        HttpUtil.sendOkHttpRequest("http://is.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-102&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1489226058493&count=30&min_time=1489205901&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String  datainfo = response.body().string();
                    DuanziData = datainfo;
                    Log.d("x", "onResponse: "+DuanziData);
                    Log.d("z", "onResponse: "+datainfo);
                    try {
                        JSONObject jsonObject = new JSONObject(datainfo);
                        JSONObject jsonObject1= new JSONObject(jsonObject.getString("data"));
                        DuanziGson duanziGson = new Gson().fromJson(jsonObject1.toString(),DuanziGson.class);

                        int length = duanziGson.dataInfos.size();
                        Log.d("zzzz", "onResponse: "+length);
                        int i=0;
                        while(i<length){


                            if(null==duanziGson.dataInfos.get(i).groupData){
                                i++;

                            }
                            else {
                                DuanziData duanziData = new DuanziData();
                                duanziData.setDuanzitext(duanziGson.dataInfos.get(i).groupData.text);
                                duanziData.setUsername(duanziGson.dataInfos.get(i).groupData.user.username);
                                duanziData.setUsericon(duanziGson.dataInfos.get(i).groupData.user.icon);
                                DuanziList.add(duanziData);
                                Log.d("zzzzzzz", "onResponse: "+i+duanziGson.dataInfos.get(i).groupData.text);
                                i++;
                            }

                        }
                        Log.d("zzzzzzzzzzzzz", "onResponse: "+DuanziList.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

        HttpUtil.sendOkHttpRequest(HttpUtil.getUrl(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MeiziGson meiziGson =HttpUtil.handleMeiziResponse(response.body().string());
                imageUrl= new ArrayList<String>();
                for (int i=0;i<meiziGson.MeiziList.size();i++){
                    imageUrl.add(meiziGson.MeiziList.get(i).url);
                }
                Log.d("mm",meiziGson.MeiziList.get(0).url+"长度为"+imageUrl.size());
                Log.d("mmm","长度为"+imageUrl.size());
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.MainTab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    meiziFragment = new MeiziFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("imageurl",imageUrl);
                    meiziFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.MainContent,meiziFragment).commit();
                    Log.d("select","aaaaa");
                }
                if (tab.getPosition()==1){
                    duanziFragment = new DuanziFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("duanzidata",DuanziData);
                    duanziFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.MainContent,duanziFragment).commit();
                    Log.d("select","aaaaa");
                }
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

        for (int i = 0; i < 3; i++) {
            if(i==0){
                tabLayout.addTab(tabLayout.newTab().setCustomView(getView(this,i)),true);
            }
            else {
                tabLayout.addTab(tabLayout.newTab().setCustomView(getView(this, i)));}
        }

    }
    public View getView(Context context,int position){
        View view = LayoutInflater.from(context).inflate(R.layout.main_tab,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.TabImage);
        TextView textView=(TextView)view.findViewById(R.id.TabText);
        imageView.setImageResource(TabImage[position]);
        textView.setText(TabTextList[position]);
        return view;
    }
}
