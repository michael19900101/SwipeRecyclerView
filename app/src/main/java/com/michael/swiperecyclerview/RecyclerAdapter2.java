package com.michael.swiperecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter {


    private Context context;
    private LayoutInflater inflater;
    private List<Integer> lists = new ArrayList<>();

    public RecyclerAdapter2(Context context, List<Integer> lists) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.lists = lists;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder2(inflater.inflate(R.layout.item_layout, null, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder2 viewHolder = (MyViewHolder2) holder;
        viewHolder.txt.setText("第" + String.valueOf(lists.get(position)) + "项");

    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        } else {
            return 0;
        }
    }

}
