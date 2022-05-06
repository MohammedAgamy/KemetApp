package com.kemet.kemetapp.ui.fragment;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kemet.kemetapp.pojo.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class PresenterHome {
    DataHome dataHome;
    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();


    public PresenterHome(DataHome dataHome) {
        this.dataHome = dataHome;
    }

    public ArrayList<HomeModel> addData() {

        ArrayList<HomeModel> mList = new ArrayList<>();

        //get data from fireStore
        mFireStore.collection("HomeRecyclerr").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : list) {
                            HomeModel homeModel = snapshot.toObject(HomeModel.class);
                             mList.add(homeModel);
                             Log.d("dataFromFire" , String.valueOf((homeModel.getName())));
                        }

                    }

                });

         return mList ;
    }


    public void getData() {
        dataHome.getHomeData(addData());
        Log.d("datFromFireBase" , String.valueOf(addData())) ;}
}
