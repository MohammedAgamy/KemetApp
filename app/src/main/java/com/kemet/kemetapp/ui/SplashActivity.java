package com.kemet.kemetapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kemet.kemetapp.R;

public class SplashActivity extends AppCompatActivity {
    ImageView mLogoSplash;
    Animation mStartAnim;

    //fireBase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        iniView();
        handlerSplash();

    }

    private void iniView() {

        //findView
        mLogoSplash = findViewById(R.id.logoSplash);
        //load anim logo
        mStartAnim = AnimationUtils.loadAnimation(this, R.anim.logo_kemet_anim);
        mLogoSplash.startAnimation(mStartAnim);

        //firebase
        mAuth = FirebaseAuth.getInstance();
    }

    //methodSplash
    public void handlerSplash() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    //check user is register or no
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else {
                    //start RegisterActivity
                    startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                    finish();
                }
            }
        }, 3000);

    }


}