package com.kemet.kemetapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kemet.kemetapp.Adapter.TourGuideAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.TourGuideModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TourGuideFragment extends Fragment {
    //view
    RecyclerView mTourGuideRecycler;

    //Model
    ArrayList<TourGuideModel> mList;
    TourGuideAdapter mTourGuideAdapter;

    //Firebase
    FirebaseFirestore mFireStore;

    public TourGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tour_guide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniView(view);
        getDataFromFirebase();

    }


    private void iniView(View view) {

        //View
        mTourGuideRecycler = view.findViewById(R.id.tour_guide_recycler);
        //list
        mList = new ArrayList<>();
        //Firebase
        mFireStore = FirebaseFirestore.getInstance();


    }


    //method get data from firebase
    private void getDataFromFirebase() {

        //get data from fireStore
        mFireStore.collection("TourGuide").orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            TourGuideModel tourGuideModel = snapshot.toObject(TourGuideModel.class);
                            mList.add(tourGuideModel);
                        }
                        mTourGuideAdapter.notifyDataSetChanged();
                    }
                });


        mTourGuideAdapter = new TourGuideAdapter(getActivity(), mList);
        mTourGuideRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTourGuideRecycler.setAdapter(mTourGuideAdapter);
        mTourGuideAdapter.notifyDataSetChanged();


    }

}