package com.xd.drivesafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.xd.drivesafe.Admin.AdminMainActivity;
import com.xd.drivesafe.Driver.MainDriverActivity;
import com.xd.drivesafe.Driver.RegistrationActivity;
import com.xd.drivesafe.Reporter.ReporterMainActivity;
import com.xd.drivesafe.User.UserMainActivity;
import com.xd.drivesafe.User.UserRegActivity;

public class LoginActivity extends AppCompatActivity {


    private Button button;
    MaterialEditText email,pass;


    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().hide();


        email = findViewById(R.id.loginemailId);
        pass = findViewById(R.id.loginPassId);
        button = findViewById(R.id.loginId);
        progressDialog = new ProgressDialog(this);


        findViewById(R.id.bcamedriverID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });


        findViewById(R.id.texttttt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, UserRegActivity.class));

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Email = email.getText().toString().trim();
                final String Pass = pass.getText().toString().trim();

                if (Email.isEmpty()){
                    email.setError("Email Required");
                }
                else if (Pass.isEmpty()){
                    pass.setError("Password Required");
                }
                else {
                    progressDialog.setMessage("please wait...");
                    progressDialog.show();

                    firebaseAuth = FirebaseAuth.getInstance();

                    firebaseAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                             String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                FirebaseFirestore.getInstance().collection("idnty").document(user).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                String name = task.getResult().get("name").toString();

                                                if (name.equals("user")){
                                                    progressDialog.dismiss();
                                                    storedata(name);
                                                    startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                                                    finish();
                                                }
                                                if (name.equals("admin")){
                                                    progressDialog.dismiss();
                                                    storedata(name);
                                                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                                    finish();
                                                } if (name.equals("reporter")){
                                                    progressDialog.dismiss();
                                                    storedata(name);
                                                    startActivity(new Intent(LoginActivity.this, ReporterMainActivity.class));
                                                    finish();
                                                } if (name.equals("driver")){
                                                    progressDialog.dismiss();
                                                    storedata(name);
                                                    startActivity(new Intent(LoginActivity.this, MainDriverActivity.class));
                                                    finish();
                                                }
                                            }
                                        });

                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
            }
        });


    }


    private void storedata(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("identy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.commit();
    }


}
