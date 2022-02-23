package com.kemet.kemetapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kemet.kemetapp.Adapter.HomeAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.HomeModel;
import com.kemet.kemetapp.ui.RegisterActivity;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.HomeOnClick {

    //view
    RecyclerView mHomeRecycler;

    //Model
    List<HomeModel> mList;
    HomeAdapter mHomeAdapter;

    //Firebase
    FirebaseFirestore mFireStore;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniView(view);
        getDataFromFirebase();
    }


    private void iniView(View view) {

        //View
        mHomeRecycler = view.findViewById(R.id.recyclerHome);


        //
        mList = new ArrayList<>();

        //Firebase
        mFireStore = FirebaseFirestore.getInstance();


    }


    private void getDataFromFirebase() {

        mFireStore.collection("HomeRecyclerr").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            HomeModel homeModel = snapshot.toObject(HomeModel.class);
                            mList.add(homeModel);
                        }
                        mHomeAdapter.notifyDataSetChanged();
                    }
                });


        mHomeAdapter = new HomeAdapter(getActivity(), mList, this);
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRecycler.setAdapter(mHomeAdapter);
        mHomeAdapter.notifyDataSetChanged();


    }


    @Override
    public void onClick_Home(String s) {
        if (s.equalsIgnoreCase("0")) {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_hotielFragment);

        }

        if (s.equalsIgnoreCase("2")) {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_civilizationFragment);

        }
    }
}