package com.example.vastum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TVitemAdapter extends RecyclerView.Adapter<TVitemAdapter.itemViewHolder> {

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
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getName());
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
    public TVitemAdapter(ArrayList<ProductsInfo> itemList){
        mList  = itemList;
    }

}
