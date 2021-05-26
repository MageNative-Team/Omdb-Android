package com.example.klyp_test.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.klyp_test.R;

public class Splash extends Activity {
    private int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent i = new Intent(Splash.this, Dashboard.class);
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
    }
}