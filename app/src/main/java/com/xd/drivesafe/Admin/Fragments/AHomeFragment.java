package com.xd.drivesafe.Admin.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xd.drivesafe.Admin.CreateAdminActivity;
import com.xd.drivesafe.Admin.CreateReporterActivity;
import com.xd.drivesafe.Admin.RequestDriverActivity;
import com.xd.drivesafe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AHomeFragment extends Fragment implements View.OnClickListener {
    public AHomeFragment() {
    }


    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG = "QRCScanner-MainActivity";

    LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10, layout11, layout12;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ahome, container, false);


        layout1 = view.findViewById(R.id.scannerlinID);
         layout2 = view.findViewById(R.id.linnidverificationId);
        layout3 = view.findViewById(R.id.smsalertlinID);
        layout4 = view.findViewById(R.id.allincidentlinId);
        layout5 = view.findViewById(R.id.repotrterslinID);
        layout6 = view.findViewById(R.id.adminlistID);
        layout7 = view.findViewById(R.id.bestdriverlinID);
        layout8 = view.findViewById(R.id.createadminlinID);
        layout9 = view.findViewById(R.id.alldriverlinID);
        layout10 = view.findViewById(R.id.createreporterlinID);
        layout11 = view.findViewById(R.id.pendingdriversID);
        layout12 = view.findViewById(R.id.rejectdriverliID);


        layout1.setOnClickListener(AHomeFragment.this);
        layout2.setOnClickListener(AHomeFragment.this);
        layout3.setOnClickListener(AHomeFragment.this);
        layout4.setOnClickListener(AHomeFragment.this);
        layout5.setOnClickListener(AHomeFragment.this);
        layout6.setOnClickListener(AHomeFragment.this);
        layout7.setOnClickListener(AHomeFragment.this);
        layout8.setOnClickListener(AHomeFragment.this);
        layout9.setOnClickListener(AHomeFragment.this);
        layout10.setOnClickListener(AHomeFragment.this);
        layout11.setOnClickListener(AHomeFragment.this);
        layout12.setOnClickListener(AHomeFragment.this);


        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == layout1) {
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions
                    .request(Manifest.permission.CAMERA) // ask single or multiple permission once
                    .subscribe(granted -> {
                        if (granted) {
                            Intent i = new Intent(getActivity(), QrCodeActivity.class);
                            startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                        } else {
                            // At least one permission is denied
                        }
                    });

      }


        else if (v == layout3) {

        } else if (v == layout4) {

        } else if (v == layout5) {

        } else if (v == layout6) {

        } else if (v == layout7) {

        } else if (v == layout8) {
            startActivity(new Intent(getActivity(), CreateAdminActivity.class));


        } else if (v == layout9) {

        } else if (v == layout10) {

            startActivity(new Intent(getActivity(), CreateReporterActivity.class));

        } else if (v == layout11) {

            Intent intent = new Intent(getActivity(), RequestDriverActivity.class);
            intent.putExtra("key","app");
            startActivity(intent);

        } else if (v == layout12) {
            Intent intent = new Intent(getActivity(), RequestDriverActivity.class);
            intent.putExtra("key","reject");
            startActivity(intent);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Log.d(LOGTAG, "COULD NOT GET A GOOD RESULT.");
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (result != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d(LOGTAG, "Have scan result in your app activity :" + result);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //  startActivity(new Intent(getActivity(), ReportPageActivity.class));

                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
    }


}