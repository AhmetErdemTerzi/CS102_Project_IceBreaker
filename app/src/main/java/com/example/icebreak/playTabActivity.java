package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class playTabActivity extends AppCompatActivity {

    Spinner gameType, dateTime, numOfPlayers;
    EditText lobbyCode;
    Button joinBtn, createBtn, btnUser, btnPlay, btnNotifications;

    boolean admin;
    public static Event event;
    public static Lobby lobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_tab);

        event = new Event("", false, "", 0);

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
                lobby = new Lobby(lobbyCode.getText().toString());
                joinLobby();
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

        isAdmin();

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
                    if (admin)
                        event = new Event(gametype, true, date_time, Integer.parseInt(num_Players));
                    else
                        event = new Event( gametype, false, date_time, Integer.parseInt(num_Players));

                    createLobby();
                }

            }
        });


    }

    public void isAdmin(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID().toString()).child("isAdmin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals("1"))
                    admin = true;
                else
                    admin = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createLobby(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("isLobbyLeader").setValue(true);
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(event.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).setValue(UserTab.userClass.getUID());
        Intent intent = new Intent(playTabActivity.this, LobbyActivity.class);
        startActivity(intent);
    }

    public void joinLobby(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("isLobbyLeader").setValue(false);
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobbyCode.getText().toString()).child("Players").child(UserTab.userClass.getUID()).setValue(UserTab.userClass.getUID());
        Intent intent = new Intent(playTabActivity.this, LobbyActivity.class);
        startActivity(intent);
    }
}