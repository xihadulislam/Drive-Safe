package com.xd.drivesafe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xd.drivesafe.R;

public class SendSMSActivity extends AppCompatActivity {


    private Button btnSendSMS;
    private EditText etPhoneNum, etMessage;
    private final static int REQUEST_CODE_PERMISSION_SEND_SMS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        etPhoneNum = findViewById(R.id.etPhoneNum);
        etMessage =findViewById(R.id.etMsg);
        btnSendSMS =  findViewById(R.id.btnSendSMS);

        btnSendSMS.setEnabled(false);

        // setting List View for Messages

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            btnSendSMS.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(SendSMSActivity.this, new String[]{
                    (Manifest.permission.SEND_SMS)}, REQUEST_CODE_PERMISSION_SEND_SMS);
        }

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etMessage.getText().toString();
                String phoneNum = etPhoneNum.getText().toString();

                SmsManager smsMan = SmsManager.getDefault();
                smsMan.sendTextMessage(phoneNum, null, msg, null, null);
                Toast.makeText(SendSMSActivity.this,
                        "SMS send to " + phoneNum, Toast.LENGTH_LONG).show();
            }
        });
    }



    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_SEND_SMS:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btnSendSMS.setEnabled(true);
                }
                break;
        }


    }

}


