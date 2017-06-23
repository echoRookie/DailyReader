package com.example.rookie.dailyreader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.MeiziDetialActivity;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by rookie on 2017/6/23.
 */

public class MeiziViewPagerFragment extends Fragment {
    private ImageView photoView ;
    private static  final String ARG1 = "imageUrl";
    private String imageUrl;
    private ImageView back;

    public static MeiziViewPagerFragment newInstance(String imageUrl){
        //存储文章信息并实例化fragment
        MeiziViewPagerFragment meiziViewPagerFragment = new MeiziViewPagerFragment();
        Bundle bundle = new Bundle();
        //文章图片
        bundle.putString(ARG1,imageUrl);
        meiziViewPagerFragment.setArguments(bundle);
        return meiziViewPagerFragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meizi_detail_sunstitution_item,container,false);
        photoView = (ImageView) view.findViewById(R.id.meizi_detail_image);
         imageUrl= getArguments().getString(ARG1);
        Glide.with(getActivity()).load(imageUrl).into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MeiziDetialActivity.class);
                intent.putExtra("imageUrl",imageUrl);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
