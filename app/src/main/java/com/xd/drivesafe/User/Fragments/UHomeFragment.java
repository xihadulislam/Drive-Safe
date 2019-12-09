package com.xd.drivesafe.User.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xd.drivesafe.Adapters.AllincidentReporterAdapter;
import com.xd.drivesafe.Adapters.AllincidentUserAdapter;
import com.xd.drivesafe.Models.ReportModel;
import com.xd.drivesafe.R;
import com.xd.drivesafe.Reporter.AllincidentlistRActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UHomeFragment extends Fragment {


    public UHomeFragment() { }


    RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    List<ReportModel> reportModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_uhome, container, false);


        recyclerView = view.findViewById(R.id.userrecyID);

        progressDialog = new ProgressDialog(getActivity());


        progressDialog.show();

        FirebaseFirestore.getInstance().collection("Report").orderBy("createat", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    reportModelList = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult()){

                        ReportModel reportModel = doc.toObject(ReportModel.class);
                        reportModelList.add(reportModel);
                        reportModelList.add(reportModel);
                        reportModelList.add(reportModel);

                    }

                    progressDialog.dismiss();
                    AllincidentUserAdapter adapter = new AllincidentUserAdapter(getActivity(),reportModelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);

                }

            }
        });








        return view;
    }

}
