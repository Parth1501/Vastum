package com.example.vastum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ArrayList<TV_TVPart_demo> tvPartList;
    private TVitemAdapter tvitemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CreateList();
        BuildRecyclerView();

        bottomNavigationView = findViewById(R.id.home_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_sell);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(HomeActivity.this,MainActivity.class));
                        return true;
                    case R.id.navigation_sell:
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        return true;
                }
                return false;
            }
        });
    }

    private void CreateList(){
        tvPartList = new ArrayList<>();
        tvPartList.add(new TV_TVPart_demo("WHERE IT IS",R.drawable.logo));
        tvPartList.add(new TV_TVPart_demo("HERE IT IS",R.drawable.monitor));
        tvPartList.add(new TV_TVPart_demo("WHERE IT IS",R.drawable.logo));
        tvPartList.add(new TV_TVPart_demo("HERE IT IS",R.drawable.monitor));
        tvPartList.add(new TV_TVPart_demo("WHERE IT IS",R.drawable.logo));
        tvPartList.add(new TV_TVPart_demo("HERE IT IS",R.drawable.monitor));
    }

    private void BuildRecyclerView(){
        recyclerView = findViewById(R.id.RecyclerTV);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        tvitemAdapter = new TVitemAdapter(tvPartList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tvitemAdapter);
        tvitemAdapter.setOnItemClickListener(new TVitemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }
}
