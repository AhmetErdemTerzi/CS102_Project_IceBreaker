package com.example.icebreak;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Lobby {

    ArrayList<User> players;
    boolean isEventOfficial;
    String lobbyCode;

    //INSTANCES BELOW THIS COMMENT DO NOT EXIST IN UML
    String lobbyName, gameType;
    DatabaseReference lobby;
    boolean flag;
    boolean user_flag;

    public Lobby(boolean isEventOfficial,  String lobbyCode){
        lobby = FirebaseDatabase.getInstance().getReference().child("Lobby");
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobbyCode).child("isOver").setValue(false);
        flag = false;
        this.lobbyCode = lobbyCode;
        this.isEventOfficial = isEventOfficial;

        lobby.child(lobbyCode).child("isEventOfficial").setValue(isEventOfficial);
        lobby.child(lobbyCode).child("Start").setValue(false);

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

    public void setGameType(String gameType){
        this.gameType = gameType;
        lobby.child(lobbyCode).child("gameType").setValue(gameType);
    }
    public String getGameType(){return gameType;}
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

    }

    public String getLobbyCode(){
        return lobbyCode;
    }

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
                    User user = new User(uid);
                    players.add(user);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public boolean getOfficial(){return isEventOfficial;}

    public ArrayList<User> getPlayers(){

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
