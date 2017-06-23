package com.example.rookie.dailyreader.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.example.rookie.dailyreader.MainActivity;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.MyPreferenceActivity;
import com.example.rookie.dailyreader.service.NotificationService;
import com.example.rookie.dailyreader.util.TaskKiller;

/**
 * Created by rookie on 2017/6/14.
 */

public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载设置布局
        addPreferencesFromResource(R.xml.setting_preference);
        //开启消息通知的点击事件
        findPreference("switch_notification").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Boolean result1= (Boolean)newValue ;
                Log.d("fffff", "onPreferenceClick: "+result1);
                //值为true则开启消息服务
                if (result1 == true){
                    Intent intent = new Intent(getActivity(),NotificationService.class);
                    getActivity().startService(intent);
                }
                //值为false则关闭消息服务
                else {
                    Intent intent = new Intent(getActivity(),NotificationService.class);
                    getActivity().stopService(intent);
                }
                return true;
            }
        });
        //        模式切换的点击事件
        findPreference("isNight").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Boolean result1= (Boolean)newValue ;
                Log.d("fffff", "onPreferenceClick: "+result1);
                //值为true则切换为夜间模式
                if (result1){
                    ((AppCompatActivity) getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Intent intent = new Intent(getActivity(),MyPreferenceActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.alpha_start, R.anim.alpha_start);
                    getActivity().finish();
                    for (int i= 0;i<TaskKiller.activityList.size();i++){
                        if(TaskKiller.activityList.get(i) instanceof  MainActivity){
                            TaskKiller.activityList.get(i).recreate();
                        }
                    }

                }
                //值为false则切换为日间模式
                else {
                    ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    for (int i= 0;i<TaskKiller.activityList.size();i++){
                        if(TaskKiller.activityList.get(i) instanceof  MainActivity){
                            TaskKiller.activityList.get(i).recreate();
                        }
                    }

                }
                return true;
            }
        });
    }
}
