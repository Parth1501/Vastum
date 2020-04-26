package com.example.vastum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class orderdetailsfragment extends Fragment {
    private DatabaseReference ref;
    private DatabaseReference mdbUser;
    private FirebaseUser user;
    private ReedemAdapter mAdapter;
    private List<String> voucher_name;
    private List<Integer> points;
    private List<String> already_redeem;
    private List<String> redeem_keys;
    private ProgressDialog loading;
    View view;
    RecyclerView recyclerView;

    orderdetailsfragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loading = ProgressDialog.show(getContext(), "Fetching", "Please Wait");
        view = inflater.inflate(R.layout.orderdetailsfragment,container,false);
        recyclerView = view.findViewById(R.id.odrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        mdbUser = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        user = FirebaseAuth.getInstance().getCurrentUser();
        voucher_name = new ArrayList<>();
        redeem_keys = new ArrayList<>();
        points = new ArrayList<>();
        already_redeem = new ArrayList<>();

        checkAlready();
        return view;
    }
    private void checkAlready() {
        mdbUser = FirebaseDatabase.getInstance().getReference().child("UserInfo");

//        Log.e("Check", user.getUid());

        mdbUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("userID").getValue().toString().equals(user.getUid())) {
                        //setUserProducts(ds.getKey().toString(),  ds.child("userSoldProduct").getValue().toString());
                        try {

                            String redeem_value = ds.child("userRedeem").getValue().toString();
                            String[] spilt_redeem_value = redeem_value.split(",");
                            for (String str : spilt_redeem_value) {
                                already_redeem.add(str);
                                Log.e("Spilt String", str + already_redeem.size());
                            }

                        } catch (Exception e) {
                            Log.e("DatabseError", e.toString());


                            //userRedeem Not Found in database
                        }

                    }

                }
                setOrderLayout();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    private void isAvailable() {
        if (points.size() == 0) {
            TextView t = getView().findViewById(R.id.order_available);
            t.setVisibility(View.VISIBLE);
            loading.cancel();
        }

    }
    private void reverseList() {
        Collections.reverse(points);
        Collections.reverse(voucher_name);
    }

    private void setOrderLayout() {
        //checkAlready()
        ref = FirebaseDatabase.getInstance().getReference().child("Redeem");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (already_redeem.contains(ds.getKey())) {
                        Log.e("INSIDE", already_redeem.size() + "IN");
                        points.add(Integer.parseInt(ds.child("point").getValue().toString()));
                        voucher_name.add(ds.child("name").getValue().toString());
                        redeem_keys.add(ds.getKey());

                    }
                }
                isAvailable();
                reverseList();
                mAdapter = new ReedemAdapter(voucher_name, points,true);
                recyclerView.setAdapter(mAdapter);
                loading.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
