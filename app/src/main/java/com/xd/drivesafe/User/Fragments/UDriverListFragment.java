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
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.xd.drivesafe.Adapters.UserDriversAdapter;
import com.xd.drivesafe.Models.UserModel;
import com.xd.drivesafe.R;
import com.xd.drivesafe.User.CommentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UDriverListFragment extends Fragment {


    public UDriverListFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    List<UserModel> userModelList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_udriver_list, container, false);

        recyclerView = view.findViewById(R.id.driversrecyId);


        ProgressDialog pd = new ProgressDialog(getActivity());

        pd.show();

        FirebaseFirestore.getInstance().collection("approved_Drivers").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            userModelList.clear();
                            for (DocumentSnapshot doc : task.getResult()){

                                UserModel userModel = doc.toObject(UserModel.class);
                                userModelList.add(userModel);
                                userModelList.add(userModel);
                                userModelList.add(userModel);

                            }

                            pd.dismiss();
                            UserDriversAdapter userDriversAdapter = new UserDriversAdapter(getActivity(),userModelList);

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(userDriversAdapter);

                        }
                        else
                        {
                            pd.dismiss();
                        }

                    }
                });







        return view;
    }

}
