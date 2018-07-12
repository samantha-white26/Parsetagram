package com.example.samanthawhite.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements LogoutFragment.OnLogoutSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.nav_toolbar);

        setSupportActionBar(toolbar);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment createPost = new CreatePostFragment();
        final Fragment home = new HomeFragment();
        final Fragment logout = new LogoutFragment();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.navigation_main:
                                //replace first: placeholder fragment with new fragment
                               fragmentTransaction.replace(R.id.your_placeholder, home).commit();
                                Toast.makeText(MainActivity.this,"made it to new fragment",Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.navigation_logout:
                               fragmentTransaction.replace(R.id.your_placeholder, logout).commit();
                               return true;
                            case R.id.navigation_post:
                                fragmentTransaction.replace(R.id.your_placeholder, createPost).commit();
                                Toast.makeText(MainActivity.this,"made it to new fragment",Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


    @Override
    public void onMainItemSelected() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Log.i("homeactivity", "logged out");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Log.i("homeactivity", "not logged out");
        }
    }
}
