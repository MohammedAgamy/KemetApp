package com.kemet.kemetapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.ui.HomeActivity;

import java.util.HashMap;


public class EditProfileFragment extends Fragment {
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

    private ImageView image;
    private FirebaseAuth firebaseAuth;
    private Spinner  nationality,  day, month, year;
    private FirebaseFirestore firestore;


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
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Name = view.findViewById(R.id.Name);
        Phone =view. findViewById(R.id.Phone);
        progressBar = view.findViewById(R.id.Progress);
       nationality = view.findViewById(R.id.spinner1);
       day =view. findViewById(R.id.spinner2);
        month =view. findViewById(R.id.spinner3);
        year =view. findViewById(R.id.spinner4);
        Male = view.findViewById(R.id.radio_Male);
        Female =view. findViewById(R.id.radio_Female);
        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        //onclickl
        savedata =view. findViewById(R.id.Savedata);
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
        Fragment fragment=new Fragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();


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
        map.put("nationality",nationality.getSelectedItem());
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
                        Fragment fragment=new Fragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

    }


}