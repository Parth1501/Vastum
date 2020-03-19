package com.example.vastum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RedeemActivity extends AppCompatActivity {
    int getIntentKey;
    RecyclerView recycle;
    BottomNavigationView bottomNavigationView;
    List<String> voucher_name;
    List<Integer> points;
    ReedemAdapter mAdapter;
    DatabaseReference ref;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        recycle = findViewById(R.id.redeem_recycle);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        relativeLayout = findViewById(R.id.redeemRelative);

        voucher_name = new ArrayList<>();

        points = new ArrayList<>();

        getIntentKey = getIntent().getIntExtra("Flag", 10);
        bottomnavigation();
        setRedemLayout();


        recycle.setOnTouchListener(new OnSwipeTouchListener(RedeemActivity.this){
            public void onSwipeTop() {
//                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                startActivity((new Intent(RedeemActivity.this, HomeActivity.class)).putExtra("Flag",1));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                startActivity((new Intent(RedeemActivity.this, ProfileActivity.class)).putExtra("Flag",0));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
//                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }






    private void setRedemLayout()  {
        ref = FirebaseDatabase.getInstance().getReference().child("Redeem");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    points.add(Integer.parseInt(ds.child("point").getValue().toString()));
                    voucher_name.add(ds.child("name").getValue().toString());


                }
                mAdapter = new ReedemAdapter(voucher_name, points);
                recycle.setAdapter(mAdapter);
                mAdapter.setOnRedeemClickListener(new ReedemAdapter.OnRedeemClick() {
                    @Override
                    public void reedem(int i) {
                        AlertDialog.Builder b = new AlertDialog.Builder(RedeemActivity.this);
                        b.setMessage("After confirmation "+points.get(i)+" points will deduct from your account");
                        b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                                //ENTER YOUR DATABASE RELATED CODE HERE


                            }
                        });
                        b.setNegativeButton("Cancel", null);
                        b.show();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.navigation_redeem);
    }

    private void bottomnavigation() {
        bottomNavigationView = findViewById(R.id.redeem_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_redeem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity((new Intent(RedeemActivity.this, MainActivity.class)).putExtra("Flag", 2));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                    case R.id.navigation_sell:
                        startActivity((new Intent(RedeemActivity.this, HomeActivity.class)).putExtra("Flag", 2));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                    case R.id.navigation_redeem:
                        return true;
                    case R.id.navigation_profile:
                        startActivity((new Intent(RedeemActivity.this, ProfileActivity.class)).putExtra("Flag", 2));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getIntent().getIntExtra("Flag", 0) == 3) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        } else {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//            if(getIntentKey == 1){
//                bottomNavigationView.setSelectedItemId(R.id.navigation_sell);
//            }
//            else if(getIntentKey == 0){
//                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//            }
        }
    }


}

