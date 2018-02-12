package com.michael.swiperecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {


    public TextView txt;
    public LinearLayout layout;
    public MyViewHolder(View itemView) {
        super(itemView);
        txt= (TextView) itemView.findViewById(R.id.item_recycler_txt);
        layout= (LinearLayout) itemView.findViewById(R.id.item_recycler_ll);
    }
}
