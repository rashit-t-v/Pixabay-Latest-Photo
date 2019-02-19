package com.rashit.tiugaev.image.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.rashit.tiugaev.image.HomeActivity;
import com.rashit.tiugaev.image.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // do something after 1s
            }

            @Override
            public void onFinish() {
                // do something end times 5s
                final Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        }.start();

    }



    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
