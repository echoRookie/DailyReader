package com.example.rookie.dailyreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;


/**
 * Created by rookie on 2017/6/2.
 */

public class ViewPagerItemFragment extends Fragment {
    private static  final String ARG1 = "imageUrl";
    private static  final String ARG2 = "text";
    private ImageView imageView;
    private TextView textView;
    private String imageUrl;
    private String text;
    public static ViewPagerItemFragment newInstance(String imageUrl,String text){
        ViewPagerItemFragment viewPagerItemFragment = new ViewPagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG1,imageUrl);
        bundle.putString(ARG2,text);
        viewPagerItemFragment.setArguments(bundle);
        return viewPagerItemFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            imageUrl = getArguments().getString(ARG1);
            text = getArguments().getString(ARG2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_viewpager_item,container,false);
        imageView = (ImageView) view.findViewById(R.id.viewpager_item_imageview);
        textView = (TextView) view.findViewById(R.id.viewpager_item_textview);
        Glide.with(getActivity()).load(imageUrl).into(imageView);
        textView.setText(text);
        return view;
    }
}