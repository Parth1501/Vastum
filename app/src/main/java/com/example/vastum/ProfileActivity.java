package com.example.vastum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private Button logoutButton;
    private BottomNavigationView bottomNavigationView;
    private TextView userName;
    private RelativeLayout relativeLayout;
    int getIntentKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getIntentKey = getIntent().getIntExtra("Flag",10);

        logoutButton = findViewById(R.id.ProfileLogoutButton);
        bottomNavigationView = findViewById(R.id.profile_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        userName = findViewById(R.id.UserName);
        relativeLayout= findViewById(R.id.profileRelative);

        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                //startActivity(new Intent(ProfileActivity.this, LoginAcitivity.class));
                //finish();
                startActivity(new Intent(ProfileActivity.this,Main2Activity.class));
                finish();
            }
        });

        androidx.appcompat.widget.Toolbar  toolbar = findViewById(R.id.toolbar);
        TextView location = findViewById(R.id.Location);
        TextView vastum = findViewById(R.id.vastum);
        toolbar.setTitle("");
        location.setText("");
        location.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        setSupportActionBar(toolbar);

        bottomnavigation();

        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(ProfileActivity.this){
            public void onSwipeTop() {
//                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                startActivity((new Intent(ProfileActivity.this, RedeemActivity.class)).putExtra("Flag",3));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
//                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void bottomnavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity((new Intent(ProfileActivity.this,MainActivity.class)).putExtra("Flag",3));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;
                    case R.id.navigation_sell:
                        startActivity((new Intent(ProfileActivity.this, HomeActivity.class)).putExtra("Flag",3));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;
                    case R.id.navigation_redeem:
                        startActivity((new Intent(ProfileActivity.this,RedeemActivity.class)).putExtra("Flag",3));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    case R.id.navigation_profile:
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

//        if(getIntentKey==1){
//            bottomNavigationView.setSelectedItemId(R.id.navigation_sell);
//        }
//        else if(getIntentKey==2){
//            bottomNavigationView.setSelectedItemId(R.id.navigation_redeem);
//        }
//        else if(getIntentKey==0){
//            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//        }

    }
}
