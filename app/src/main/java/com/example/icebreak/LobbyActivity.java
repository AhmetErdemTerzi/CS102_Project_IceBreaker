package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyActivity extends AppCompatActivity implements View.OnClickListener {

    Button[]  but = new Button[8];
    Button start, exit;
    TextView[]  player;
    TextView gametype, code, lobbyOwner;
    ArrayList<User> players;
    boolean isLobbyLeader;
    Lobby lobby;

    String[] uidList;
    boolean flag;
    int i,textHelper;
    boolean textHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        flag = false;
        textHelper = 0;
        textHelp = false;
        player = new TextView[8];

        but[0] = (Button) this.findViewById(R.id.but1);
        but[1] = (Button) this.findViewById(R.id.but2);
        but[2] = (Button) this.findViewById(R.id.but3);
        but[3] = (Button) this.findViewById(R.id.but4);
        but[4] = (Button) this.findViewById(R.id.but5);
        but[5] = (Button) this.findViewById(R.id.but6);
        but[6] = (Button) this.findViewById(R.id.but7);
        but[7] = (Button) this.findViewById(R.id.but8);

        for(int x = 0; x< but.length; x++){
            but[x].setOnClickListener(this);
        }

        start = this.findViewById(R.id.start);
        exit = this.findViewById(R.id.exit);
        //lobbyOwner = this.findViewById(R.id.lobbyOwner);
        //lobbyOwner.setText(UserTab.userClass.getName() + "'s Lobby");

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Start").setValue(true);
            }
        });

        player[0] = (TextView) this.findViewById(R.id.player1);
        player[1] = (TextView) this.findViewById(R.id.player2);
        player[2] = (TextView) this.findViewById(R.id.player3);
        player[3] = (TextView) this.findViewById(R.id.player4);
        player[4] = (TextView) this.findViewById(R.id.player5);
        player[5] = (TextView) this.findViewById(R.id.player6);
        player[6] = (TextView) this.findViewById(R.id.player7);
        player[7] = (TextView) this.findViewById(R.id.player8);

        gametype = (TextView) this.findViewById(R.id.gametype);
        code = (TextView) this.findViewById(R.id.code);

        lobby = playTabActivity.event.getLobby();
        code.setText(lobby.getLobbyCode());

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("isLobbyLeader").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isLobbyLeader=snapshot.getValue(Boolean.class);
                if(isLobbyLeader) {
                    start.setVisibility(View.VISIBLE);
                }
                else {
                    start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //getPlayerList();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getPlayerList();
            }
        },0,1000);

        amIKicked();


        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Start").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((Boolean) snapshot.getValue()){
                    FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("gameType").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue().toString().equals("Indoor Game")) {
                                Intent intent = new Intent(LobbyActivity.this, QuizActivity.class);
                                startActivity(intent);
                                setContentView(R.layout.activity_quiz);
                            }
                            else if(snapshot.getValue().toString().equals("Outdoor Game")){
                                Intent intent = new Intent(LobbyActivity.this, OutdoorEventMainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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
                getPlayerList();
                getPlayerList();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                getPlayerList();
                getPlayerList();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removePlayer(int n) {

        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").addListenerForSingleValueEvent(new ValueEventListener() {//TO GET PLAYERS; FIRST, FIND PLAYERS.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                System.out.println("Number of players: " + snapshot.getChildrenCount());
                uidList = new String[(int) snapshot.getChildrenCount()];
                int a = 0;
                boolean next;
                for (DataSnapshot child : snapshot.getChildren()) {
                    uidList[a] = child.getValue().toString();
                    System.out.println("------"+uidList[a]+"------");

                    if(a==snapshot.getChildrenCount()-1){
                        for (int x = 0; x < uidList.length; x++) {
                            System.out.println(uidList[x]);
                            //players.add(new User(uidList[x]));
                        }
                    }
                    else
                        a++;
                    //System.out.println("İÇERDEYİM BEEEEEE: " + uid)
                }
                if(uidList.length >= n) {
                    FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").child(uidList[n]).removeValue();
                    getPlayerList();
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uidList[n]).child("Kicked").setValue(true);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPlayerList() {

        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").addListenerForSingleValueEvent(new ValueEventListener() {//TO GET PLAYERS; FIRST, FIND PLAYERS.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                System.out.println("Number of players: " + snapshot.getChildrenCount());
                uidList = new String[(int) snapshot.getChildrenCount()];
                int a = 0;
                boolean next;
                for (DataSnapshot child : snapshot.getChildren()) {
                    uidList[a] = child.getValue().toString();
                    System.out.println("------"+uidList[a]+"------");

                    if(a==snapshot.getChildrenCount()-1){
                        for (int x = 0; x < uidList.length; x++) {
                            System.out.println(uidList[x]);
                            //players.add(new User(uidList[x]));
                            flag = true;
                        }
                        if (flag) {
                            flag = false;
                            System.out.println("Bak burdayım");
                            getNames(0);

                        }
                    }
                    else
                        a++;
                    //System.out.println("İÇERDEYİM BEEEEEE: " + uid)
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getNames(int x){
        textHelper = x;
        if(textHelper==uidList.length) {
            System.out.println("BASE CASE");
            for(int a = textHelper; a< player.length; a++){
                player[a].setText("*EMPTY*");
            }
            textHelper = 0;

        }
        else if(textHelper<uidList.length) {
            System.out.println(uidList[textHelper]+"------------------"+textHelper);
            FirebaseDatabase.getInstance().getReference().child("Users").child(uidList[textHelper]).child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() != null) {
                        System.out.println("Kullanıcı adı : " + snapshot.getValue().toString());
                        player[textHelper].setText(snapshot.getValue().toString());
                        textHelp = true;
                    }
                    if(textHelp) {
                        textHelp = false;
                        getNames(textHelper + 1);
                    }
                    else
                        getNames(textHelper);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();
    }

    @Override
    public void onClick(View v) {
        if(isLobbyLeader) {
            if (v.getId() == R.id.but1) {
                removePlayer(0);
            } else if (v.getId() == R.id.but2) {
                removePlayer(1);
            } else if (v.getId() == R.id.but3) {
                removePlayer(2);
            } else if (v.getId() == R.id.but4) {
                removePlayer(3);
            } else if (v.getId() == R.id.but5) {
                removePlayer(4);
            } else if (v.getId() == R.id.but6) {
                removePlayer(5);
            } else if (v.getId() == R.id.but7) {
                removePlayer(6);
            } else if (v.getId() == R.id.but8) {
                removePlayer(7);
            }
        }
    }

    public void amIKicked(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("Kicked").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(Boolean.class)){
                    FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("Kicked").setValue(false);
                    Toast.makeText(LobbyActivity.this,"You are kicked.", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}