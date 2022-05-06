package com.kemet.kemetapp.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.drm.DrmStore;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
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
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;

public class RoomOrderFragment extends Fragment implements View.OnClickListener {
    private SliderView mSliderView;
    private AutoCompleteTextView TextInputLayout;
    private EditText mUserName;
    private LinearLayout mTackPhoto, mSelectStartDate, mSelectEndDate;
    private Button mUploadOrder_btn;

    int[] slideImage = {R.drawable.hotel6, R.drawable.hotel7, R.drawable.hotel8, R.drawable.hotel9};
    int cameraCode = 220;
    String mName, mNationality, mImagePass, mStatDate, mEndDate;
    Uri resultUri;

    FirebaseFirestore mFirebaseFirestore;
    private StorageReference storageReference;

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
        TextInputLayout = view.findViewById(R.id.spinnerView);
        mUserName = view.findViewById(R.id.enterName_roomOrder);
        mTackPhoto = view.findViewById(R.id.tackPhoto);
        mTackPhoto.setOnClickListener(this);
        mSelectStartDate = view.findViewById(R.id.selectStartData);
        mSelectStartDate.setOnClickListener(this);
        mSelectEndDate = view.findViewById(R.id.selectEndData);
        mSelectEndDate.setOnClickListener(this);
        mUploadOrder_btn = view.findViewById(R.id.btn_select_room);
        mUploadOrder_btn.setOnClickListener(this);


        mFirebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        showImageSlider();
        showSpinner();
        onBack();


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
        Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
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
                uPloadImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    private void uPloadImage(Uri resultUri) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String userID=auth.getCurrentUser().getUid();
        StorageReference ref = storageReference.child("RoomOrder").child(userID+ ".jpg");
       // Log.d("userId" , userID);
        ref.putFile(resultUri);


       ref.getDownloadUrl().addOnCompleteListener(task -> {
           Uri downloadUrl=  task.getResult();
            mImagePass =  downloadUrl.toString();
            Log.d("mImagepass" , mImagePass);
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
        if (!mName.isEmpty() && mNationality != null && !mStatDate.isEmpty() && !mEndDate.isEmpty() && mImagePass != null) {
            OrderRoomModel orderRoomModel = new OrderRoomModel(mName, mNationality,mStatDate, mEndDate,mImagePass);
            mFirebaseFirestore.collection("UserInfoRoom").document().set(orderRoomModel)
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
                                        ShowAllRoomFragment roomFragment = new ShowAllRoomFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, roomFragment).commit();
                                        dialog.dismiss();
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

    private void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                ShowAllRoomFragment fragment = new ShowAllRoomFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tackPhoto:
                checkPermission();
                break;
            case R.id.selectStartData:
                selectStartDate();
                break;
            case R.id.selectEndData:
                selectEndDate();
                break;
            case R.id.btn_select_room:
                uploadData();
                break;
        }
    }
}