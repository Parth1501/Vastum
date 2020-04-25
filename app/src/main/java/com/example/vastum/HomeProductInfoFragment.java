package com.example.vastum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


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
    private String[] images;
    private DatabaseReference dbRef;

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
        final Bundle b=this.getArguments();
        final View newView = view;



        final Gallery gallery= view.findViewById(R.id.gallery1);
        Log.e("THE PRODUCT","**"+b.getString("product id")+"**");
        dbRef  = FirebaseDatabase.getInstance().getReference().child("productsForSell");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("productID").getValue(String.class).equals(b.getString("product id"))) {
                        Log.e("THE IMAGES URI", ds.child("productImageUri").getValue().toString() + " ");
                        setNewResources(ds.child("productImageUri").getValue().toString());
                    }
                    Log.e("IMAGE URI","NOURI");
                }
                gallery.setAdapter(new ImageAdapter(getContext()));
                gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                    {
                        // display the images selected
                        ImageView imageView = (ImageView) newView.findViewById(R.id.ImageViewItem);
                        if(!images[position].equals("")){
                            Glide.with(getContext()).load(images[position]).into(imageView);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });




        return view;
    }


    private void setNewResources(String res){
        images = res.split(",");
    }
    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        public ImageAdapter(Context context){
            this.context = context;
            TypedArray a = getActivity().obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground,0);
            a.recycle();
        }
        public int getCount(){
            return images.length;
        }

        @Override
        public Object getItem(int i) {
            return images[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(context);
            if(!images[i].equals("")){
                Glide.with(context).load(images[i]).into(imageView);
            }
            imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }


}
