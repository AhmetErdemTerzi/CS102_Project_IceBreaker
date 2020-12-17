package com.example.icebreak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        image = (ImageView) this.findViewById(R.id.image);

            Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentUser != null) { //means that user is logged in
                            Intent homeIntent = new Intent(SplashActivity.this, UserTab.class);
                            startActivity(homeIntent);
                        } else {
                            // user is not logged in
                            Intent loginIntent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(loginIntent);
                        }
                    }
                }, 1500);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentUser != null) { //means that user is logged in
                            Intent homeIntent = new Intent(SplashActivity.this, UserTab.class);
                            startActivity(homeIntent);
                        } else {
                            // user is not logged in
                            Intent loginIntent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(loginIntent);
                        }
                    }
                });






    }
}