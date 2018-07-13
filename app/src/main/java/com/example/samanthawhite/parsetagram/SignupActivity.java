package com.example.samanthawhite.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_username;
    private EditText et_password;
    private Button signUpBtn;
    private String email;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        signUpBtn = findViewById(R.id.btn_signup);




        signUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = et_email.getText().toString();
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                SignUp();
            }
        });
    }

    public void SignUp(){
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignupActivity.this, "new user created", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    //TODO start the main activity
                    // Hooray! Let them use the app now.
                } else {
                    Toast.makeText(SignupActivity.this, "user not created!", Toast.LENGTH_LONG).show();
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }



}
