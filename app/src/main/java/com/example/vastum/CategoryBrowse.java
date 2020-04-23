package com.example.vastum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryBrowse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryBrowse extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TVitemAdapter adapter;
    private DatabaseReference dbProd;
    private ArrayList<ProductsInfo> tvPartList;


    public CategoryBrowse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryBrowse.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryBrowse newInstance(String param1, String param2) {
        CategoryBrowse fragment = new CategoryBrowse();
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
        View view=inflater.inflate(R.layout.fragment_category_browse, container, false);
        Bundle b1=this.getArguments();
        recyclerView = view.findViewById(R.id.categoryBrowseRecycler);
        dbProd = FirebaseDatabase.getInstance().getReference().child("productsForSell");
        tvPartList = new ArrayList<>();

        CreateList(b1.getString("title"));
        BuildRecyclerView();


        return view;
    }

    private void CreateList(final String title){
            dbProd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(ds.child("productCategory").getValue().toString().equals(title)){
                            tvPartList.add(ds.getValue(ProductsInfo.class));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    private void BuildRecyclerView(){

        recyclerView.setHasFixedSize(true);
        adapter = new TVitemAdapter(tvPartList,this.getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        recyclerView.setAdapter(adapter);
    }
}
