package com.example.samanthawhite.parsetagram;

import android.app.Application;

import com.example.samanthawhite.parsetagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    //first entry point of our application
    @Override
    public void onCreate() {
        super.onCreate();

        //need to register our subclass
        //custom parse model
        ParseObject.registerSubclass(Post.class);

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
