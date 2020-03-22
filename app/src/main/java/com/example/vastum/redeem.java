package com.example.vastum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link redeem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class redeem extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int getIntentKey;
    RecyclerView recycle;
    BottomNavigationView bottomNavigationView;
    List<String> voucher_name;
    List<Integer> points;
    ReedemAdapter mAdapter;
    DatabaseReference ref;
    private RelativeLayout relativeLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public redeem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment redeem.
     */
    // TODO: Rename and change types and number of parameters
    public static redeem newInstance(String param1, String param2) {
        redeem fragment = new redeem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_redeem, container, false);
        recycle = view.findViewById(R.id.redeem_recycle);
        recycle.setLayoutManager(new LinearLayoutManager(this.getContext()));
        relativeLayout = view.findViewById(R.id.redeemRelative);

        voucher_name = new ArrayList<>();

        points = new ArrayList<>();
        setRedemLayout();
        return view;
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
                        AlertDialog.Builder b = new AlertDialog.Builder(redeem.this.getContext());
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

}
