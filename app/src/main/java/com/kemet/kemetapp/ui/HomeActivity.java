package com.kemet.kemetapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kemet.kemetapp.Adapter.HomeAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.HomeModel;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

public class HomeActivity extends AppCompatActivity {
    SNavigationDrawer mNavigationDrawer;
    ImageView mOpenMenu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iniView();


    }

    //iniView
    private void iniView() {
        mOpenMenu=findViewById(R.id.meny_nav);
        mNavigationDrawer = findViewById(R.id.navigationDrawer);



        mOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNavigationDrawer.openDrawer();

            }
        });

    }
}