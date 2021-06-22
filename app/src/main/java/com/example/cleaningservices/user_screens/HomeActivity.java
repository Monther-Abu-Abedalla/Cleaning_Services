package com.example.cleaningservices.user_screens;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cleaningservices.R;
import com.example.cleaningservices.fragments.CompletedPendingServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigation = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.miHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                        break;

                    case R.id.miServices:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CompletedPendingServicesFragment()).commit();
                        break;
                    case R.id.miNotifications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NotificationFragment()).commit();
                        break;

                    case R.id.miProfile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
                        break;
                }
                return true;
            }
        });


        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NewServiceFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

    }
}