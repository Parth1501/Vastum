package com.example.vastum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

public class OdetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String orders[];
    public  OdetailAdapter(Context context,String orders[]){
        this.context=context;
        this.orders=orders;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.order_dataview,parent,false);
        order od=new order(view);
        return od;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((order)holder).tv.setText(orders[position]);
    }

    @Override
    public int getItemCount() {
        return orders.length;
    }

    public class order extends RecyclerView.ViewHolder {
        TextView tv;
        public order(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.order);
        }
    }
}
