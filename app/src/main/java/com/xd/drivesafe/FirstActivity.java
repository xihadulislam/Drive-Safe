package com.xd.drivesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xd.drivesafe.Admin.AdminMainActivity;
import com.xd.drivesafe.Driver.MainDriverActivity;
import com.xd.drivesafe.Reporter.ReporterMainActivity;
import com.xd.drivesafe.User.UserMainActivity;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";
    String  idnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        SharedPreferences sharedPreferences = getSharedPreferences("identy", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("name") ) {
            idnt = sharedPreferences.getString("name",null);

        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){

            if (idnt.equals("user")){
                startActivity(new Intent(FirstActivity.this, UserMainActivity.class));
                finish();
            }
           else if (idnt.equals("admin")){
                startActivity(new Intent(FirstActivity.this, AdminMainActivity.class));
                finish();
            }else if (idnt.equals("reporter")){
                startActivity(new Intent(FirstActivity.this, ReporterMainActivity.class));
                finish();
            }else if (idnt.equals("driver")){
                startActivity(new Intent(FirstActivity.this, MainDriverActivity.class));
                finish();
            }
           else
            {
                Log.d(TAG, "onCreate: ");
            }

        }


        findViewById(R.id.buuuuuuuuuuuuuuuuuut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                finish();

            }
        });


    }
}