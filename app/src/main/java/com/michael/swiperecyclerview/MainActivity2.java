package com.michael.swiperecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity implements MyRecyclerView.onGetListener {


    private List<Integer> lists = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter2 adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initData();
        initviews();
    }

    private void initviews() {
        recyclerView = findViewById(R.id.my_recycler);
        adapter = new RecyclerAdapter2(getApplicationContext(), lists);
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void initData() {
        for (int i = 1; i < 20; i++) {
            lists.add(i);
        }
    }


    @Override
    public void getPosition(int position) {

        Toast.makeText(getApplicationContext(), "现在是第" + String.valueOf(position + 1) + "项", Toast.LENGTH_SHORT).show();
    }
}
