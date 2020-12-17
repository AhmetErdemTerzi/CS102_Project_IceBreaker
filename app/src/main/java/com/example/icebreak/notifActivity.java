package com.example.icebreak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class notifActivity extends AppCompatActivity {

    Button btnUser, btnPlay, btnNotifications;
    TextView notif1, notif2, notif3, notif4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        btnUser = (Button) this.findViewById(R.id.btnUser);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNotifications = (Button) this.findViewById(R.id.btnNotifications);

        notif1 = (TextView) this.findViewById(R.id.notif1);
        notif2 = (TextView) this.findViewById(R.id.notif2);
        notif3 = (TextView) this.findViewById(R.id.notif3);
        notif4 = (TextView) this.findViewById(R.id.notif4);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( notifActivity.this, UserTab.class);
                startActivity(intent);
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( notifActivity.this, playTabActivity.class);
                startActivity(intent);
            }
        });
    }
}