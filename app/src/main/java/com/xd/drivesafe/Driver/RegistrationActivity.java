package com.xd.drivesafe.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.xd.drivesafe.LoginActivity;
import com.xd.drivesafe.Models.Identity;
import com.xd.drivesafe.Models.UserModel;
import com.xd.drivesafe.R;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private int STORAGE_PERMISSION_CODE = 1;
    private Button Bregister, Bselestimage;
    private RadioGroup radioGroup;
    private RadioButton Rmale, Rfemale;
    private EditText Ename, Enid, Elicense, Eaddress, Ephone, Eownername, Eowneradress, Eownerphone, Enumberplate, Eemail, Epassword, Ebirthdate;
    private ImageView showphoto;

    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private Uri muri;
    private StorageTask mUploadTask;
    private ProgressDialog progressDialog;
    private String ImageUri;

    private FirebaseAuth firebaseAuth;

    private String name = null, nid = null, license = null, address = null, phone = null, ownername = null, owneradress = null, ownerphone = null, numberplate = null, email = null, password = null, birthdate = null, gender = null, qrcode = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        Bregister = findViewById(R.id.dregisterID);
        Bselestimage = findViewById(R.id.dselctimageID);
        radioGroup = findViewById(R.id.radiogroup);
        Rmale = findViewById(R.id.maleID);
        Rfemale = findViewById(R.id.femaleID);
        Ename = findViewById(R.id.dnameID);
        Enid = findViewById(R.id.dnidID);
        Elicense = findViewById(R.id.dlicenseID);
        Eaddress = findViewById(R.id.daddressID);
        Ephone = findViewById(R.id.dphoneID);
        Eownername = findViewById(R.id.downernameID);
        Eowneradress = findViewById(R.id.downeraddressID);
        Eownerphone = findViewById(R.id.downerPhoneId);
        Enumberplate = findViewById(R.id.dnumberplateID);
        Eemail = findViewById(R.id.demailId);
        Epassword = findViewById(R.id.dpasswordID);
        showphoto = findViewById(R.id.dimageID);
        Ebirthdate = findViewById(R.id.dbirthdateID);

        progressDialog = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();


        storageReference = FirebaseStorage.getInstance().getReference("userinfo");

        Bselestimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);


                } else {
                    requestStoragePermission();

                }

            }
        });


        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressDialog.setMessage("Please Wait...!");
                progressDialog.show();

                name = Ename.getText().toString().trim();
                nid = Enid.getText().toString().trim();
                license = Elicense.getText().toString().trim();
                address = Eaddress.getText().toString().trim();
                phone = Ephone.getText().toString().trim();
                ownername = Eownername.getText().toString().trim();
                owneradress = Eowneradress.getText().toString().trim();
                ownerphone = Eownerphone.getText().toString().trim();
                numberplate = Enumberplate.getText().toString().trim();
                email = Eemail.getText().toString().trim();
                password = Epassword.getText().toString().trim();
                birthdate = Ebirthdate.getText().toString().trim();

                if (name.isEmpty()) {
                    Ename.setError("Name required");
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            photoupload();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });


    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(RegistrationActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            muri = data.getData();
            Picasso.get().load(muri).fit().centerCrop().into(showphoto);

            Log.d(TAG, "onActivityResult: " + muri);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }


    private void photoupload() {
        if (muri != null) {

            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(muri));
            mUploadTask = fileReference.putFile(muri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    ImageUri = uri.toString();

                                    final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                    UserModel userModel = new UserModel(name, nid, license, address, phone, ownername, owneradress, ownerphone,
                                            numberplate, email, password, ImageUri, null, birthdate,
                                            "male", System.currentTimeMillis(), System.currentTimeMillis(), 500, userid, (float) 0.0);
                                    FirebaseFirestore.getInstance().collection("temp_userInfo").document(userid).set(userModel)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    Identity identity = new Identity("driver");

                                                    FirebaseFirestore.getInstance().collection("idnty").document(userid).set(identity);
                                                    progressDialog.dismiss();
                                                    Toast.makeText(RegistrationActivity.this, "success", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                                    Animatoo.animateSlideLeft(RegistrationActivity.this);
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();

                                        }
                                    });


                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            progressDialog.dismiss();
            Toast.makeText(RegistrationActivity.this, "No Photo Selected.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(this); //fire the slide left animation
    }


}

