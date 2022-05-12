package com.kemet.kemetapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kemet.kemetapp.Adapter.CivilizationAdapter;
import com.kemet.kemetapp.Adapter.HomeAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.CivilizationModel;
import com.kemet.kemetapp.pojo.HomeModel;
import com.kemet.kemetapp.ui.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CivilizationFragment extends Fragment implements CivilizationAdapter.OnClickCivilization {
    //view
    RecyclerView mCivilizationRecycler;

    //Model
    List<CivilizationModel> mList;
    CivilizationAdapter mCivilizationAdapter;
    String mIdCiv ;

    //Firebase
    FirebaseFirestore mFireStore;


    public CivilizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_civilization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniView(view);
        getDataFromFirebase();
        onBack();
    }


    private void iniView(View view) {
        //View
        mCivilizationRecycler = view.findViewById(R.id.CivilizationHome);
        //
        mList = new ArrayList<>();
        //Firebase
        mFireStore = FirebaseFirestore.getInstance();
        Bundle bundle = getArguments();
        assert bundle != null;
        mIdCiv = (String) bundle.get("typeId");
    }

    private void getDataFromFirebase() {

        mFireStore.collection("CivilizationItem").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            CivilizationModel homeModel = snapshot.toObject(CivilizationModel.class);
                            String  id = (String) snapshot.get("typeId");
                            if(mIdCiv.equalsIgnoreCase(id))
                            {
                                mList.add(homeModel);
                            }

                        }
                        mCivilizationAdapter.notifyDataSetChanged();
                    }
                });


        mCivilizationAdapter = new CivilizationAdapter(getActivity(), mList, this);
        mCivilizationRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCivilizationRecycler.setAdapter(mCivilizationAdapter);
        mCivilizationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickCivizItem(String id) {
        civilizationInformationFragment civilizationInformationFragment = new civilizationInformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("civilizationFragment", id);
        civilizationInformationFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, civilizationInformationFragment).commit();


    }


    private void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                HomeFragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

    }
}