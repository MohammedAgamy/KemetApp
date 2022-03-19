package com.kemet.kemetapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kemet.kemetapp.Adapter.HomeAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.HomeModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
        //list
        mList = new ArrayList<>();
        //Firebase
        mFireStore = FirebaseFirestore.getInstance();


    }


    //method get data from firebase
    private void getDataFromFirebase() {

        //get data from fireStore
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
        if (s.equalsIgnoreCase("1")) {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_tourGuideFragment);

        }

        if (s.equalsIgnoreCase("2")) {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_civilizationFragment);

        }
        if (s.equalsIgnoreCase("3")) {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_carFragment);

        }

    }

    @Override
    public void onItemClick(String pos) {

    }

}