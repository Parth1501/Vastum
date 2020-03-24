package com.example.vastum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    String items[];
    public  ItemRecyclerAdapter(Context mContext,String items[]){
        this.mContext=mContext;
        this.items=items;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View row=inflater.inflate(R.layout.recyclerview_row,parent,false);
        Item item=new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Item)holder).textView.setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public  class Item extends RecyclerView.ViewHolder{
        TextView textView;
        public Item(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.sellitem);
        }
    }
}
