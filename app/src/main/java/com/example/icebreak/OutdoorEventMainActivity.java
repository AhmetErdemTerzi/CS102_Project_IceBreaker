package com.example.icebreak;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OutdoorEventMainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imageButtonGoogleMaps, imageButtonPedometer;
    Button btnSend, btnGame, btnScoreboard;
    EditText codeInput;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> oCodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
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
        oCodes.add("ICEBREAK");
        oCodes.add("OzcanHocaIsBest");
        oCodes.add("CS102IsTheBest");
        oCodes.add("KINGKONGDONKEYBEST");

        FirebaseDatabase.getInstance().getReference().child("Lobby").child(playTabActivity.event.getLobbyCode()).child("isOver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((boolean)snapshot.getValue())
                    gameIsOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void gameIsOver()
    {

        Intent intent = new Intent(OutdoorEventMainActivity.this, ScoreBoardActivity.class);
        startActivity(intent);
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
            String str = codeInput.getText().toString();
            if(oCodes.contains(str))
            {
                oCodes.remove(str);
                UserTab.userClass.setCurrentPoint(UserTab.userClass.getCurrentPoint() + 10);
                AlertDialog dialog = new AlertDialog.Builder(OutdoorEventMainActivity.this)
                        .setMessage("Congrats! You entered a valid code!")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            else
            {
                Toast.makeText(this, "The code is invalid!", Toast.LENGTH_SHORT).show();
            }
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