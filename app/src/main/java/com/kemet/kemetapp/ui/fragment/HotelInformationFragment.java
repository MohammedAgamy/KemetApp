package com.kemet.kemetapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.ui.HomeActivity;

import org.jetbrains.annotations.NotNull;

public class HotelInformationFragment extends Fragment {
    FirebaseFirestore mFirestore;
    DocumentReference documentReference;
    String idHotel;

    TextView mHotel_Name;

    public HotelInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        idHotel = (String) bundle.get("idHotel");
        //firebase
        mFirestore = FirebaseFirestore.getInstance();
        //View
        mHotel_Name = view.findViewById(R.id.hotel_name_inf);
        getDataFromFireBase();
    }

    // HotelInformation
    private void getDataFromFireBase() {

        mFirestore.collection("HotelItemInfomation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                String id = documentSnapshots.getString("idhotel");
                                String hotelName = documentSnapshots.getString("name");

                                if (idHotel.equalsIgnoreCase(id)) {
                                    mHotel_Name.setText(hotelName);
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
       HotielFragment hotielFragment=new HotielFragment();
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,hotielFragment).commit();

    }
}