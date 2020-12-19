package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OutdoorScoreboardActivity extends AppCompatActivity {

    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_scoreboard);


        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("outdoorRequestReceived").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.getValue(Boolean.class)  && UserTab.userClass.getAvailability()) {
                        AlertDialog dialog = new AlertDialog.Builder(OutdoorScoreboardActivity.this)
                                .setMessage("You received a request!")
                                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(OutdoorScoreboardActivity.this, TaskReceiverActivity.class);
                                        startActivity(intent);

                                    }
                                })
                                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("outdoorRequestReceived").setValue(false);
                                    }
                                })
                                .show();
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}