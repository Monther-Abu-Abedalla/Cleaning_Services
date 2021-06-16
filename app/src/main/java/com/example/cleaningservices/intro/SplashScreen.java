package com.example.cleaningservices.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cleaningservices.R;
import com.example.cleaningservices.login_signup.LoginActivity;
import com.example.cleaningservices.user_screens.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    final private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color));

        Boolean isFirstTimeOpened = getSharedPreferences("isFirstTimeOpened", MODE_PRIVATE).getBoolean("isFirstTimeOpened", true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstTimeOpened) {
                    startActivity(new Intent(getApplicationContext(), Intro.class));
                } else {
                    if (auth.getCurrentUser() == null) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                }
                finish();
            }
        }, 1000);

    }

    }
