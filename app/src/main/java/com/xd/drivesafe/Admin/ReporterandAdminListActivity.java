package com.xd.drivesafe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xd.drivesafe.Adapters.AdminRepoterAdapter;
import com.xd.drivesafe.Models.ReporterDataModel;
import com.xd.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

public class ReporterandAdminListActivity extends AppCompatActivity {




    ListView listView;

    List<ReporterDataModel> listModelList=new ArrayList<>();

    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporterand_admin_list);


        listView = findViewById(R.id.mainlistId23);
        progressDialog = new ProgressDialog(this);


        Intent intent = getIntent();

        String val = intent.getStringExtra("key");


        if (val.equals("admin")){

            adminlist();
        }
        else if (val.equals("repo")){

            reportlist();

        }








    }

    private void reportlist() {

        progressDialog.setMessage("please wait...");
        progressDialog.show();
        Query query = FirebaseFirestore.getInstance().collection("Reporterinfo");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    for (DocumentSnapshot doc : task.getResult()){

                        ReporterDataModel userModel = doc.toObject(ReporterDataModel.class);

                        listModelList.add(userModel);

                    }

                    progressDialog.dismiss();

                    AdminRepoterAdapter adapter = new AdminRepoterAdapter(ReporterandAdminListActivity.this,listModelList);
                    listView.setAdapter(adapter);

                }

            }
        });

    }


    private void adminlist() {

        progressDialog.setMessage("please wait...");
        progressDialog.show();
        Query query = FirebaseFirestore.getInstance().collection("admininfo");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    for (DocumentSnapshot doc : task.getResult()){

                        ReporterDataModel userModel = doc.toObject(ReporterDataModel.class);

                        listModelList.add(userModel);

                    }

                    progressDialog.dismiss();

                    AdminRepoterAdapter adapter = new AdminRepoterAdapter(ReporterandAdminListActivity.this,listModelList);
                    listView.setAdapter(adapter);

                }

            }
        });


    }
}
