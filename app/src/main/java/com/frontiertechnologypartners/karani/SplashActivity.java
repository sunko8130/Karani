package com.frontiertechnologypartners.karani;

import androidx.appcompat.app.AppCompatActivity;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.FireworksPainter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private SyncTextPathView appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // making notification bar transparent
        Util.changeStatusBarColor(this);
        init();
        appTitle.setPathPainter(new FireworksPainter());
        appTitle.drawPath(1000f);
        appTitle.setDuration(3000);
        appTitle.startAnimation(0, 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    private void init() {
        appTitle = findViewById(R.id.txt_app_label);
    }
}
