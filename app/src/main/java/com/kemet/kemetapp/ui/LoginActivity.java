package com.kemet.kemetapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.kemet.kemetapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //findView
    EditText mEmail, mPassword;
    Button mBtnLogin;
    ImageView mBack_Register, mBtnRegister;

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
                //زرار تسجيل الدخول
                logIn();
                break;

        }

    }

    //دالة تسجيل الدخول
    private void logIn() {
        //get data from user
        // استلام الداتا من المستخدم عن طريق Edit text
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        //check data
        //ختبار اذا ما كان المستخدم ادخل البيانات بشكل صحيح ولا لا
        if (validation(email, password)) {

//login with firebase
            //استخدام Auth عشان يسجل المستخدم فى الفاير بيز
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            // اذا ما كان المستخدم سجل بنجاح يزهب الى شاشة الهوم
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                            Snackbar.make(findViewById(android.R.id.content), " User SignIn.. ", Snackbar.LENGTH_LONG).show();
                        } else {
                            // لم يسجل بنجاح يظهر رساه تفقد من الانترنت
                            Snackbar.make(findViewById(android.R.id.content), " Check Your Internet.. ", Snackbar.LENGTH_LONG).show();

                        }
                    });
        }


    }

    //METHOD check data
    // دالة اختبار البيانات من المستخدم عن طريق if
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