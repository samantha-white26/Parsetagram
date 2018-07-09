package com.example.samanthawhite.parsetagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    //first entry point of our application
    @Override
    public void onCreate() {
        super.onCreate();

        //set up parse...clientKey that is masterkey
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("Parstagram")
                .clientKey("ijk876vhi543sw")
                .server("http://samanthe-white26-fbu-instagram.herokuapp.com/parse")
                .build();

        //intialize the parse
        Parse.initialize(configuration);
    }
}
