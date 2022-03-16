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
import com.kemet.kemetapp.Adapter.HotelAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.HotelModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class  HotielFragment extends Fragment implements HotelAdapter.OnClickHotel{
    //view
    RecyclerView mHotelRecycler;

    //Model
    List<HotelModel> mList;
    HotelAdapter mHotelAdapter;

    //Firebase
    FirebaseFirestore mFireStore;

    public HotielFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotiel, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniView(view);
        getDataFromFirebase();
    }


    private void iniView(View view) {

        //View
        mHotelRecycler = view.findViewById(R.id.recyclerHotiel);
        //list
        mList = new ArrayList<>();
        //Firebase
        mFireStore = FirebaseFirestore.getInstance();

    }

    private void getDataFromFirebase() {

        mFireStore.collection("HotelItem").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            HotelModel hotelModel = snapshot.toObject(HotelModel.class);
                            mList.add(hotelModel);
                        }
                        mHotelAdapter.notifyDataSetChanged();
                    }
                });


        mHotelAdapter = new HotelAdapter(getActivity(), mList,this);
        mHotelRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHotelRecycler.setAdapter(mHotelAdapter);
        mHotelAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickHotelItem(String id) {
       HotelInformationFragment hotelInformationFragment=new HotelInformationFragment();
       Bundle bundle=new Bundle();
       bundle.putString("idHotel" ,id);
       hotelInformationFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,hotelInformationFragment).commit();
    }
}