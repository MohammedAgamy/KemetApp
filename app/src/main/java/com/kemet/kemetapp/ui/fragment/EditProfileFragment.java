package com.kemet.kemetapp.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kemet.kemetapp.EditProfileActivity;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.UserInfoModel;
import com.kemet.kemetapp.ui.HomeActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "EditProfileActivity2";
    private static final String KEY_Name = "name";
    private static final String KEY_Phone = "phone";
    private static final String KEY_Nationality = "nationality";
    private static final String KEY_Gender = " gender";
    private static final String KEY_Day = "day";
    private static final String KEY_Month = "month";
    private static final String KEY_Year = "year";
    private static final String KEY_Image = "image";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference data = db.document("UsersData/profile");

    private ProgressBar progressBar;
    private EditText Name, Phone;
    private Button savedata;
    private RadioButton Male, Female;

    private ImageView uaer_imageView, mTackPhoto;
    private FirebaseAuth firebaseAuth;
    private Spinner nationality, day, month, year;
    private FirebaseFirestore firestore;
    StorageReference storageReference ;
    private Uri resultUri;

    String mImagePass;



    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Name = view.findViewById(R.id.Name);
        Phone = view.findViewById(R.id.Phone);
        progressBar = view.findViewById(R.id.Progress);
        nationality = view.findViewById(R.id.spinner1);
        day = view.findViewById(R.id.spinner2);
        month = view.findViewById(R.id.spinner3);
        year = view.findViewById(R.id.spinner4);
        Male = view.findViewById(R.id.radio_Male);
        Female = view.findViewById(R.id.radio_Female);
        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        //onclickl
        savedata = view.findViewById(R.id.Savedata);
        mTackPhoto=view.findViewById(R.id.camrea);
        mTackPhoto.setOnClickListener(this);
        uaer_imageView=view.findViewById(R.id.uaer_imageView);





        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Savedata();
            }

        });

        view.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUser();
            }
        });
    }

    private void UpdateUser() {
        String name = Name.getText().toString();
        String phone = Phone.getText().toString();
        String Nationality = nationality.getSelectedItem().toString();
        String Day = day.getSelectedItem().toString();
        String Month = month.getSelectedItem().toString();
        String Year = year.getSelectedItem().toString();


        data.update(KEY_Name, name);
        data.update(KEY_Phone, phone);
        data.update(KEY_Nationality, Nationality);
        data.update(KEY_Day, Day);
        data.update(KEY_Month, Month);
        data.update(KEY_Year, Year);


        updateData();
        Toast.makeText(getActivity(), "updatedata", Toast.LENGTH_SHORT).show();
        Fragment fragment = new Fragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();


    }

    private void updateData() {
        data.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(getActivity(), "Error while loading !", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString(KEY_Name);
                    String phone = documentSnapshot.getString(KEY_Phone);
                    // String Nationality = documentSnapshot.getString(KEY_Nationality);
                    //  String gender = documentSnapshot.getString(KEY_Gender);
                    //   String day = documentSnapshot.getString(KEY_Day);
                    //   String month = documentSnapshot.getString(KEY_Month);
                    //   String year = documentSnapshot.getString(KEY_Year);


                    Name.setText(name);
                    Phone.setText(phone);
                } else {
                    Name.setText(" ");
                    Phone.setText(" ");

                }
            }
        });

    }

    private void Savedata() {
        String name = Name.getText().toString();
        String phone = Phone.getText().toString();
        String male = Male.getText().toString();
        String female = Female.getText().toString();


        // Create a new user
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("nationality", nationality.getSelectedItem());
        map.put("day", day.getSelectedItem());
        map.put("month", month.getSelectedItem());
        map.put("year", year.getSelectedItem());
        map.put("gender", male);
        map.put("gender", female);
        map.put("phone", phone);
        map.put("image", "null");


        data.set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Save Data", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new Fragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

    }

public void updateUserData()
{
    String name = Name.getText().toString();
    String phone = Phone.getText().toString();
    String male = Male.getText().toString();
    String female = Female.getText().toString();

    if(name.isEmpty() && phone.isEmpty() && male.isEmpty() || female.isEmpty())
    {
        Toast.makeText(getActivity(), "Enter Your Data..", Toast.LENGTH_SHORT).show();
    }

    else
    {
        UserInfoModel userInfoModel=new UserInfoModel(name ,phone ,male,female ,null );
        firestore.collection("UserInfo").document().set(userInfoModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            ProfileFragment profileFragment =new ProfileFragment();
                            Toast.makeText(getActivity(), "Data Updated..", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,profileFragment).commit();
                        }
                    }
                });

    }
}

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
           case R.id.camrea:
               checkPermission();
            break;
        }

    }



    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // to ask user to reade external storage
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                tackPhoto();
            }
        }
    }


    private void tackPhoto() {
        // Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
        CropImage.activity()
                .start(getContext(), this);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Glide.with(getActivity()).load(resultUri).into(uaer_imageView);
                uPloadImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private void uPloadImage(Uri resultUri) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String userID=auth.getCurrentUser().getUid();
        StorageReference ref = storageReference.child("CarPass").child(userID+ ".jpg");
        // Log.d("userId" , userID);
        ref.putFile(resultUri);


        ref.getDownloadUrl().addOnCompleteListener(task -> {
            Uri downloadUrl=  task.getResult();
            mImagePass =  downloadUrl.toString();
            Log.d("mImagepass" , mImagePass);
        });

    }
}