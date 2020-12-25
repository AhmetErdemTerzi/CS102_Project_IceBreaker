package com.example.icebreak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class OutdoorEventMainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imageButtonGoogleMaps, imageButtonPedometer;
    Button btnSend, btnGame, btnScoreboard;
    EditText codeInput;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_event_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        imageButtonGoogleMaps = this.findViewById(R.id.mapImageButton);
        imageButtonPedometer = this.findViewById(R.id.walkImageButton);
        btnSend = this.findViewById(R.id.sendButton);
        btnGame = this.findViewById(R.id.btnGame);
        btnScoreboard = this.findViewById(R.id.btnScoreboard);
        codeInput = this.findViewById(R.id.codeEditText);

        imageButtonPedometer.setOnClickListener(this);
        imageButtonGoogleMaps.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == imageButtonGoogleMaps.getId())
        {
            Intent intent = new Intent(OutdoorEventMainActivity.this, GlobalTask1.class);
            startActivity(intent);
        }

        else if(id == imageButtonPedometer.getId())
        {
            Intent intent = new Intent(OutdoorEventMainActivity.this, GlobalTask2.class);
            startActivity(intent);
        }

        else if(id == btnScoreboard.getId())
        {
            Intent intent = new Intent(OutdoorEventMainActivity.this, OutdoorScoreboardActivity.class);
            startActivity(intent);
        }

        else if(id == btnSend.getId())
        {
            // TODO get orienteering code and test it
        }

    }

    @Override
    public void onBackPressed() {
        firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").document(playTabActivity.FirestoreUserReference).delete();
        firebaseDatabase.getReference().child("Lobby").child(UserTab.userClass.getCurrentLobby().getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();
        Intent intent = new Intent(OutdoorEventMainActivity.this, playTabActivity.class);
        startActivity(intent);
    }
}