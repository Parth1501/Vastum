package com.example.vastum;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductsSectionAdapter extends RecyclerView.Adapter<ProductsSectionAdapter.ItemRowHolder> {


    private ArrayList<ProductsSectionsModel> dataList;
    private Context mContext;

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_list, null);

        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    public ProductsSectionAdapter(ArrayList<ProductsSectionsModel> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
        final String sectionName = dataList.get(position).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(position).getAllItemsInSection();

        holder.itemTitle.setText(sectionName);
        TVitemAdapter tvadp = new TVitemAdapter(singleSectionItems,mContext);

        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(tvadp);


    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;



        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnMore= (Button) view.findViewById(R.id.btnMore);

            this.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm=((Main2Activity) v.getContext()).getSupportFragmentManager();
                    Fragment old=fm.findFragmentByTag("home");
                    Bundle b1=new Bundle();
                    b1.putString("title",itemTitle.getText().toString());
                    Fragment newfrag=new CategoryBrowse();
                    newfrag.setArguments(b1);
                    fm.beginTransaction().hide(old).add(R.id.homehost,newfrag,"category").commit();
                }
            });
        }

    }

}
