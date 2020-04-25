package com.example.vastum;

import android.content.Context;
import android.media.tv.TvContentRating;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView my_recycler_view;
    private ArrayList<ProductsInfo> tvPartList;
    ArrayList<ProductsSectionsModel> allSampleData;
    private DatabaseReference dbCategories, dbProd;
    private StorageReference stRef;
    private ProductsSectionAdapter adapter;
    View view;
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String Categories="";
    public home() {
        // Required empty public constructor
        context=this.getContext();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
        view=inflater.inflate(R.layout.fragment_home, container, false);
        my_recycler_view=view.findViewById(R.id.RecyclerItems);
        dbCategories = FirebaseDatabase.getInstance().getReference().child("Category");
        dbProd = FirebaseDatabase.getInstance().getReference().child("productsForSell");
        stRef = FirebaseStorage.getInstance().getReference();
        allSampleData = new ArrayList<ProductsSectionsModel>();
        CreateList();
        BuildRecyclerView();
        return view;
    }

    private void CreateList() {
        dbCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Categories+=","+ds.getKey();
                    Log.e("THE KEYS",ds.getKey());
                }
                CreateSeparateLists();
                Log.e("HE EE ",Categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.e("THE CATEGORIES",Categories+"bb");
    }

    private void  CreateSeparateLists(){
        final String[] DiffCategory = Categories.split(",");
        dbProd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (String Category : DiffCategory) {
                    if(!Category.equals("") ){
                        int count =5;
                        ProductsSectionsModel model = new ProductsSectionsModel();
                        model.setHeaderTitle(Category);
                        tvPartList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            if(ds.child("productCategory").getValue(String.class).equals(Category) && count !=0){
                                tvPartList.add(ds.getValue(ProductsInfo.class));
                                count--;
                            }
                        }
                        model.setAllItemsInSection(tvPartList);
                        allSampleData.add(model);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void BuildRecyclerView() {

        my_recycler_view.setHasFixedSize(true);
        adapter = new ProductsSectionAdapter( allSampleData,this.getContext());
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        my_recycler_view.setAdapter(adapter);


    }
}
