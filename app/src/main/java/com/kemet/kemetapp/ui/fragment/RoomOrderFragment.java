package com.kemet.kemetapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kemet.kemetapp.Adapter.SliderAdapter;
import com.kemet.kemetapp.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

public class RoomOrderFragment extends Fragment {
    SliderView mSliderView;

    int[] slideImage = {R.drawable.hotel6, R.drawable.hotel7, R.drawable.hotel8, R.drawable.hotel9};
    public RoomOrderFragment() {
        // Required empty public constructor
    }
  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_order, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSliderView = view.findViewById(R.id.imageSlider_r);


        showImageSlider();
    }

    private void showImageSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(slideImage);
        mSliderView.setSliderAdapter(sliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        mSliderView.startAutoCycle();
    }
}