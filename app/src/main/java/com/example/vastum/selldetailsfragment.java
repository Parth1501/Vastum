package com.example.vastum;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class selldetailsfragment extends Fragment {
    View view;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser userid = mAuth.getCurrentUser();
    String user = userid.getUid();
    RecyclerView recyclerView;
    private TVitemAdapter adapter;
    private ArrayList<ProductsInfo> productsInfos;
    private DatabaseReference mdbProd;
    selldetailsfragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selldetailsfragment,container,false);
        productsInfos = new ArrayList<>();
        adapter = new TVitemAdapter(productsInfos);
        DatabaseReference mdbUser = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        mdbProd = FirebaseDatabase.getInstance().getReference().child("productsForSell");
        mdbUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                           if (ds.child("userID").getValue().toString().equals(mAuth.getCurrentUser().getUid())) {
                                                              SoldProducts(ds.child("userSoldProduct").getValue(String.class));
                                                           }
                                                       }
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                   }

                                               });
        recyclerView=view.findViewById(R.id.itemrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void SoldProducts(String Products)
    {
       final String[]  products = Products.split(",");
        Log.e("THE PRODUCTS",Products+products[0]+",***"+products[1]+"**");
        mdbProd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(String prod : products){
                    if(!prod.equals("")){
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            Log.e("THE INFO",ds.child("productID").getValue().toString());
                            if(ds.child("productID").getValue().toString().equals(prod)){
                                Log.e("THE INFO::",ds.child("productID").getValue().toString());
                                productsInfos.add(ds.getValue(ProductsInfo.class));
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}


