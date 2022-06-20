package com.kemet.kemetapp.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kemet.kemetapp.Adapter.HomeAdapter;
import com.kemet.kemetapp.Adapter.TypeTorusimAdapter;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.HomeModel;
import com.kemet.kemetapp.pojo.TourismModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.HomeOnClick, TypeTorusimAdapter.OnClickType {

    //view
    RecyclerView mHomeRecycler;
    //Model
    List<HomeModel> mList;
    ArrayList<TourismModel> mTourList;
    HomeAdapter mHomeAdapter;
    PresenterHome presenterHome;
    //Firebase
    FirebaseFirestore mFireStore;
    //
    String getName, getId;
    Dialog dialog;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniView(view);
        getDataFromFirebase();
        //presenterHome.getData();
    }


    private void iniView(View view) {
        //View
        mHomeRecycler = view.findViewById(R.id.recyclerHome);
        //list
        mList = new ArrayList<>();
        mTourList = new ArrayList<>();

        //Firebase
        mFireStore = FirebaseFirestore.getInstance();
        //mvp
        //presenterHome = new PresenterHome(this);
        // getDataFromFirebase();

    }


    //method get data from firebase
    private void getDataFromFirebase() {

        //get data from fireStore
        mFireStore.collection("HomeRecyclerr").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            HomeModel homeModel = snapshot.toObject(HomeModel.class);
                            mList.add(homeModel);
                        }
                        mHomeAdapter.notifyDataSetChanged();
                    }
                });

        mHomeAdapter = new HomeAdapter(getActivity(), mList, this);
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRecycler.setAdapter(mHomeAdapter);
        mHomeAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick_Home(String s) {
        if (s.equalsIgnoreCase("0")) {

            HotielFragment hotielFragment = new HotielFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, hotielFragment).commit();
            //Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_hotielFragment);

        }
        if (s.equalsIgnoreCase("1")) {
            TourGuideFragment tourGuideFragment = new TourGuideFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, tourGuideFragment).commit();
            //Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_tourGuideFragment);

        }

        if (s.equalsIgnoreCase("2")) {
            openDialog();
          /*  CivilizationFragment civilizationFragment = new CivilizationFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, civilizationFragment).commit();*/
            //Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_civilizationFragment);

        }
        if (s.equalsIgnoreCase("3")) {
            CarFragment carFragment = new CarFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, carFragment).commit();
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_carFragment);

        }

    }

    private void openDialog() {

        //create dialog
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_tourism);
        dialog.getWindow().setLayout(1000, 800);
        dialog.show();

        RecyclerView mLisTousrism = dialog.findViewById(R.id.listTourism);
        // ArrayList<TourismModel> typeList= new ArrayList<>();
        TypeTorusimAdapter typeTorusimAdapter = new TypeTorusimAdapter(getActivity(), mTourList, this);

        mLisTousrism.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLisTousrism.setAdapter(typeTorusimAdapter);
        mHomeAdapter.notifyDataSetChanged();


    }

    /////////////////////////////////////////////////////////////

    @Override
    public void onStart() {
        //presenterHome.getData();
        super.onStart();
        mFireStore.collection("TypeOfTourism").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            TourismModel tourismModel = snapshot.toObject(TourismModel.class);
                            //getName = tourismModel.getName();
                            getId = tourismModel.getId();
                            mTourList.add(tourismModel);

                        }
                    }
                });

    }

 
    @Override
    public void onClickTypeItem(String id) {
        CivilizationFragment civilizationFragment = new CivilizationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("typeId", id);
        Log.d("idHome", String.valueOf(id));
        civilizationFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, civilizationFragment).commit();
        dialog.dismiss();
    }

/*
    @Override
    public void getHomeData(ArrayList<HomeModel> homeModels) {
        mList = homeModels ;
        Log.d("datFromFireBase22+", String.valueOf(mList));
        mHomeAdapter = new HomeAdapter(getActivity(), mList, this);
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRecycler.setAdapter(mHomeAdapter);
        mHomeAdapter.notifyDataSetChanged();
*/
}

   /* private void getDataFromFirebase() {
        //get data from fireStore
        presenterHome.getData();
        mHomeAdapter = new HomeAdapter(getActivity(), mList, this);
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRecycler.setAdapter(mHomeAdapter);
        mHomeAdapter.notifyDataSetChanged();
    }*/





       /* ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_list_item_1, mTourList);
        mLisTousrism.setAdapter(arrayAdapter);

        mLisTousrism.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CivilizationFragment civilizationFragment=new CivilizationFragment();
                Bundle bundle=new Bundle();
                bundle.putString("typeId" ,getId);
                Log.d("idHome" , String.valueOf(id));
                civilizationFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,civilizationFragment).commit();
            }
        });*/