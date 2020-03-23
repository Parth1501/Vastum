package com.example.vastum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        my_recycler_view=view.findViewById(R.id.RecyclerItems);
        allSampleData = new ArrayList<ProductsSectionsModel>();
        CreateList();
        BuildRecyclerView();
        return view;
    }

    private void CreateList() {
        for (int i = 1; i <= 3; i++) {

            ProductsSectionsModel dm = new ProductsSectionsModel();

            dm.setHeaderTitle("Section " + i);

            tvPartList = new ArrayList<>();
//            tvPartList.add(new ProductsInfo("WHERE IT IS", R.drawable.logo));
//            tvPartList.add(new ProductsInfo("HERE IT IS", R.drawable.monitor));
//            tvPartList.add(new ProductsInfo("WHERE IT IS", R.drawable.logo));
//            tvPartList.add(new ProductsInfo("HERE IT IS", R.drawable.monitor));
//            tvPartList.add(new ProductsInfo("WHERE IT IS", R.drawable.logo));
//            tvPartList.add(new ProductsInfo("HERE IT IS", R.drawable.monitor));
            dm.setAllItemsInSection(tvPartList);

            allSampleData.add(dm);

        }
    }

    private void BuildRecyclerView() {

        my_recycler_view.setHasFixedSize(true);
        ProductsSectionAdapter adapter = new ProductsSectionAdapter( allSampleData,this.getContext());
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        my_recycler_view.setAdapter(adapter);

    }
}
