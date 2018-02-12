package com.michael.swiperecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyViewHolder2 extends RecyclerView.ViewHolder {


    public TextView txt;
    public MyViewHolder2(View itemView) {
        super(itemView);
        txt= (TextView) itemView.findViewById(R.id.item_recycler_txt);
    }
}
