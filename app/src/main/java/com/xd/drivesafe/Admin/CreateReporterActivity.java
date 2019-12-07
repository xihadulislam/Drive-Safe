package com.xd.drivesafe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.xd.drivesafe.Models.Identity;
import com.xd.drivesafe.Models.ReporterDataModel;
import com.xd.drivesafe.R;

public class CreateReporterActivity extends AppCompatActivity {


    private MaterialEditText Eusername,Eemail,Epass;

    private Button button;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reporter);

        getSupportActionBar().hide();

        Eusername = findViewById(R.id.createusernamerepoID);
        Eemail = findViewById(R.id.createemailrepoID);
        Epass = findViewById(R.id.createpassrepoID);

        progressDialog = new ProgressDialog(this);

        button = findViewById(R.id.createbuttonrepoID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please Wait...!");
                progressDialog.show();

                String username = Eusername.getText().toString().trim();
                String email = Eemail.getText().toString().trim();
                String pass = Epass.getText().toString().trim();


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                String userid = task.getResult().getUser().getUid();


                                ReporterDataModel reporterDataModel = new ReporterDataModel(username,email,userid);

                                FirebaseFirestore.getInstance().collection("Reporterinfo")
                                        .document(userid).set(reporterDataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        Identity identity = new Identity("reporter");

                                        FirebaseFirestore.getInstance().collection("idnty").document(userid).set(identity);

                                        startActivity(new Intent(CreateReporterActivity.this,AdminMainActivity.class));
                                        finish();

                                    }
                                });




                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(CreateReporterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });









    }
}
