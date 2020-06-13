package com.xd.drivesafe.Admin.Fragments;


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
import com.google.firebase.firestore.QuerySnapshot;
import com.xd.drivesafe.Adapters.ComentAdapter;
import com.xd.drivesafe.Adapters.NotificationAdapter;
import com.xd.drivesafe.Models.NotificationModel;
import com.xd.drivesafe.R;
import com.xd.drivesafe.User.CommentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ANotificationFragment extends Fragment {


    public ANotificationFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;


    List<NotificationModel> notificationModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_anotification, container, false);


        recyclerView   = view.findViewById(R.id.notificationrecy1);


        FirebaseFirestore.getInstance().collection("Notificationsadmin").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            notificationModelList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()){

                                NotificationModel notificationModel = doc.toObject(NotificationModel.class);
                                notificationModelList.add(notificationModel);
                            }

                            if (notificationModelList.size()==0){

                                view.findViewById(R.id.nonotificA).setVisibility(View.VISIBLE);
                            }

                            NotificationAdapter adapter = new NotificationAdapter(getActivity(),notificationModelList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);

                        }

                    }
                });


        return view;


    }

}
