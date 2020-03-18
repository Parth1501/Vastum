package com.example.vastum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements sellPointsDialog.sellPointsListener {


    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ArrayList<ProductsInfo> tvPartList;
    private TVitemAdapter tvitemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;
    private int getIntentKey;
    private CardView buttonCard;
    private Spinner category,brand,type,age;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getIntentKey = getIntent().getIntExtra("Flag",0);

        imgCapture = findViewById(R.id.imageView);
        buttonCard = findViewById(R.id.buttonCard);
        category = findViewById(R.id.itemCategoryDropDown);
        brand = findViewById(R.id.itemBrandDropDown);
        type = findViewById(R.id.itemTypeDropDown);
        age=findViewById(R.id.itemAgeDropDown);

        mDatabase = FirebaseDatabase.getInstance().getReference("/Category");
        spinnerfillup();

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

    private void spinnerfillup() {

        Query databsase1 = FirebaseDatabase.getInstance().getReference("Category/Refrigerator/Old");
        databsase1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                // System.out.println(dataSnapshot.child("Television").getChildrenCount());
                                                final List<String> items = new ArrayList<String>();

                                                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                                    String itemName = itemSnapshot.getKey();
                                                    items.add(itemName);
                                                }
                                                java.util.Collections.sort(items);
                                                final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item, items);
                                                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                age.setAdapter(itemAdapter);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
            });


                Query databsase = FirebaseDatabase.getInstance().getReference("Category");
        databsase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // System.out.println(dataSnapshot.child("Television").getChildrenCount());
                final List<String> items = new ArrayList<String>();

                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    items.add(itemName);
                }
                java.util.Collections.sort(items);
                final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item, items);
                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(itemAdapter);

                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selected = items.get(i);

                    mDatabase.child(selected).child("Brand").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            final List<String> items = new ArrayList<String>();

                            for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                                String itemName = itemSnapshot.getKey();
                                items.add(itemName);
                            }
                            java.util.Collections.sort(items);
                            ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item, items);
                            itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            brand.setAdapter(itemAdapter);

                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                        mDatabase.child(selected).child("Type").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                final List<String> items = new ArrayList<String>();

                                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                                    String itemName = itemSnapshot.getKey();
                                    System.out.println(itemName);
                                    items.add(itemName);
                                }
                                java.util.Collections.sort(items);
                                ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item, items);
                                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                type.setAdapter(itemAdapter);

                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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

