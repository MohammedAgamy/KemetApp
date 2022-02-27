package com.kemet.kemetapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;


import com.kemet.kemetapp.R;


public class HomeActivity extends AppCompatActivity {
    DrawerLayout mNavigationDrawer;
    ImageView mOpenMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iniView();


    }

    //iniView
    private void iniView() {
        mOpenMenu = findViewById(R.id.meny_nav);
        mNavigationDrawer = findViewById(R.id.NavigationDrawer);


        mOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationDrawer.openDrawer(Gravity.LEFT);

            }
        });

    }
}