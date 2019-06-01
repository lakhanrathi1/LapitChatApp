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

public class LoginActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    EditText mloginemail,mloginpassword;
    private ProgressDialog mlogprogress;
    Button mloginbutton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mtoolbar = (Toolbar)findViewById(R.id.loginToolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mlogprogress = new ProgressDialog(this);

        mloginemail = (EditText)findViewById(R.id.loginemailText);
        mloginpassword = (EditText)findViewById(R.id.loginpasswordText);
        mloginbutton = (Button)findViewById(R.id.loginButton);

        mloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginemail = mloginemail.getText().toString();
                String loginpassword = mloginpassword.getText().toString();

                if (!TextUtils.isEmpty(loginemail) || !TextUtils.isEmpty(loginpassword)){

                    mlogprogress.setTitle("Logging You");
                    mlogprogress.setMessage("Thank You For Patient");
                    mlogprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mlogprogress.setCanceledOnTouchOutside(false);
                    mlogprogress.show();

                    loginuser(loginemail,loginpassword);


                }

            }
        });
    }

    private void loginuser(String loginemail, String loginpassword) {

        mAuth.signInWithEmailAndPassword(loginemail,loginpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    mlogprogress.dismiss();
                    Intent logtomain = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(logtomain);
                    finish();
                }
                else{

                    mlogprogress.hide();
                    Toast.makeText(getApplicationContext(),"Could Not sign in",Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
