package com.example.rookie.dailyreader;

import android.content.Context;
import android.support.design.widget.TabLayout;
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

import com.example.rookie.dailyreader.Fragment.MeiziFragment;
import com.example.rookie.dailyreader.gson.MeiziGson;
import com.example.rookie.dailyreader.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String url="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
    private TabLayout tabLayout;
    private String TabTextList[] = new String[]{"妹子","段子","趣闻"};
    private Integer TabImage[] = new Integer[]{R.drawable.tab_meizi,R.drawable.tab_news,R.drawable.tab_joker};
    private Integer TabImageSelect[] = new Integer[]{R.drawable.tab_meizi_select,R.drawable.tab_news_select,R.drawable.tab_joker_select};
    private  ArrayList<String> imageUrl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        toolbar.setTitle("DailyReader");

        HttpUtil.sendOkHttpRequest(url, new Callback() {
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

                    MeiziFragment meiziFragment = new MeiziFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("imageurl",imageUrl);
                    meiziFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.MainContent,meiziFragment).commit();
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
