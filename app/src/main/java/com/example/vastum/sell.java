package com.example.vastum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sell#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sell extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ArrayList<ProductsInfo> tvPartList;
    private TVitemAdapter tvitemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;
    private static final int SELECT_FILE = 2 ;
    private int getIntentKey;
    private CardView buttonCard;
    private Spinner category,brand,type,age;
    private DatabaseReference mDatabase,mdbProd,mdbUser;
    private RelativeLayout relativeLayout;
    private TextView textPoints;
    private String points;
    private StorageReference stReff;
    private ProductsInfo prod;
    private SharedPreferences pref;
    private ArrayList<Uri> mImageUri;
    private FirebaseAuth mAuth;
    int uploadcount;

    public sell() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sell.
     */
    // TODO: Rename and change types and number of parameters
    public static sell newInstance(String param1, String param2) {
        sell fragment = new sell();
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
        View view=inflater.inflate(R.layout.fragment_sell, container, false);
        imgCapture = view.findViewById(R.id.imageView);
        buttonCard = view.findViewById(R.id.buttonCard);
        category = view.findViewById(R.id.itemCategoryDropDown);
        brand = view.findViewById(R.id.itemBrandDropDown);
        type = view.findViewById(R.id.itemTypeDropDown);
        age=view.findViewById(R.id.itemAgeDropDown);
        relativeLayout = view.findViewById(R.id.homeRelative);
        mDatabase = FirebaseDatabase.getInstance().getReference("/Category");
        mdbProd = FirebaseDatabase.getInstance().getReference().child("productsForSell");
        mdbUser = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        pref = this.getActivity().getSharedPreferences("StoredPreferences",MODE_PRIVATE);
        mImageUri=new ArrayList<Uri>();
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("NotFirstTime",false);
        editor.commit();

        //prod = new ProductsInfo(mdbProd.push().getKey());



        spinnerfillup();




        imgCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
//                camera();
            }
        });
        buttonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPoints();
            }
        });


        return view;
    }

    private void spinnerfillup() {

        Query databsase1 = FirebaseDatabase.getInstance().getReference("Category/Refrigerator/Old");
        databsase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot.child("Television").getChildrenCount());
                final List<String> items = new ArrayList<String>();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    items.add(itemName);
                }
                java.util.Collections.sort(items);
                final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(sell.this.getContext(), android.R.layout.simple_spinner_item, items);
                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                age.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query databsase = FirebaseDatabase.getInstance().getReference("Category");
        databsase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot.child("Television").getChildrenCount());
                final List<String> items = new ArrayList<String>();

                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    items.add(itemName);
                }
                java.util.Collections.sort(items);
                final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(sell.this.getContext(), android.R.layout.simple_spinner_item, items);
                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(itemAdapter);

                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selected = items.get(i);

                        mDatabase.child(selected).child("Brand").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                final List<String> items = new ArrayList<String>();

                                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                                    String itemName = itemSnapshot.getKey();
                                    items.add(itemName);
                                }
                                java.util.Collections.sort(items);
                                ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(sell.this.getContext(), android.R.layout.simple_spinner_item, items);
                                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                brand.setAdapter(itemAdapter);

                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        mDatabase.child(selected).child("Type").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                final List<String> items = new ArrayList<String>();

                                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                                    String itemName = itemSnapshot.getKey();
                                    System.out.println(itemName);
                                    items.add(itemName);
                                }
                                java.util.Collections.sort(items);
                                ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(sell.this.getContext(), android.R.layout.simple_spinner_item, items);
                                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                type.setAdapter(itemAdapter);

                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }

    private void showPoints(){
        //sellPointsDialog sellPointsDialog= new sellPointsDialog();
        //sellPointsDialog.setPoints(checkPoints());
        //sellPointsDialog.show(this.getContext()getParentFragmentManager(),"sellPointsDialog");



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_sell_points,null);
        textPoints = view.findViewById(R.id.textPoints);
        textPoints.setText(checkPoints());

        builder.setView(view)
                .setTitle("Points")
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //deleteFolder();
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sellSuccessful();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                        //deleteFolder();
                        dialogInterface.dismiss();
            }
                }).show();

    }
    private void toast(String s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
    }

//EDITED BY PARTH
    private void deleteFolder(){
        stReff.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for(StorageReference file:listResult.getItems()) {
                    file.delete();
                    toast("Deleted");
                }
            }
        });
        //Task<ListResult> t=stReff.listAll();
        //toast(t.toString());

        /*stReff.child().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              //  Toast.makeText(getContext(),"Product removed",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                System.out.println("---------ERRROR--------------" +"-------------------------------------");
                Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        });
        stReff.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Product removed",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                System.out.println("---------ERRROR--------------" +"-------------------------------------");
                Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        });*/
    }

    private String checkPoints(){
        return "1000";
    }

    private void cameraIntent() {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt,Image_Capture_Code);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    public void sellSuccessful() {
        SharedPreferences.Editor editor = pref.edit();
        prod = new ProductsInfo(mdbProd.push().getKey());
        uploadcount=0;
        prod.setProductCategory(category.getSelectedItem().toString());
        prod.setProductBrand(brand.getSelectedItem().toString());
        prod.setProductAge(age.getSelectedItem().toString());
        prod.setProductType(type.getSelectedItem().toString());
        prod.setProductName(type.getSelectedItem().toString() +" "+ category.getSelectedItem().toString());
        prod.setProductPoints(checkPoints());
        stReff = FirebaseStorage.getInstance().getReference().child(prod.getProductID());
        editor.putString("productID",prod.getProductID());
        editor.commit();
        for(Uri u1 : mImageUri){
            final ProgressDialog loading = ProgressDialog.show(getContext(), "Uploading Item", "Please Wait");
            final int imageNumber = pref.getBoolean("NotFirstTime", false) ? (new Random()).nextInt(Integer.MAX_VALUE) : 1;
            if (imageNumber == 1) {
                (pref.edit()).putBoolean("NotFirstTime", true).commit();
//                    prod.setProductFirstImageURI(mImageUri.toString());
            }
//                pro d.setProductImageUri(mImageUri.toString());
            final StorageReference mFileRef = stReff.child("img" + imageNumber + ".jpeg");

            mFileRef.putFile(u1).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return mFileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                        uploadcount++;
                    }
                    else {
                        Toast.makeText(sell.this.getContext(),"Some Images might not be uploaded",Toast.LENGTH_SHORT);
                    }
                    if (imageNumber == 1) {
                        prod.setProductFirstImageURI(downloadUri.toString());
                    }
                    prod.setProductImageUri(downloadUri.toString());
                    mImageUri.remove(0);
                    if(mImageUri.size()==0) mdbProd.child(prod.getProductID()).setValue(prod);
                    Toast.makeText(getContext(), uploadcount + " Images Uploaded ", Toast.LENGTH_SHORT).show();
                    loading.cancel();
                }
            });

        }


        mdbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("userID").getValue().toString().equals(mAuth.getCurrentUser().getUid())){
                       setUserProducts(ds.getKey().toString(),  ds.child("userSoldProduct").getValue().toString(),ds.child("userPoints").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imgCapture.setImageDrawable(getResources().getDrawable(R.drawable.image_add));
    }

    private void setUserProducts(String Uid,String Prod,String Points){
        mdbUser.child(Uid).child("userSoldProduct").setValue(Prod+","+prod.getProductID());
        String value = Integer.toString(Integer.parseInt(Points)+Integer.parseInt(prod.getProductPoints()));
        mdbUser.child(Uid).child("userPoints").setValue(value);
        ((Main2Activity)getContext()).setWalletText(value);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK) {
            if (requestCode == Image_Capture_Code) {

                Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapImage, "Title", null);
                Uri temp=Uri.parse(path);
                mImageUri.add(temp);
            }
            else if(requestCode == SELECT_FILE){
                mImageUri.add(data.getData());
            }

            Picasso.get().load(mImageUri.get(mImageUri.size()-1)).into(imgCapture);


        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this.getContext(), "Cancelled", Toast.LENGTH_LONG).show();

        }
    }

    private void selectImage(){
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result=Utility.checkPermission(getContext());
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
