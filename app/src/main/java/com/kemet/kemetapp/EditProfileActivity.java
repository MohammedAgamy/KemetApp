package com.kemet.kemetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.kemet.kemetapp.ui.fragment.ProfileFragment;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
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
    private Spinner nationality,  day, month, year;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Name = findViewById(R.id.Name);
        Phone =findViewById(R.id.Phone);
        progressBar = findViewById(R.id.Progress);
        nationality = findViewById(R.id.spinner1);
        day = findViewById(R.id.spinner2);
        month = findViewById(R.id.spinner3);
        year =findViewById(R.id.spinner4);
        Male = findViewById(R.id.radio_Male);
        Female = findViewById(R.id.radio_Female);
        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        //onclickl
        savedata = findViewById(R.id.Savedata);

    }
    public void Savedata(View v) {
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
                        Toast.makeText(EditProfileActivity.this, "Save Data", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfileActivity.this, ProfileFragment.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

    }

    public void UpdateUser(View v) {

        String name = Name.getText().toString();
        String phone = Phone.getText().toString();
        String Nationality = nationality.getSelectedItem().toString();
        String Day = day.getSelectedItem().toString();
        String Month = month.getSelectedItem().toString();
        String Year = year.getSelectedItem().toString();


        // HashMap<String, Object> map = new HashMap<>();
        // map.put(KEY_Name, name);
        // map.put(KEY_Phone,phone);
        //  map.put(KEY_Nationality, Nationality);
        // map.put(KEY_Day,Day);
        // map.put(KEY_Month,Month);
        // map.put(KEY_Year,Year);

        //data.set(map, SetOptions.merge());
        data.update(KEY_Name, name);
        data.update(KEY_Phone, phone);
        data.update(KEY_Nationality, Nationality);
        data.update(KEY_Day, Day);
        data.update(KEY_Month, Month);
        data.update(KEY_Year, Year);

        updateData();
        Toast.makeText(this, "updatedata", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditProfileActivity.this  , ProfileFragment.class));


    }
    public void   updateData(){
        data.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(EditProfileActivity.this, "Error while loading !", Toast.LENGTH_SHORT).show();
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

}