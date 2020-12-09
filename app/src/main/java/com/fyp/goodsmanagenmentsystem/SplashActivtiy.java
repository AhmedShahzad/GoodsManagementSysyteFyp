package com.fyp.goodsmanagenmentsystem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
public class SplashActivtiy extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean isdarkmodeon;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activtiy);
        logo=findViewById(R.id.logo);
        sharedPreferences
                =getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor
                = sharedPreferences.edit();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if(new SessionManager(SplashActivtiy.this).isLoggedIn())
                {
                    Intent i = new Intent(SplashActivtiy.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashActivtiy.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 5000);
    }
}