package com.example.lakhan.lapitchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button mStart;
    Button mloginstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mStart = (Button)findViewById(R.id.ButtonStart);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(),Register.class);
                startActivity(register);
                finish();
            }
        });


        mloginstart = (Button)findViewById(R.id.LoginButton);
        mloginstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starttolog = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(starttolog);
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
