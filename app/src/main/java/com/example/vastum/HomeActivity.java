package com.example.vastum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements sellPointsDialog.sellPointsListener {


    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ArrayList<TV_TVPart_demo> tvPartList;
    private TVitemAdapter tvitemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;
    private int getIntentKey;
    private CardView buttonCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getIntentKey = getIntent().getIntExtra("Flag",0);

        imgCapture = findViewById(R.id.imageView);
        buttonCard = findViewById(R.id.buttonCard);
        bottomnavigation();


        imgCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
            }
        });
        buttonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPoints();
            }
        });
//        camera();

    }

    private void showPoints(){
        sellPointsDialog sellPointsDialog= new sellPointsDialog();
        sellPointsDialog.setPoints("1000");
        sellPointsDialog.show(getSupportFragmentManager(),"sellPointsDialog");
    }

    private void camera() {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt,Image_Capture_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imgCapture.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void bottomnavigation() {
        bottomNavigationView = findViewById(R.id.home_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_sell);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity((new Intent(HomeActivity.this, MainActivity.class)).putExtra("Flag",1));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;
                    case R.id.navigation_sell:
                        return true;
                    case R.id.navigation_redeem:
                        startActivity((new Intent(HomeActivity.this, RedeemActivity.class)).putExtra("Flag",1));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        return true;
                    case R.id.navigation_profile:
                        startActivity((new Intent(HomeActivity.this, ProfileActivity.class)).putExtra("Flag",1));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void finish() {
        super.finish();

        if(getIntentKey==0){
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
        else{
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//            if(getIntentKey==2){
//                bottomNavigationView.setSelectedItemId(R.id.navigation_redeem);
//            }
//            else if(getIntentKey==3){
//                bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
//            }
        }

    }

    @Override
    public void sellSuccessful() {
        //we will pass product info in future.
    }
}

