package com.example.vastum;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReedemAdapter extends RecyclerView.Adapter<ReedemAdapter.ReedemHolder> {
    List<String> voucher_name;
    List<Integer> points;
    private OnRedeemClick mListener;
    public interface OnRedeemClick {
        void reedem(int i);
    }
    public void setOnRedeemClickListener(OnRedeemClick listener) {
        mListener=listener;
    }


    public ReedemAdapter(List<String> voucher_name, List<Integer> points) {
        this.voucher_name=voucher_name;
        this.points=points;
    }

    @NonNull
    @Override
    public ReedemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.activity_redeem_data,viewGroup,false);
        return new ReedemHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReedemHolder reedemHolder, int i) {
        reedemHolder.voucher_name_view.setText(voucher_name.get(i));
        reedemHolder.points_view.setText(points.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return voucher_name.size();
    }

    public static class ReedemHolder extends RecyclerView.ViewHolder {

        TextView voucher_name_view,points_view;
        Button redeem_btn;

        public ReedemHolder(@NonNull View itemView, final OnRedeemClick listener) {
            super(itemView);
            voucher_name_view=itemView.findViewById(R.id.voucher_name);
            points_view=itemView.findViewById(R.id.value);
            redeem_btn=itemView.findViewById(R.id.redeem_button);

            redeem_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=getAdapterPosition();
                    listener.reedem(i);
                }
            });

        }



    }

}
