package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
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
import android.widget.Toast;


public class LobbyActivity extends AppCompatActivity implements View.OnClickListener {

    Button[]  but = new Button[8];
    Button start, exit;
    TextView[]  player;
    TextView gametype, code, lobbyOwner;
    ArrayList<User> players;
    boolean isLobbyLeader;
    Lobby lobby;
    Event event;
    boolean amIALone;
    int tempp;
    boolean bollle;

    String[] uidList;
    boolean flag;
    int i,textHelper;
    boolean textHelp;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_lobby);

        amIALone = false;
        flag = false;
        textHelper = 0;
        textHelp = false;
        player = new TextView[8];
        firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                gametype.setText(documentSnapshot.getString("GameType"));
            }
        });

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

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();
                firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").document(playTabActivity.FirestoreUserReference).delete();
              //if(amIALone){
              //    FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).removeValue();
              //    firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).delete();

              //}

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

        if(playTabActivity.getEvent() != null) {
            lobby = playTabActivity.getEvent().getLobby();
            gametype.setText(lobby.getGameType());
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

        amIKicked();


        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Start").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    if ((Boolean) snapshot.getValue()) {

                        //ERKOŞKOOO ************    İNANILMAZ BİR ATLAYIŞ


                        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("gameType").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getValue().toString().equals("Indoor Game")) {
                                    if (timer != null) {
                                        timer.cancel();
                                    }
                                    Intent intent = new Intent(LobbyActivity.this, QuizActivity.class);
                                    startActivity(intent);
                                }
                                else if(snapshot.getValue().toString().equals("Outdoor Game")){
                                    if (timer != null) {
                                        timer.cancel();
                                    }
                                    Intent intent = new Intent(LobbyActivity.this, OutdoorEventMainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //ERKOŞKOOO ************ OOOMAAYYGAT



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //lobby.findPlayers();



        //bir de normal oyuncu versiyonu
    }

    //ERKİNNN NAPTIN BABAAAA

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

    //YOK ETMİŞŞŞSİNNN

    public void getPlayerList() {

        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").addListenerForSingleValueEvent(new ValueEventListener() {//TO GET PLAYERS; FIRST, FIND PLAYERS.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                System.out.println("Number of players: " + snapshot.getChildrenCount());
                if(snapshot.getChildrenCount() == 1){
                    amIALone = true;
                }

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
                setUnvisible(player[a], but[a]);
            }
            textHelper = 0;

        }
        else if(textHelper<uidList.length) {
            System.out.println(uidList[textHelper]+"------------------");
            FirebaseDatabase.getInstance().getReference().child("Users").child(uidList[textHelper]).child("isLobbyLeader").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                    System.out.println("deger" + snapshot1.getValue().toString());
                    if(snapshot1.getValue().toString().equals("true")){
                        bollle = true;
                    }
                    else{
                        bollle = false;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            FirebaseDatabase.getInstance().getReference().child("Users").child(uidList[textHelper]).child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() != null) {
                        System.out.println("Kullanıcı adı : " + snapshot.getValue().toString());

                        if(bollle){
                            player[textHelper].setTextColor(Color.RED);
                            player[textHelper].setText(snapshot.getValue().toString() + "  (LEADER)");
                        }
                        else{
                            player[textHelper].setTextColor(Color.WHITE);
                            player[textHelper].setText(snapshot.getValue().toString());
                        }
                        if(isLobbyLeader){
                            if(snapshot.getValue().toString().equals(UserTab.userClass.getName())){
                                player[textHelper].setVisibility(View.VISIBLE);
                                but[textHelper].setVisibility(View.INVISIBLE);
                            }
                            else {
                                setVisible(player[textHelper], but[textHelper]);
                            }
                        }
                        else{
                            setVisible(player[textHelper], but[textHelper]);
                            but[textHelper].setVisibility(View.INVISIBLE);
                            }

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

    //ERKİNCİM
    @Override
    public void onClick(View v) {
        if(isLobbyLeader) {
            if (v.getId() == R.id.but1 && !player[0].getText().equals(UserTab.userClass.getName()) && !player[0].getText().equals("*EMPTY*")) {
                tempp = 0;
            } else if (v.getId() == R.id.but2 && !player[1].getText().equals(UserTab.userClass.getName()) && !player[1].getText().equals("*EMPTY*")) {
                tempp = 1;
            } else if (v.getId() == R.id.but3 && !player[2].getText().equals(UserTab.userClass.getName()) && !player[2].getText().equals("*EMPTY*")) {
                tempp = 2;
            } else if (v.getId() == R.id.but4 && !player[3].getText().equals(UserTab.userClass.getName()) && !player[3].getText().equals("*EMPTY*")) {
                tempp = 3;
            } else if (v.getId() == R.id.but5 && !player[4].getText().equals(UserTab.userClass.getName()) && !player[4].getText().equals("*EMPTY*")) {
                tempp = 4;
            } else if (v.getId() == R.id.but6 && !player[5].getText().equals(UserTab.userClass.getName()) && !player[5].getText().equals("*EMPTY*")) {
                tempp = 5;
            } else if (v.getId() == R.id.but7 && !player[6].getText().equals(UserTab.userClass.getName()) && !player[6].getText().equals("*EMPTY*")) {
                tempp = 6;
            } else if (v.getId() == R.id.but8 && !player[7].getText().equals(UserTab.userClass.getName()) && !player[7].getText().equals("*EMPTY*")) {
                tempp = 7;
            }
            AlertDialog dialog = new AlertDialog.Builder(LobbyActivity.this)
                    .setMessage("Do you want to kick " + player[tempp].getText() + " ?")
                    .setPositiveButton("Kick", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removePlayer(tempp);
                        }
                    })
                    .setNegativeButton("Let him stay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }


    public void amIKicked() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("Kicked").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    if (snapshot.getValue(Boolean.class)) {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("Kicked").setValue(false);
                        Toast.makeText(LobbyActivity.this, "You are kicked.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();
        firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").document(playTabActivity.FirestoreUserReference).delete();

       //if(amIALone){
       //    FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobby.getLobbyCode()).removeValue();
       //    firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).delete();

       //}
    }

    public void setEvent(Event event){
        this.event = event;
    }

    public void setUnvisible(TextView textView, Button button){
        textView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        button.setClickable(false);
    }

    public void setVisible(TextView textView, Button button){
        textView.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        button.setClickable(true);
    }




}