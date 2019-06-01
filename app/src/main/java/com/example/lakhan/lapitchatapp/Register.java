package com.example.lakhan.lapitchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText mName,mPassword,mEmail;
    Button mCreateButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Toolbar mtoolbar;
    private  ProgressDialog mregprogress;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mtoolbar = (Toolbar)findViewById(R.id.registerToolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();


        mName = (EditText)findViewById(R.id.nameText);
        mEmail = (EditText)findViewById(R.id.loginemailText);
        mPassword = (EditText)findViewById(R.id.loginpasswordText);
        mCreateButton = (Button)findViewById(R.id.loginButton);
        mregprogress = new ProgressDialog(this);


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String display_name = mName.getText().toString();
                String display_email = mEmail.getText().toString();
                String display_password = mPassword.getText().toString();

                if (!TextUtils.isEmpty(display_email) || !TextUtils.isEmpty(display_name)||!TextUtils.isEmpty(display_password)){

                    mregprogress.setTitle("Registering User");
                    mregprogress.setMessage("Thank You For Patient");
                    mregprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mregprogress.setCanceledOnTouchOutside(false);
                    mregprogress.show();

                    register(display_name,display_email,display_password);


                }


            }
        });


    }

    private void register(final String display_name, String display_email, String display_password) {

        mAuth.createUserWithEmailAndPassword(display_email, display_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mdatabase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
                            HashMap<String,String> usermap = new HashMap<String, String>();
                            usermap.put("Name",display_name);
                            usermap.put("Image","default");
                            usermap.put("Status","Hey I am using Chit-Chat App");
                            usermap.put("thumb_img","default");

                            mdatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        mregprogress.dismiss();
                                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();

                                    }
                                }
                            });



                            //Log.d(T  AG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        }
                        else {
                            mregprogress.hide();

                            Toast.makeText(Register.this, "Something is Wrong", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }




}
