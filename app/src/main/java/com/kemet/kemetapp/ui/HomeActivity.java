package com.kemet.kemetapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.ui.fragment.HomeFragment;
import com.kemet.kemetapp.ui.fragment.ProfileFragment;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout mNavigationDrawer;
    ImageView mOpenMenu;
    ProfileFragment profileFragment;
    HomeFragment homeFragment;
    LinearLayout mBtnProfile, mBtnThemes, mLogOut;
    BottomNavigationView mBottomNavigationView;


    UiModeManager mUiModeManager;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iniView();
        openMenu();
        onBottomNavSelected();


    }

    //iniView
    private void iniView() {
        mOpenMenu = findViewById(R.id.meny_nav);
        mNavigationDrawer = findViewById(R.id.NavigationDrawer);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBtnProfile = findViewById(R.id.icon_profiel);
        mBtnProfile.setOnClickListener(this);
        mBtnThemes = findViewById(R.id.menu_themes);
        mBtnThemes.setOnClickListener(this);
        mLogOut = findViewById(R.id.logOut);
        mLogOut.setOnClickListener(this);


        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();


        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_profiel:
                ///got to profile
                //الذهاب الى شاشة البروفيل فرجمنت (intent)
                mBtnProfile.setBackgroundResource(R.color.linermenu);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profileFragment).commit();
                mNavigationDrawer.close();
                break;
            case R.id.menu_language:
                changeLanguuage();
                break;
            //changeThemes
            case R.id.menu_themes:
                mBtnThemes.setBackgroundResource(R.color.linermenu);
                changeThemes();
                break;
            case R.id.logOut:
                mLogOut.setBackgroundResource(R.color.linermenu);
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, SplashActivity.class));
                finish();
                break;

        }
    }


    public void openMenu() {

        mOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //فتح النافجيشن من الشمال
                mNavigationDrawer.openDrawer(Gravity.LEFT);

            }
        });
    }

    public void onBottomNavSelected() {
        mBottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            switch (item.getItemId()) {
                case R.id.home_bottom_nav:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();
                    return true;
                case R.id.profile_bottom_nav:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profileFragment).commit();
                    return true;

            }
            return false;
        });
    }

    private void changeLanguuage() {

    }


    private void changeThemes() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dielog_item);
        SwitchMaterial mSwitchCompat = dialog.findViewById(R.id.swOnOff);
        dialog.show();


        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isChecked = true;
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    dialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, SplashActivity.class));


                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }

            }
        });
        mNavigationDrawer.close();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder outDialog = new AlertDialog.Builder(this);
        outDialog.setTitle("Do You Need Exit");
        outDialog.setPositiveButton("Exit ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });


    }
}






