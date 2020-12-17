package com.example.icebreak;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class User {

    double[] coordinate;
    String uid, username;
    boolean available, isLobbyLeader;
    FirebaseUser user;
    DatabaseReference userInstances;
    int currentPoint, winCount, loseCount;
    double averagePoint;
    boolean change_flag;
    String str;
    //Event currentEvent;


    public User(){
        userInstances = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = getUID();
        username = "";
        userInstances.child(uid).child("controller").setValue(0);
        change_flag = false;
        getValuesFromDatabase();
        listenValuesAlways();

        //TODO: BURAYA LİSTENERLARI KOY: HER DEĞİŞİKLİKTE ÇAĞIRILAN LİSTENER




    }

    public void controller(){
        username = str;
        userInstances.child(uid).child("controller").setValue(1);
    }

    public void getValuesFromDatabase(){

        userInstances.child(uid).child("isLobbyLeader").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isLobbyLeader = (Boolean) snapshot.getValue();
                System.out.println(isLobbyLeader);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Available").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                available = (Boolean) snapshot.getValue();
                System.out.println(available);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str = snapshot.getValue(String.class);
                //System.out.println(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Lose Count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loseCount = snapshot.getValue(Integer.class);
                System.out.println(loseCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Average Points").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                averagePoint = snapshot.getValue(Double.class);
                System.out.println(averagePoint);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Win Count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                winCount = snapshot.getValue(Integer.class);
                System.out.println(winCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        controller();
    }

    public void listenValuesAlways(){

        userInstances.child(uid).child("Average Points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                averagePoint = snapshot.getValue(Double.class);
                System.out.println("ava 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userInstances.child(uid).child("isLobbyLeader").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                isLobbyLeader = (Boolean) snapshot.getValue();
                System.out.println("lobi 2");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Available").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                available = (Boolean) snapshot.getValue();
                System.out.println("avail 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = (String) snapshot.getValue().toString();
                System.out.println("username 2 ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Lose Count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loseCount = snapshot.getValue(Integer.class);

                System.out.println("lose 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Win Count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                winCount = snapshot.getValue(Integer.class);
                System.out.println("win 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public boolean getChangeFlag(){
        return  change_flag;
    }

    public void setChangeFlag(boolean bool){
        change_flag= bool;
    }

    public String getName(){

        return username;
    }

    public void setName(String name){
        username = name;
        userInstances.child(getUID()).child("Username").setValue(name);
    }

    //public double getAveragePoint(){}

    public void setAveragePoint(double point){}

    public void setCurrentPoint(int point){}

    //public int getCurrentPoint(){}

    public String getUID(){
        return user.getUid();
    }

    public int getLoseCount (){

        return loseCount;
    }

    //increment lose count + increment win count

    public int getWinCount(){


        return winCount;
    }

   /* public double[] getLocation(){
        //TODO use googlemaps
    }*/

    public void addRequest(String request){

    }

    public void setAvailable(boolean available){
        userInstances.child("Available").setValue(available);
    }

    public boolean getAvailability(){

        return available;
    }

    public double getAvgPoints() {
        return averagePoint;
    }

    //public void setEvent(Event event){}

    //public Event getEvent(){}

    //public void joinLobby(Lobby lobby){}
}
