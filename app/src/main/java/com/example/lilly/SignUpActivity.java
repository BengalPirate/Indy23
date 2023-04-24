package com.example.lilly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lilly.models.User;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (ParseUser.getCurrentUser() != null){
            goMainActivity();
        }
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUpUser(firstName, lastName, email, username, password);
            }
        });
    }
    private void signUpUser(String firstName, String lastName, String email, String username, String password) {
        ParseUser user = new ParseUser();
        //Parse newUser = new ParseObject();
        //user.(firstName);
        //user.setKeyLastName(lastName);
        user.put("firstName", firstName);
        user.put("lastName", lastName);

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Navigate user to main activity
                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Issue with Signup: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}