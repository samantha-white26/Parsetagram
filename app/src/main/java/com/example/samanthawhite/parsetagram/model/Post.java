package com.example.samanthawhite.parsetagram.model;

import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;


@ParseClassName("Post")
public class Post extends ParseObject {
    //should match the columns in our Heroku server
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_CREATED_AT = "createdAt";

    //encapsulate accessors and mutators (getter and setter)

    //accessor and mutator
    //gets description for the post
    public String getDescription(){
        //getString is defined in the ParseObject Class... need to pass in a key as defined in the heroku
        return getString(KEY_DESCRIPTION);

    }

    public void setDescription(String description){
        //used when you want to create a post
        //create a post model and set description using this method
        put(KEY_DESCRIPTION, description);
    }

    public Date getCreatedAt() {
        return super.getCreatedAt();
    }

    public String getRelativeTimeAgo() {
        long dateMillis = getCreatedAt().getTime();
        return DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }



    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }


    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    //query of a post class
    public static class Query extends ParseQuery<Post> {
        //constructor
        public Query(){
            super(Post.class);
        }

        //get the first 20 posts
        public Query getTop(){
            orderByDescending(KEY_CREATED_AT);
            setLimit(20);
            return this;
        }

        //want to be sure that the queries include the user
        public Query withUser(){
            include("user");
            return this;
        }

    }
}
