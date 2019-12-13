package com.xd.drivesafe.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.xd.drivesafe.Adapters.AllincidentReporterAdapter;
import com.xd.drivesafe.Models.ReportModel;
import com.xd.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

public class MyIncidentActivity extends AppCompatActivity {

    RecyclerView recyclerView2;
    List<ReportModel> reportModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_incident);

        recyclerView2  = findViewById(R.id.OwnRecyID);

        setRecyview2();
    }


    private void setRecyview2() {


        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseFirestore.getInstance().collection("Report").whereEqualTo("userid",userid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    reportModelList = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()){

                        ReportModel reportModel = doc.toObject(ReportModel.class);
                        reportModelList.add(reportModel);

                    }
                    AllincidentReporterAdapter adapter = new AllincidentReporterAdapter(MyIncidentActivity.this,reportModelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyIncidentActivity.this);
                    recyclerView2.setLayoutManager(linearLayoutManager);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setAdapter(adapter);
                }

            }
        });

    }

}

