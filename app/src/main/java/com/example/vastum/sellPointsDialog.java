package com.example.vastum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class sellPointsDialog extends AppCompatDialogFragment  {
    private TextView textPoints;
    private String points;

    public interface sellPointsListener{
        void sellSuccessful();
    }

    public sellPointsListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (sellPointsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement getEventInfo ");
        }
    }

    public void setPoints(String Textpoints){
        points = Textpoints;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_sell_points,null);
        textPoints = view.findViewById(R.id.textPoints);
        textPoints.setText(points);
        builder.setView(view)
                .setTitle("Points")
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.sellSuccessful();
                    }
                });

        return builder.create();
    }
}
