package com.kemet.kemetapp.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kemet.kemetapp.Adapter.SliderAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.OrderRoomModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.widget.Debug.getLocation;

public class TourGideOrderFragment extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 200;
    private SliderView mSliderView;
    private AutoCompleteTextView TextInputLayout;
    private EditText mUserName;
    private LinearLayout mTackPhoto, mSelectStartDate, mSelectEndDate, mTackLocation;
    private Button mUploadOrder_btn;
    private ProgressBar mPrograssPar;


    private int[] slideImage = {R.drawable.car1, R.drawable.hotel7, R.drawable.c2, R.drawable.c4};
    private int cameraCode = 210;
    private String mName, mNationality, mImagePass, mStatDate, mEndDate;
    private Uri resultUri;
    String startLocation;

    FirebaseFirestore mFirebaseFirestore;
    private StorageReference storageReference;

    LocationManager locationManager = null;
    com.google.android.gms.location.LocationRequest locationRequest;


    public TourGideOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tour_gide_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniView(view);

    }

    private void iniView(View view) {
        mSliderView = view.findViewById(R.id.imageSlider_tour);
        TextInputLayout = view.findViewById(R.id.spinnerView_tour);
        mUserName = view.findViewById(R.id.enterName_tour);
        mTackPhoto = view.findViewById(R.id.tackPhoto_tour);
        mTackPhoto.setOnClickListener(this);
        mSelectStartDate = view.findViewById(R.id.selectStartData_t);
        mSelectStartDate.setOnClickListener(this);
        mSelectEndDate = view.findViewById(R.id.selectEndData_t);
        mSelectEndDate.setOnClickListener(this);
        mUploadOrder_btn = view.findViewById(R.id.btn_select_tour);
        mUploadOrder_btn.setOnClickListener(this);
        mTackLocation = view.findViewById(R.id.tackUserLocation);
        mTackLocation.setOnClickListener(this);
        mPrograssPar = view.findViewById(R.id.progras_tour);


        mFirebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        showImageSlider();
        showSpinner();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tackPhoto_tour:
                checkPermission();
                break;
            case R.id.selectStartData_t:
                selectStartDate();
                break;
            case R.id.selectEndData_t:
                selectEndDate();

                break;

            case R.id.btn_select_tour:
                uploadData();
                break;

            case R.id.tackUserLocation:
                getCurrentLocation();
                break;
        }
    }


    private void showImageSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(slideImage);
        mSliderView.setSliderAdapter(sliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        mSliderView.startAutoCycle();
    }


    private void showSpinner() {

        ArrayList<String> mList = new ArrayList<>();

        mList.add("Egypt");
        mList.add("Belize");
        mList.add("Argentina");
        mList.add("Armenia");
        mList.add("Austria");
        mList.add("Germany");
        mList.add("India");
        mList.add("Japan");
        mList.add("Mexico");

        TextInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.cstoum_spiner);
                dialog.getWindow().setLayout(1000, 800);
                dialog.show();

                EditText enterNationality = dialog.findViewById(R.id.enterNationality);
                ListView mListNationality = dialog.findViewById(R.id.listNationality);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mList);
                mListNationality.setAdapter(arrayAdapter);

                enterNationality.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        arrayAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                mListNationality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mNationality = arrayAdapter.getItem(position);
                        dialog.dismiss();

                    }
                });
            }


        });


    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // to ask user to reade external storage
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                tackPassportPhoto();
            }
        }
    }


    private void tackPassportPhoto() {
        // Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
        CropImage.activity()
                .start(getContext(), this);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Toast.makeText(getContext(), "Photo Tacked Don", Toast.LENGTH_SHORT).show();

                uPloadImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private void uPloadImage(Uri resultUri) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userID = auth.getCurrentUser().getUid();
        StorageReference ref = storageReference.child("orderTourGidePass").child(userID + ".jpg");
        // Log.d("userId" , userID);
        ref.putFile(resultUri);

        ref.getDownloadUrl().addOnCompleteListener(task -> {
            Uri downloadUrl = task.getResult();
            mImagePass = downloadUrl.toString();
            Log.d("mImagepass", mImagePass);
        });

    }


    private void selectStartDate() {
        MaterialDatePicker.Builder materialBuilder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker materialDatePicker = materialBuilder.build();

        materialDatePicker.show(getActivity().getSupportFragmentManager(), "");


        materialDatePicker.addOnPositiveButtonClickListener(selection ->
                mStatDate = materialDatePicker.getHeaderText());


    }


    private void selectEndDate() {

        MaterialDatePicker.Builder materialBuilder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker materialDatePicker = materialBuilder.build();

        materialDatePicker.show(getActivity().getSupportFragmentManager(), "");


        materialDatePicker.addOnPositiveButtonClickListener(selection ->
                mEndDate = materialDatePicker.getHeaderText());

    }


    private void uploadData() {
        mName = mUserName.getText().toString();
        if (!mName.isEmpty() && mNationality != null && !mStatDate.isEmpty() && !mEndDate.isEmpty() && mImagePass != null && startLocation != null) {
            OrderRoomModel orderRoomModel = new OrderRoomModel(mName, mNationality, mStatDate, mEndDate, mImagePass, startLocation);
            mFirebaseFirestore.collection("UserInfTourGide").document().set(orderRoomModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Dialog dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.don_item);
                                dialog.setTitle("Order Created");
                                dialog.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        HomeFragment homeFragment = new HomeFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();
                                    }
                                }, 4000);
                            } else {
                                Toast.makeText(getActivity(), "check Your Internet", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


        } else {
            Toast.makeText(getActivity(), "Enter Your Data", Toast.LENGTH_SHORT).show();
        }
    }

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
                                mPrograssPar.getPaddingStart();
                                mPrograssPar.setVisibility(View.VISIBLE);
                                if (locationResult != null) {

                                    int index = locationResult.getLocations().size() - 1;
                                    startLocation = locationResult.getLocations().get(index).getLatitude() + "," + locationResult.getLocations().get(index).getLongitude();
                                    // endLocation = "29.98646413043888, 31.12975354178391";
                                    Toast.makeText(getContext(), "Location Don", Toast.LENGTH_SHORT).show();
                                    mPrograssPar.setVisibility(View.INVISIBLE);
                                    //Log.d("loca", startLocation);
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

}