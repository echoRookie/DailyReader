package com.example.rookie.dailyreader.fragment;

import android.content.Intent;
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
import com.example.rookie.dailyreader.activity.NewsDetialActivity;


/**
 * Created by rookie on 2017/6/2.
 * 静态工厂模式建立新闻模块轮播图fragment
 */

public class ViewPagerItemFragment extends Fragment {
    private static  final String ARG1 = "imageUrl";
    private static  final String ARG2 = "text";
    private ImageView imageView;
    private TextView textView;
    private String imageUrl;
    private String text;
    private String newsId;
    public static ViewPagerItemFragment newInstance(String imageUrl,String text,String id){
        //存储文章信息并实例化fragment
        ViewPagerItemFragment viewPagerItemFragment = new ViewPagerItemFragment();
        Bundle bundle = new Bundle();
        //文章图片
        bundle.putString(ARG1,imageUrl);
        //文章标题
        bundle.putString(ARG2,text);
        //文章id
        bundle.putString("newsId",id);
        viewPagerItemFragment.setArguments(bundle);
        return viewPagerItemFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            imageUrl = getArguments().getString(ARG1);
            text = getArguments().getString(ARG2);
            newsId = getArguments().getString("newsId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*加载布局文件*/
        View view = inflater.inflate(R.layout.news_viewpager_item,container,false);
        imageView = (ImageView) view.findViewById(R.id.viewpager_item_imageview);
        textView = (TextView) view.findViewById(R.id.viewpager_item_textview);
        //加载文章图片
        Glide.with(getActivity()).load(imageUrl).into(imageView);
        //图片点击事件，进入文章详情页
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsDetialActivity.class);
                intent.putExtra("newsId",newsId);
                getActivity().startActivity(intent);
            }
        });
        //文章标题
        textView.setText(text);
        return view;
    }
}
