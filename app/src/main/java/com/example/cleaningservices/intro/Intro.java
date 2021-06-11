package com.example.cleaningservices.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.cleaningservices.R;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ViewPager2 view = findViewById(R.id.viewPager);
        view.setPageTransformer(new ZoomOutPageTransformer());
        view.setAdapter(new FragmentsIntroAdapter(this));


    }
}