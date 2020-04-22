package com.example.vastum;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TVitemAdapter extends RecyclerView.Adapter<TVitemAdapter.itemViewHolder> {
    private Context context;
    private ArrayList<ProductsInfo> mList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_card, parent, false);
        itemViewHolder evh = new itemViewHolder(v,mListener);
        return  evh;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        ProductsInfo currentItem = mList.get(position);
        holder.textView1.setText(currentItem.getProductName());
        Glide.with(context).load(currentItem.getProductFirstImageURI()).into(holder.imageView);
        Log.e("THE IMAGE",currentItem.getProductFirstImageURI()+" ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=((Main2Activity) v.getContext()).getSupportFragmentManager();
                Fragment old=fm.findFragmentByTag("home");
                Fragment nfrag=new HomeProductInfoFragment();
                fm.beginTransaction().addToBackStack("old");
                fm.beginTransaction().hide(old).commit();
                Bundle b=new Bundle();
                b.putString("product id","product id: i dont know where it is");
                nfrag.setArguments(b);
                fm.beginTransaction().add(R.id.homehost,nfrag,"hpf").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;


        public itemViewHolder(@NonNull View itemView, final OnItemClickListener mListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public TVitemAdapter(ArrayList<ProductsInfo> itemList, Context context){
        mList  = itemList;
        this.context = context;
    }

}
