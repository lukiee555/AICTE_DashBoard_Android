package com.example.lokeshsoni.dashboard.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.adapter.AllRatingAdapter;
import com.example.lokeshsoni.dashboard.modal.Reviews;

import java.util.ArrayList;
import java.util.List;

public class AllRatingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rating);

        recyclerView = findViewById(R.id.recyclerViewReviews);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        List<Reviews> reviewsList = (ArrayList<Reviews>) args.getSerializable("ARRAYLIST");

        AllRatingAdapter allRatingAdapter= new AllRatingAdapter(AllRatingActivity.this,reviewsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(allRatingAdapter);


    }
}
