package com.example.icebreak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class playTabActivity extends AppCompatActivity {

    Spinner gameType, dateTime, numOfPlayers;
    EditText lobbyCode;
    Button joinBtn, createBtn, btnUser, btnPlay, btnNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_tab);
        gameType = (Spinner) this.findViewById(R.id.gameType);
        dateTime = (Spinner) this.findViewById(R.id.dateTime);
        numOfPlayers = (Spinner) this.findViewById(R.id.numOfPlayers);

        lobbyCode = (EditText) this.findViewById(R.id.lobbyCode);
        joinBtn = (Button) this.findViewById(R.id.joinBtn);
        createBtn = (Button) this.findViewById(R.id.createBtn);
        btnUser = (Button) this.findViewById(R.id.btnUser);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNotifications = (Button) this.findViewById(R.id.btnNotifications);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameType.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.num_of_players, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numOfPlayers.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.time_date, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateTime.setAdapter(adapter2);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( playTabActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( playTabActivity.this, UserTab.class);
                startActivity(intent);
            }
        });


        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( playTabActivity.this, notifActivity.class);
                startActivity(intent);
            }
        });
        
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gametype = gameType.getSelectedItem().toString();
                String date_time = dateTime.getSelectedItem().toString();
                String num_Players = numOfPlayers.getSelectedItem().toString();
                
                if ( gametype.equals("SELECT GAME TYPE") || date_time.equals("SELECT TIME") || num_Players.equals("SELECT PLAYER COUNT") ) {
                    Toast.makeText(playTabActivity.this, "Please set all of the game settings.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(playTabActivity.this, "Congrats! You created a game.", Toast.LENGTH_SHORT).show();
                //TODO: SERVER'A BAĞLI Bİ LOBİ OLUŞCAK.

                }

            }
        });


    }
}