package com.example.vastum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeProductInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeProductInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeProductInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeProductInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeProductInfoFragment newInstance(String param1, String param2) {
        HomeProductInfoFragment fragment = new HomeProductInfoFragment();
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
        View view=inflater.inflate(R.layout.fragment_home_product_info, container, false);
        Bundle b=this.getArguments();
        TextView tv=view.findViewById(R.id.productid);
        tv.setText(b.getString("product id"));
        Button backbtn=view.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm=((Main2Activity) v.getContext()).getSupportFragmentManager();
                //fm.beginTransaction().detach(fm.findFragmentByTag("hpf"));
                //fm.popBackStack();
                fm.beginTransaction().hide(fm.findFragmentByTag("hpf")).commit();
                fm.beginTransaction().addToBackStack("old");
                fm.beginTransaction().show(fm.findFragmentByTag("home")).commit();
                //fm.beginTransaction().replace(R.id.homehost,fm.findFragmentByTag("home")).commit();
            }
        });
        return view;
    }


}
