package com.example.icebreak;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Lobby {

    ArrayList<User> players;
    boolean isEventOfficial;
    String lobbyCode;
    double[] lobbyCoordinates;

    //INSTANCES BELOW THIS COMMENT DO NOT EXIST IN UML
    String lobbyName;
    DatabaseReference lobby;
    boolean flag;
    boolean user_flag;
    String gameType;


    public Lobby(double[] lobbyCoordinates, boolean isEventOfficial, String dateTime, String lobbyCode){
        flag = false;
        lobby = FirebaseDatabase.getInstance().getReference().child("Lobby");
        gameType = "Outdoor";
        this.lobbyCode = lobbyCode;
        this.lobbyCoordinates = lobbyCoordinates;
        this.isEventOfficial = isEventOfficial;
       // setDateTime(dateTime);
         lobby.child(lobbyCode).child("isEventOfficial").setValue(isEventOfficial);
        lobby.child(lobbyCode).child("gameType").setValue(gameType);
        user_flag = false;

        players = new ArrayList<User>();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").setValue("");

        findPlayers();

        lobby.child(lobbyCode).child("Players").child("ZZZZZZZZZZZZZZ").removeValue();


    }

    public Lobby(boolean isEventOfficial, String time, String lobbyCode){
        lobby = FirebaseDatabase.getInstance().getReference().child("Lobby");
        flag = false;
        gameType = "Indoor";
        this.lobbyCode = lobbyCode;
        this.isEventOfficial = isEventOfficial;
       // setDateTime(time);
        lobby.child(lobbyCode).child("isEventOfficial").setValue(isEventOfficial);
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

    public String getLobbyCode(){
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
                players.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    String uid = child.getValue().toString();
                    //System.out.println("İÇERDEYİM BEEEEEE: " + uid);
                    User user = new User(uid);
                    players.add(user);

                }
                System.out.println(players.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public ArrayList<User> getPlayers(){

        System.out.println(players);
        return players;
    }

    public void setDateTime(String dateTime){}

    public String getDateTime(){return "";}

    //public boolean addPlayer(User player, String code, double[] location){return true;}//LOBİYE ADAM EKLEME ACTIVITY UZERINDE OLCAK

    public void removePlayer(String uid){
        lobby.child(lobbyCode).child("Players").child(uid).removeValue();
    }
}
