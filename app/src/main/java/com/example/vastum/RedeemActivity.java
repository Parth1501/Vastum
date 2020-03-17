package com.example.vastum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RedeemActivity extends AppCompatActivity {
    int getIntentKey;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        getIntentKey = getIntent().getIntExtra("Flag",0);
        bottomnavigation();
    }

    private void bottomnavigation() {
        bottomNavigationView = findViewById(R.id.redeem_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_redeem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity((new Intent(RedeemActivity.this, MainActivity.class)).putExtra("Flag",2));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;
                    case R.id.navigation_sell:
                        startActivity((new Intent(RedeemActivity.this, HomeActivity.class)).putExtra("Flag",2));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;
                    case R.id.navigation_redeem:
                        return true;
                    case R.id.navigation_profile:
                        startActivity((new Intent(RedeemActivity.this, ProfileActivity.class)).putExtra("Flag",2));
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

        if(getIntent().getIntExtra("Flag",0)==3){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//            bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        }
        else{
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//            if(getIntentKey == 1){
//                bottomNavigationView.setSelectedItemId(R.id.navigation_sell);
//            }
//            else if(getIntentKey == 0){
//                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//            }
        }
    }
}
