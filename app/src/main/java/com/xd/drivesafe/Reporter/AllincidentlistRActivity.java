package com.xd.drivesafe.Reporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xd.drivesafe.Adapters.AllincidentReporterAdapter;
import com.xd.drivesafe.Models.ReportModel;
import com.xd.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

public class AllincidentlistRActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    List <ReportModel> reportModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allincidentlist_r);


        recyclerView = findViewById(R.id.allincidentID);
        progressDialog = new ProgressDialog(this);


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

                    AllincidentReporterAdapter adapter = new AllincidentReporterAdapter(AllincidentlistRActivity.this,reportModelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllincidentlistRActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);

                    recyclerView.setAdapter(adapter);

                }

            }
        });








    }
}
