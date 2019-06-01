package com.example.lakhan.lapitchatapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Status extends AppCompatActivity {

    private Toolbar statusToolbar;
    private Button statuschangeButton;
    private TextView statusChange;
    private DatabaseReference statusDatabase;
    private FirebaseUser statusFirebase;
    private ProgressDialog mprogessbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        statusFirebase = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = statusFirebase.getUid();
        statusDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(current_uid);

        statusToolbar=(Toolbar)findViewById(R.id.status_tool_bar);
        setSupportActionBar(statusToolbar);
        getSupportActionBar().setTitle("Change Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statusChange = (TextView)findViewById(R.id.changestatus);
        statuschangeButton = (Button)findViewById(R.id.chagestatusButton);
        statuschangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprogessbar = new ProgressDialog(Status.this);
                mprogessbar.setTitle("Changing");
                mprogessbar.setMessage("Please Wait While we Update your Status");
                mprogessbar.show();
                String new_status = statusChange.getText().toString();

                statusDatabase.child("Status").setValue(new_status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){


                            mprogessbar.dismiss();

                        }
                        else {

                            Toast.makeText(getApplicationContext(),"There is mistake",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
