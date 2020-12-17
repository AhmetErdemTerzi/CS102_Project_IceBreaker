package com.example.icebreak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class reviewSuggestionsActivity extends AppCompatActivity {

    Button btnUser, btnPlay, btnNotifications, userInfo,revSug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_suggestions);

        userInfo= (Button) this.findViewById(R.id.userInfo);
        revSug= (Button) this.findViewById(R.id.revSug);

        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reviewSuggestionsActivity.this, UserTab.class);
                startActivity(intent);
            }
        });

        btnUser = (Button) this.findViewById(R.id.btnUser);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNotifications = (Button) this.findViewById(R.id.btnNotifications);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( reviewSuggestionsActivity.this, playTabActivity.class);
                startActivity(intent);
            }
        });


        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( reviewSuggestionsActivity.this, notifActivity.class);
                startActivity(intent);
            }
        });
    }
}