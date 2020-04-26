package com.example.vastum;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button logoutButton;
    private Button orderdetails,accountdetails;
    private BottomNavigationView bottomNavigationView;
    private TextView userName;
    private RelativeLayout relativeLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String str[]={"Account Details","Ordered Details","Log Out"};
    private int img[]={R.drawable.account,R.drawable.order,R.drawable.logout};
    private ListView list;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        userName=view.findViewById(R.id.UserName);
        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        list=view.findViewById(R.id.profile_list);
        //ArrayAdapter<Integer> adapter_img=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,str);
        //ArrayAdapter last=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1);
       // last.add(adapter);
       // last.add(adapter_img);
        MyAdapter myAdapter=new MyAdapter();
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getContext(),AccountDetailActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(profile.this.getContext(), ordersdetails.class));
                        break;
                    case 2:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(profile.this.getContext(), LoginAcitivity.class));
                        getActivity().getSupportFragmentManager().popBackStack();
                        break;

                }
            }
        });



        /*logoutButton = view.findViewById(R.id.ProfileLogoutButton);
        userName = view.findViewById(R.id.UserName);
        relativeLayout= view.findViewById(R.id.profileRelative);
        accountdetails=view.findViewById(R.id.Account_Details);
        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        accountdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AccountDetailActivity.class));
            }
        });
        orderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this.getContext(), ordersdetails.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this.getContext(), LoginAcitivity.class));
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });*/

        return view;
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=getLayoutInflater().inflate(R.layout.profile_list_view,null);
            ImageView i=v.findViewById(R.id.profile_image);
            TextView t=v.findViewById(R.id.profile_text);
            i.setImageResource(img[position]);
            t.setText(str[position]);

            return v;
        }
    }
}
