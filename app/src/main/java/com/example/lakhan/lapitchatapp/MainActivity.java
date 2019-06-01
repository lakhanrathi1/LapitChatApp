package com.example.lakhan.lapitchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Toolbar mtoolbar;
    public ViewPager mpager;
    public SectionPageAdapter msectionpage;
    public TabLayout ntablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mtoolbar = (Toolbar)findViewById(R.id.mainpagetoolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Chit-Chat App");

        mpager = (ViewPager)findViewById(R.id.tabPager);

        msectionpage = new SectionPageAdapter(getSupportFragmentManager());

        mpager.setAdapter(msectionpage);
        ntablayout = (TabLayout)findViewById(R.id.mainTabs);
        ntablayout.setupWithViewPager(mpager);


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentuser = mAuth.getCurrentUser();


        if (currentuser == null){
                sentTostart();
        }

        // mAuth.addAuthStateListener(mAuthListener);
    }

    private void sentTostart() {

        Intent i = new Intent(getApplicationContext(),StartActivity.class);
        startActivity(i);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.mainLogOutButton){

            FirebaseAuth.getInstance().signOut();
            sentTostart();
        }

        if(item.getItemId()==R.id.mainaccountSetting){


            Intent setting = new Intent(getApplicationContext(),Setting.class);
            startActivity(setting);
            //finish();


        }
        return true;
    }
}
