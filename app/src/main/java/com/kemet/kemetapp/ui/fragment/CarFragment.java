package com.kemet.kemetapp.ui.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kemet.kemetapp.Adapter.CarAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.CarModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CarFragment extends Fragment implements CarAdapter.OnClickCar{
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    private CarAdapter adapter;
    private List<CarModel> list;

    public CarFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =view. findViewById(R.id.recyclerView);
        progressBar= view.findViewById(R.id.Progress);
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));
        list=new ArrayList<>();
        adapter=new CarAdapter(getActivity(),list,this);
        recyclerView.setAdapter(adapter);
        getData();
        onBack();

    }
    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        firebaseFirestore.collection("Car")
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {

                    if (error==null){
                        if (value==null){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),"value is null",Toast.LENGTH_SHORT).show();

                        }else {
                            for (DocumentChange documentChange: value.getDocumentChanges()){
                                CarModel model=documentChange.getDocument().toObject(CarModel.class);
                                list.add(model);
                                adapter.notifyDataSetChanged();

                            }
                            progressBar.setVisibility(View.GONE);
                        }

                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @Override
    public void onItemClick() {
        CarOrderFragment carOrderFragment=new CarOrderFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, carOrderFragment).commit();
    }



    private void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                HomeFragment carFragment=new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment ,carFragment).commit();
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity() , callback);

    }
}