package com.kemet.kemetapp.ui.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherKt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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

public class civilizationInformationFragment extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 2000;
    FirebaseFirestore mFirestore;
    DocumentReference documentReference;
    String mIdCiv, mAboutPaceFromFireBase;

    TextView mCiv_Name, mAboutPlace;
    SliderView mSliderView;
    LinearLayout mBtnGet_Location;
    String startLocation, endLocation;
    LocationManager locationManager = null;


    //FireBase
    com.google.android.gms.location.LocationRequest locationRequest;

    int[] slideImage = {R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4};

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
        assert bundle != null;
        mIdCiv = (String) bundle.get("civilizationFragment");
        //firebase
        mFirestore = FirebaseFirestore.getInstance();
        //View
        mCiv_Name = view.findViewById(R.id.civ_name_inf);
        mSliderView = view.findViewById(R.id.imageSlider_civ);
        mAboutPlace = view.findViewById(R.id.civ_text_inf);
        mBtnGet_Location = view.findViewById(R.id.civ_location_inf);
        mBtnGet_Location.setOnClickListener(this);
        getDataFromFireBase();
        showImageSlider();
        onBack();


    }

    private void showImageSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(slideImage);
        mSliderView.setSliderAdapter(sliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        mSliderView.startAutoCycle();
    }

    private void getDataFromFireBase() {
        mFirestore.collection("CivilizationInformation")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                            String id = documentSnapshots.getString("idCiv");
                            String civName = documentSnapshots.getString("name");
                            mAboutPaceFromFireBase = documentSnapshots.getString("aboutPlace");
                            endLocation = documentSnapshots.getString("location");

                            if (mIdCiv.equalsIgnoreCase(id)) {
                                mCiv_Name.setText(civName);
                                mAboutPlace.setText(mAboutPaceFromFireBase);
                            }

                            Log.d("taskTag", documentSnapshots.getId());
                        }
                    } else {
                        Log.d("taskTag", String.valueOf(task.getException()));

                    }

                });
    }


    //Location
    public void getCurrentLocation() {
        locationRequest = com.google.android.gms.location.LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (isGPSEnabled()) {
                LocationServices.getFusedLocationProviderClient(getActivity())
                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult != null) {
                                    int index = locationResult.getLocations().size() - 1;
                                    startLocation = locationResult.getLocations().get(index).getLatitude() + "," + locationResult.getLocations().get(index).getLongitude();
                                    // endLocation = "29.98646413043888, 31.12975354178391";
                                    Log.d("loca", startLocation);
                                    showLocation(startLocation, endLocation);
                                } else {
                                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

                                }
                            }


                        }, Looper.getMainLooper());
            } else {
                turnOnGPS();
            }

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isGPSEnabled() {
        boolean isEnabled = false;
        if (locationManager == null) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getActivity())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(getActivity(), "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(getActivity(), 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private void showLocation(String startLocation, String endLocation) {


        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + startLocation + "/" + endLocation);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } catch (ActivityNotFoundException exception) {
            Uri uri = Uri.parse("https://play.google.com/store/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_location_inf:
                getCurrentLocation();
                break;
        }
    }

    private void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                CivilizationFragment civilizationFragment = new CivilizationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("idHotel", "null");
                civilizationFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, civilizationFragment).commit();
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

    }

}