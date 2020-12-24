package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyActivity extends AppCompatActivity {

    Button  but1, but2, but3, but4, but5, but6, but7, but8, start, exit;
    TextView[]  player;
    TextView gametype, code, lobbyOwner;
    ArrayList<User> players;
    boolean isLobbyLeader;
    Lobby lobby;
    Event event;

    String[] uidList;
    boolean flag;
    int i,textHelper;
    boolean textHelp;
<<<<<<< HEAD
    List<User> list;
    FirebaseFirestore firebaseFirestore;

=======
>>>>>>> origin/master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        flag = false;
        textHelper = 0;
        textHelp = false;
        player = new TextView[8];
        firebaseFirestore =FirebaseFirestore.getInstance();

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
        lobbyOwner = this.findViewById(R.id.lobbyOwner);
        //lobbyOwner.setText(UserTab.userClass.getName() + "'s Lobby");

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();

                firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").document(playTabActivity.FirestoreUserReference).delete();

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

        if(playTabActivity.getEvent().getLobby() != null) {
            lobby = playTabActivity.getEvent().getLobby();
            code.setText(lobby.getLobbyCode());
        }

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


        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Start").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
<<<<<<< HEAD
                if(snapshot.getValue() != null) {
                    if ((Boolean) snapshot.getValue()) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        Intent intent = new Intent(LobbyActivity.this, QuizActivity.class);
                        startActivity(intent);

                    }
=======
>>>>>>> origin/master
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
            textHelper = 0;

        }
        else if(textHelper<uidList.length) {
            System.out.println(uidList[textHelper]+"------------------");
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


       /* for(int x=0; x<uidList.length; x++) {
            i=x;
            FirebaseDatabase.getInstance().getReference().child("Users").child(uidList[x]).child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String username = snapshot.getValue().toString();
                    System.out.println("Kullanıcı adı : " + username);
                    User user = new User(uidList[i]);
                    players.add(user);
                    player[i].setText(username);



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }*/



        // for(int i = 0; i<players.size(); i++){
        //     player[i].setText(players.get(i).getName());
        // }

    }
<<<<<<< HEAD
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();
        firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").document(playTabActivity.FirestoreUserReference).delete();

    }

    public void setEvent(Event event){
        this.event = event;
    }


=======
>>>>>>> origin/master
}