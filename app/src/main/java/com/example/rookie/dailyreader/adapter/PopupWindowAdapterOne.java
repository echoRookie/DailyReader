package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.bean.NewspaperInfo;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/24.
 */

public class PopupWindowAdapterOne extends RecyclerView.Adapter <PopupWindowAdapterOne.MyViewHolder>{
    private ArrayList<NewspaperInfo> myLists;
    private Context myContext;
    private PopupWindowAdapter popupWindowAdapter;
    private NewspaperApater myNewspaperApater;
    public PopupWindowAdapterOne(ArrayList<NewspaperInfo> lists, Context context,PopupWindowAdapter adapter,NewspaperApater newspaperApater){
        myLists = lists;
        myContext = context;
        popupWindowAdapter = adapter;
        myNewspaperApater = newspaperApater;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paper_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public ArrayList<NewspaperInfo> getMyLists() {
        return myLists;
    }

    public void setMyLists(ArrayList<NewspaperInfo> myLists) {
        this.myLists = myLists;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final int positionOne = position;
        final NewspaperInfo info = myLists.get(position);
        holder.textView.setText(info.getPaperName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNewspaperApater.getMyLists().add(info);
                myNewspaperApater.notifyDataSetChanged();
                popupWindowAdapter.getMyLists().add(info);
                popupWindowAdapter.notifyDataSetChanged();
                myLists.remove(positionOne);
                notifyItemRemoved(positionOne);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.paper_item_text);
        }
    }
}
