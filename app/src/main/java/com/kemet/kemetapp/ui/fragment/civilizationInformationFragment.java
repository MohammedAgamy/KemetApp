package com.kemet.kemetapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kemet.kemetapp.R;

import org.jetbrains.annotations.NotNull;

public class civilizationInformationFragment extends Fragment {

    FirebaseFirestore mFirestore;
    DocumentReference documentReference;
    String mIdCiv;

    TextView mCiv_Name;

    public civilizationInformationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_civilization_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        mIdCiv = (String) bundle.get("civilizationFragment");
        //firebase
        mFirestore = FirebaseFirestore.getInstance();
        //View
        mCiv_Name = view.findViewById(R.id.civ_name_inf);
        getDataFromFireBase();
    }

    private void getDataFromFireBase()
    {
        mFirestore.collection("CivilizationInformation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                String id = documentSnapshots.getString("idCiv");
                                String civName = documentSnapshots.getString("name");

                                if (mIdCiv.equalsIgnoreCase(id)) {
                                    mCiv_Name.setText(civName);
                                }

                                Log.d("taskTag", documentSnapshots.getId());
                            }
                        } else {
                            Log.d("taskTag", String.valueOf(task.getException()));

                        }

                    }
                });
    }


    @Override
    public void onPause() {
        super.onPause();
        CivilizationFragment civilizationFragment=new CivilizationFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,civilizationFragment).commit();
    }
}