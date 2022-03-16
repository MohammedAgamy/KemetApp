package com.kemet.kemetapp.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kemet.kemetapp.Adapter.SliderAdapter;
import com.kemet.kemetapp.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

public class HotelInformationFragment extends Fragment implements View.OnClickListener {
    FirebaseFirestore mFirestore;
    String idHotel;

    TextView mHotel_Name;
    Button mBtn_SelectRoom;
    SliderView mSliderView;

    int[] slideImage = {R.drawable.hotel6, R.drawable.hotel7, R.drawable.hotel8, R.drawable.hotel9};

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
        mBtn_SelectRoom = view.findViewById(R.id.hotel_select_room);
        mBtn_SelectRoom.setOnClickListener(this);
        mSliderView = view.findViewById(R.id.imageSlider);

        getDataFromFireBase();
        showImageSlider();

    }

    private void showImageSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(slideImage);
        mSliderView.setSliderAdapter(sliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        mSliderView.startAutoCycle();
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

    /*
    @Override
    public void onPause() {
        super.onPause();
        HotielFragment hotielFragment = new HotielFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, hotielFragment).commit();

    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_select_room:
                selectRoom();
                break;
        }
    }

    private void selectRoom() {
        ShowAllRoomFragment showAllRoomFragment = new ShowAllRoomFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, showAllRoomFragment).commit();
    }

    private void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                HotielFragment fragment=new HotielFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment ,fragment).commit();
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity() , callback);

    }
}