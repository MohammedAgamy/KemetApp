package com.kemet.kemetapp.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.kemet.kemetapp.R;
import com.kemet.kemetapp.ui.fragment.ProfileFragment;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout mNavigationDrawer;
    ImageView mOpenMenu;
    ProfileFragment profileFragment;
    LinearLayout mBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iniView();
        openMenu();


    }

    //iniView
    private void iniView() {
        mOpenMenu = findViewById(R.id.meny_nav);
        mNavigationDrawer = findViewById(R.id.NavigationDrawer);
        mBtnProfile = findViewById(R.id.icon_profiel);
        mBtnProfile.setOnClickListener(this);


        profileFragment = new ProfileFragment();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_profiel:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profileFragment).commit();
                mNavigationDrawer.close();
                break;
        }
    }



    public void openMenu() {

        mOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationDrawer.openDrawer(Gravity.LEFT);

            }
        });
    }


}






