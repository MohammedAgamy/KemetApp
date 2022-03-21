package com.kemet.kemetapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.Adapter.RoomAdapter;
import com.kemet.kemetapp.pojo.SelectRoomModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShowAllRoomFragment extends Fragment implements RoomAdapter.OnClickRoom {
    RecyclerView mRoomRecycler;
    RoomAdapter mRoomAdapter;
    ArrayList<SelectRoomModel> mList;

    //Firebase
    FirebaseFirestore mFireStore;

    public ShowAllRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_all_room, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniView(view);
        onBack();

    }

    private void iniView(View view) {

        //View
        mRoomRecycler = view.findViewById(R.id.showAllRoom_Recycler);
        //list
        mList = new ArrayList<>();
        //Firebase
        mFireStore = FirebaseFirestore.getInstance();
        getDataFromFirebase();

    }

    private void getDataFromFirebase() {


        mFireStore.collection("RoomType").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            SelectRoomModel hotelModel = snapshot.toObject(SelectRoomModel.class);
                            mList.add(hotelModel);
                        }
                        mRoomAdapter.notifyDataSetChanged();
                    }
                });
        mRoomAdapter = new RoomAdapter(getActivity(), mList, this);
        mRoomRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRoomRecycler.setAdapter(mRoomAdapter);
        mRoomAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClickRoomItem()
    {
        RoomOrderFragment roomOrderFragment = new RoomOrderFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, roomOrderFragment).commit();
    }

    private void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                HotelInformationFragment fragment=new HotelInformationFragment();
                Bundle bundle=new Bundle();
                bundle.putString("idHotel" ,"null");
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment ,fragment).commit();
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity() , callback);

    }
}