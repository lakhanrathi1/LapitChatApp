package com.example.lakhan.lapitchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting extends AppCompatActivity {

    private DatabaseReference mdatabaseref;
    private FirebaseUser current;

    private CircleImageView mimage;
    private TextView mname;
    private TextView mstatus;
    private Button chStatusButton;

    private Button chImageButton;
  //  private ProgressDialog mprogessbar;

    private ProgressDialog mprogress;

    private StorageReference mImageStorage;

    private static final int Gallery_picer = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mname =(TextView)findViewById(R.id.nameText);
        mstatus = (TextView)findViewById(R.id.statusText);
        mimage = (CircleImageView)findViewById(R.id.settingImage);

        current = FirebaseAuth.getInstance().getCurrentUser();
        mImageStorage = FirebaseStorage.getInstance().getReference();

        String currten_uid = current.getUid();

        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("User").child(currten_uid);
        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String get_name = dataSnapshot.child("Name").getValue().toString();
                String get_status = dataSnapshot.child("Status").getValue().toString();
                String get_image = dataSnapshot.child("Image").getValue().toString();
                String get_thumbimg = dataSnapshot.child("thumb_img").getValue().toString();


                mname.setText(get_name);
                mstatus.setText(get_status);
                Picasso.with(Setting.this).load(get_image).into(mimage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        chStatusButton = (Button)findViewById(R.id.statusButton);
        chStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent settingtostatus = new Intent(getApplicationContext(),Status.class);
                startActivity(settingtostatus);
               // finish();


            }
        });


        chImageButton = (Button)findViewById(R.id.imageButton);
        chImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(Setting.this);
/*
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);


                startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),Gallery_picer);
*/

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_picer && requestCode == RESULT_OK){

            Uri imageview = data.getData();
            CropImage.activity(imageview)
                    .setAspectRatio(1,1)
                    .start(this);



        }
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {


                        mprogress = new ProgressDialog(Setting.this);
                        mprogress.setTitle("Uploading");
                        mprogress.setMessage("Plase Wait While Updating your image");
                        mprogress.setCanceledOnTouchOutside(false);
                        mprogress.show();


                        Uri resultUri = result.getUri();

                        String Curremt_user_id =  current.getUid();

                        StorageReference filepath = mImageStorage.child("profile_photo").child(Curremt_user_id + ".jpg");
                        filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful()){

                                    //noinspection VisibleForTests
                                    String Download_uri = task.getResult().getDownloadUrl().toString();
                                    mdatabaseref.child("Image").setValue(Download_uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mprogress.dismiss();

                                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();


                                        }
                                    });
         }else
                                {

                                    Toast.makeText(getApplicationContext()," not Success",Toast.LENGTH_LONG).show();
                                    mprogress.dismiss();
                                }

                            }
                        });


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                }
    }


    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
