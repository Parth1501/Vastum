package com.example.vastum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
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
        bottomnavigation();

        camera();

    }

    private void camera() {

    }

    private void bottomnavigation() {
        bottomNavigationView = findViewById(R.id.home_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_sell);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
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


}

