package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity {

    Button  but1, but2, but3, but4, but5, but6, but7, but8, start, exit;
    TextView[]  player;
    ArrayList<User> players;
    boolean isLobbyLeader;
    Lobby lobby;

    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        player = new TextView[8];
        players = new ArrayList<User>();

        but1 = (Button) this.findViewById(R.id.but1);
        but2 = (Button) this.findViewById(R.id.but2);
        but3 = (Button) this.findViewById(R.id.but3);
        but4 = (Button) this.findViewById(R.id.but4);
        but5 = (Button) this.findViewById(R.id.but5);
        but6 = (Button) this.findViewById(R.id.but6);
        but7 = (Button) this.findViewById(R.id.but7);
        but8 = (Button) this.findViewById(R.id.but8);
        start = this.findViewById(R.id.start);
        exit = this.findViewById(R.id.exit);

        player[0] = (TextView) this.findViewById(R.id.player1);
        player[1] = (TextView) this.findViewById(R.id.player2);
        player[2] = (TextView) this.findViewById(R.id.player3);
        player[3] = (TextView) this.findViewById(R.id.player4);
        player[4] = (TextView) this.findViewById(R.id.player5);
        player[5] = (TextView) this.findViewById(R.id.player6);
        player[6] = (TextView) this.findViewById(R.id.player7);
        player[7] = (TextView) this.findViewById(R.id.player8);



        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("isLobbyLeader").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isLobbyLeader=snapshot.getValue(Boolean.class);
                if(isLobbyLeader) {

                    lobby = playTabActivity.event.getLobby();
                    listenChanges();
                    start.setVisibility(View.VISIBLE);
                    players = lobby.getPlayers();
                    setText();

                }
                else {
                    start.setVisibility(View.GONE);
                    lobby = playTabActivity.lobby;
                    listenChanges();
                    players = lobby.getPlayers();
                    setText();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //lobby.findPlayers();


        //bir de normal oyuncu versiyonu
    }

    private void listenChanges() {
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                players = lobby.getPlayers();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                players = lobby.getPlayers();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                players = lobby.getPlayers();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                players = lobby.getPlayers();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setText(){

        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").addValueEventListener(new ValueEventListener() {//TO GET PLAYERS; FIRST, FIND PLAYERS.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                players.clear();
                i = 0;
                for(DataSnapshot child : snapshot.getChildren()){
                    String uid = child.getValue().toString();
                    //System.out.println("İÇERDEYİM BEEEEEE: " + uid);
                    User user = new User(uid);

                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue(String.class) != null) {
                                players.add(user);
                                player[i].setText(snapshot.getValue(String.class));
                                System.out.println("LOBİ AKTİVİTE : " + snapshot);
                                i++;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                System.out.println(players.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        // for(int i = 0; i<players.size(); i++){
        //     player[i].setText(players.get(i).getName());
        // }

    }

}