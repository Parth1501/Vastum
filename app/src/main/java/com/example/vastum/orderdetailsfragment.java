package com.example.vastum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class orderdetailsfragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    String orders[]={"order1","order2","order3","order4","order5","order6","order7","order8"};
    orderdetailsfragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.orderdetailsfragment,container,false);
        recyclerView=view.findViewById(R.id.odrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new OdetailAdapter(this.getContext(),orders));
        return view;
    }
}
