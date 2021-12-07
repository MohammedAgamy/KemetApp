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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //findView
    EditText mEmail, mPassword;
    Button mBtnLogin;

    //firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniView();
    }

    private void iniView() {
        //EditText FindView
        mEmail = findViewById(R.id.enterEmail_Login);
        mPassword = findViewById(R.id.enterPassword_Login);
        //Button FindView
        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(this);
        //FireBase
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                logIn();
                break;
        }

    }

    private void logIn() {
        //get data from user
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        //check data
        if (validation(email, password)) {

//login with firebase
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                            Snackbar.make(findViewById(android.R.id.content), " User SignIn.. ", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), " Check Your Internet.. ", Snackbar.LENGTH_LONG).show();

                        }
                    });
        }


    }

    //METHOD check data
    private boolean validation(String email, String password) {
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