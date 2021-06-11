package com.example.cleaningservices.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cleaningservices.R;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);
        mAuth = FirebaseAuth.getInstance();




    }
}