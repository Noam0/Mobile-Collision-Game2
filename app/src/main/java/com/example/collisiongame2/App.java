package com.example.collisiongame2;

import android.app.Application;

import com.example.collisiongame2.Utilities.SharedPreferencesManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(getApplicationContext());
    }
}
