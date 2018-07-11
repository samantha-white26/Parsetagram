package com.example.samanthawhite.parsetagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.nav_toolbar);

        setSupportActionBar(toolbar);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment createPost = new CreatePostFragment();
        //final Fragment fragment2 = new SecondFragment();
        //final Fragment fragment3 = new ThirdFragment();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.navigation_main:
                                //replace first: placeholder fragment with new fragment
//                                fragmentTransaction.replace(R.id.your_placeholder, fragment1).commit();

                                return true;
//                            case R.id.navigation_logout:
//                                fragmentTransaction.replace(R.id.your_placeholder, fragment2).commit();
//                                return true;
                            case R.id.navigation_post:
                                fragmentTransaction.replace(R.id.your_placeholder, createPost).commit();
                                Toast.makeText(MainActivity.this,"made it to new fragment",Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }
}
