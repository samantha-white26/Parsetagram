package com.example.samanthawhite.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        //logic to login
        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.login_btn);
        signUpBtn = findViewById(R.id.signUp_btn);


        //get references from the login in editText fields
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                login(username, password);

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }


    private void login(String username, String password){
    // set up parse configuration
    ParseUser.logInInBackground(
        username,
        password,
        new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            // so we know that the network request is completed
            if (e == null) {
              Log.d("LoginActivity", "login in successful");
              final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
              startActivity(intent);
              finish();
            } else {
              Toast.makeText(LoginActivity.this, "User not recognized either signup or login again", Toast.LENGTH_SHORT).show();
              Log.e("LoginActivity", "login failure");
              e.printStackTrace();
            }
          }
        });
        //TODO implement later
    }
}
