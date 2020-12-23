package com.example.icebreak;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.NonNull;

public class Lobby {

    ArrayList<User> players;
    boolean isEventOfficial;
    static String lobbyCode;
    double[] lobbyCoordinates;

    //INSTANCES BELOW THIS COMMENT DO NOT EXIST IN UML
    String lobbyName;
    DatabaseReference lobby;
    boolean flag;
    boolean user_flag;
    String gameType;
    int random1,random2,random3,random4,random5,random6;
    DatabaseReference reference;

    private static final String TAG = "Bİ SAL";


    public Lobby(double[] lobbyCoordinates, boolean isEventOfficial, String dateTime, String lobbyCode){
        flag = false;
        lobby = FirebaseDatabase.getInstance().getReference().child("Lobby");
        gameType = "Outdoor";
        this.lobbyCode = lobbyCode;
        this.lobbyCoordinates = lobbyCoordinates;
        this.isEventOfficial = isEventOfficial;
       // setDateTime(dateTime);
        lobby.child(lobbyCode).child("isEventOfficial").setValue(isEventOfficial);
        lobby.child(lobbyCode).child("Start").setValue(false);
        lobby.child(lobbyCode).child("gameType").setValue(gameType);
        user_flag = false;

        players = new ArrayList<User>();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").setValue("");

        findPlayers();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").removeValue();

    }

    public Lobby(boolean isEventOfficial, String time, String lobbyCode){

        random1 = ThreadLocalRandom.current().nextInt(0, 20);

        do {
            random2 = ThreadLocalRandom.current().nextInt(0, 20);
        }while(random2 == random1);

        do {
            random3 = ThreadLocalRandom.current().nextInt(0, 20);
        }while(random3 == random2);


        do {
            random4 = ThreadLocalRandom.current().nextInt(0, 20);
        }while(random4 == random3);


        do {
            random5 = ThreadLocalRandom.current().nextInt(0, 20);
        }while(random5 == random4);         // Aynı sayılar glemesin diye

        do {
            random6 = ThreadLocalRandom.current().nextInt(0, 20);
        }while(random6 == random5);         // Aynı sayılar glemesin diye


        reference = FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobbyCode).child("Quiz");

        reference.child("Random1").setValue(random1);
        reference.child("Random2").setValue(random2);
        reference.child("Random3").setValue(random3);
        reference.child("Random4").setValue(random4);
        reference.child("Random5").setValue(random5);
        reference.child("Random6").setValue(random6);


        lobby = FirebaseDatabase.getInstance().getReference().child("Lobby");
        flag = false;
        gameType = "Indoor";
        this.lobbyCode = lobbyCode;
        this.isEventOfficial = isEventOfficial;
       // setDateTime(time);
        lobby.child(lobbyCode).child("isEventOfficial").setValue(isEventOfficial);
        lobby.child(lobbyCode).child("Start").setValue(false);
        lobby.child(lobbyCode).child("gameType").setValue(gameType);
        user_flag = false;
        players = new ArrayList<User>();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").setValue("");

        findPlayers();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").removeValue();

    }
    //FOR JOINERS
    public Lobby(String lobbyCode){
        lobby = FirebaseDatabase.getInstance().getReference().child("Lobby");
        flag = false;
        this.lobbyCode = lobbyCode;
        getInstances();
       // setDateTime(time);
        user_flag = false;
        players = new ArrayList<User>();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").setValue("");

        findPlayers();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").removeValue();

    }

    public void getInstances(){
        lobby.child(lobbyCode).child("isEventOfficial").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isEventOfficial = snapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lobby.child(lobbyCode).child("gameType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameType = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static String getLobbyCode(){
        return lobbyCode;
    }
    public String getGameType(){return gameType;}

    public boolean isEventOfficial(){
        return isEventOfficial;
    }

    public void setName(String name){
        lobbyName = name;
        lobby.child(lobbyCode).child("Name").setValue(name);
    }

    public String getName(){
        lobby.child(lobbyCode).child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lobbyName = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return lobbyName;

    }

    public void findPlayers(){

        lobby.child(lobbyCode).child("Players").addValueEventListener(new ValueEventListener() {//TO GET PLAYERS; FIRST, FIND PLAYERS.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnap : snapshot.getChildren()) {
                    players.add(new User(dataSnap.getValue(String.class)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public boolean getOfficial(){return isEventOfficial;}

    public ArrayList<User> getPlayers(){


        Log.d(TAG, "KullaNMAAAar" + players.size());
        return players;
    }

    public void setPlayers(ArrayList<User> players){
        this.players = players;
    }

    public void setDateTime(String dateTime){}

    public String getDateTime(){return "";}

    //public boolean addPlayer(User player, String code, double[] location){return true;}//LOBİYE ADAM EKLEME ACTIVITY UZERINDE OLCAK

    public void removePlayer(String uid){
        lobby.child(lobbyCode).child("Players").child(uid).removeValue();
    }

}
