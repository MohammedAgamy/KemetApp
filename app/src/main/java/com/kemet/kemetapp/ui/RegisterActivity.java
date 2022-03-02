package com.kemet.kemetapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.SaveUserData;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mName, mEmail, mPassword;
    Button mBtnRegister;
    LinearLayout mParent;
    LottieAnimationView mWaiteAnim;
    ImageView mBack_Login, mBtn_Login;

    //firebase
    FirebaseAuth mAuth;

    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniView();
    }


    private void iniView() {
        //EditText FindView
        mName = findViewById(R.id.enterName_Register);
        mEmail = findViewById(R.id.enterEmail_Register);
        mPassword = findViewById(R.id.enterPassword_Register);
        mWaiteAnim = findViewById(R.id.animationView);
        mParent = findViewById(R.id.vicibility);
        //Button FindView
        mBtnRegister = findViewById(R.id.btnregister);
        mBtnRegister.setOnClickListener(this);
        mBack_Login = findViewById(R.id.btnBack_Register);
        mBack_Login.setOnClickListener(this);
        mBtn_Login = findViewById(R.id.gologin_register);
        mBtn_Login.setOnClickListener(this);
        //FireBase
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnregister:
                register();
                break;

            case R.id.gologin_register:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void register() {

        //get data from user
        String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        //check data
        if (validation(email, password, name)) {
            inVisibilty();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mWaiteAnim.setVisibility(View.GONE);
                            //startActivity
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                            //get id
                            FirebaseUser user = mAuth.getCurrentUser();
                            String id = user.getUid();
                            upLoudUserData(name, email, password, id);

                            Snackbar.make(findViewById(android.R.id.content), " User Created.. " + id, Snackbar.LENGTH_LONG).show();

                        } else {
                            mWaiteAnim.setVisibility(View.GONE);
                            mParent.setVisibility(View.VISIBLE);
                            Snackbar.make(findViewById(android.R.id.content), " Check Your Internet.. ", Snackbar.LENGTH_LONG).show();

                        }
                    });

        }


        //auth with firebase


    }


    //METHOD check data
    private boolean validation(String email, String password, String name) {
        if (name.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), " Your Name is Empty", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (email.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), " Your Email is Empty", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(android.R.id.content), " Check Your Email ", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (password.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), " Your Password is Empty", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (password.length() < 6) {
            Snackbar.make(findViewById(android.R.id.content), " Your Password is Short", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    //Visible View
    private void inVisibilty() {
        mWaiteAnim.setVisibility(View.VISIBLE);
        mParent.setVisibility(View.GONE);
    }


    //Upload Data to firebase Firestor
    private void upLoudUserData(String name, String email, String password, String id) {
        //save data in shared
        SaveUserData saveUserData = new SaveUserData(this);
        saveUserData.SaveData(name, email, true);

        HashMap<String, Object> setData = new HashMap<>();
        setData.put("name", name);
        setData.put("email", email);
        setData.put("password", null);
        setData.put("userId", id);
        fireStore.collection("UsersData").document(id).set(setData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }

        });

    }

}