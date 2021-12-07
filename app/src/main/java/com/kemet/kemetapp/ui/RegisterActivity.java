package com.kemet.kemetapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.kemet.kemetapp.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mName, mEmail, mPassword;
    Button mBtnRegister;

    //firebase
    FirebaseAuth mAuth;


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
        //Button FindView
        mBtnRegister = findViewById(R.id.btnregister);
        mBtnRegister.setOnClickListener(this);
        //FireBase
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnregister:
                register();
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
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                            Snackbar.make(findViewById(android.R.id.content), " User Created.. ", Snackbar.LENGTH_LONG).show();
                        } else {
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
}