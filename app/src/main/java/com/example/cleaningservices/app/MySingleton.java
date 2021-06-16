package com.example.cleaningservices.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MySingleton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
