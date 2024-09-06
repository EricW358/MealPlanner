package com.example.mealplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int Splash_Duration = 2100;

    private Animation topAnimation;
    private Animation bottomAnimation;
    private ImageView splashImage;
    private TextView splashTitle;
    private TextView splashNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        splashImage = findViewById(R.id.idSplashImage);
        splashTitle = findViewById(R.id.idSplashAppTitle);
        splashNames = findViewById(R.id.idSplashNames);

        splashImage.setAnimation(topAnimation);
        splashTitle.setAnimation(bottomAnimation);
        splashNames.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, Splash_Duration);
    }

}
