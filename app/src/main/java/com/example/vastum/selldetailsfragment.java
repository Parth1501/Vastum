package com.example.vastum;

import android.os.Bundle;
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

public class selldetailsfragment extends Fragment {
    View view;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser userid = mAuth.getCurrentUser();
    String user = userid.getUid();
    RecyclerView recyclerView;
    String items[]={"item1","item2","item3","item4"};
    selldetailsfragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selldetailsfragment,container,false);
        final String sell="";
        DatabaseReference mdbUser = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        mdbUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                           if (ds.child("userID").getValue().toString().equals(mAuth.getCurrentUser().getUid())) {
                                                              sell=ds.child("userSoldProduct").getValue(String.class);
                                                           }
                                                       }
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                   }

                                               };
            recyclerView=view.findViewById(R.id.itemrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new ItemRecyclerAdapter(this.getContext(),items));
        return view;
    }
}


