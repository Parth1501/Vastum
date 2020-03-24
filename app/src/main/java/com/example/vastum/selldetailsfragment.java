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

public class selldetailsfragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    String items[]={"item1","item2","item3","item4"};
    selldetailsfragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selldetailsfragment,container,false);
        recyclerView=view.findViewById(R.id.itemrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new ItemRecyclerAdapter(this.getContext(),items));
        return view;
    }
}


