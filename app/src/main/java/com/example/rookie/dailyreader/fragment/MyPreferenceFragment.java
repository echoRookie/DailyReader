package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.rookie.dailyreader.R;

/**
 * Created by rookie on 2017/6/14.
 */

public class MyPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
    }
}
