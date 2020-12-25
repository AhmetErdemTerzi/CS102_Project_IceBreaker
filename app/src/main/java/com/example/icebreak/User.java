package com.example.icebreak;

import android.content.DialogInterface;
import android.renderscript.Sampler;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.HashMap;
import java.util.Map;

public class User {

    double[] coordinate;
    String uid, username;
    boolean available, isLobbyLeader; //available is for availability for requests
    FirebaseUser user;
    DatabaseReference userInstances;
    int currentPoint, winCount, loseCount;
    double averagePoint;
    boolean change_flag;
    String str;
    //Event currentEvent;
    int requestCount;
    Lobby currentLobby;

    FirebaseFirestore firebaseFirestore;

    public User(){
        userInstances = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        userInstances.child(uid).child("controller").setValue(0);
        change_flag = false;
        getValuesFromDatabase();
        listenValuesAlways();

        //TODO: BURAYA LİSTENERLARI KOY: HER DEĞİŞİKLİKTE ÇAĞIRILAN LİSTENER




    }
    public User(String uid){
        userInstances = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.uid = uid;
        userInstances.child(uid).child("controller").setValue(0);
        userInstances.child(uid).child("HOSHBULDUUUUUK").setValue(true);


        //getValuesFromDatabase();
        //listenValuesAlways();
//

    }

    public User(String uid, int currentPoint, String name)
    {
        userInstances = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.uid = uid;
        userInstances.child(uid).child("controller").setValue(0);
        userInstances.child(uid).child("HOSHBULDUUUUUK").setValue(true);
        this.currentPoint = currentPoint;
        this.username = name;
    }

    public void controller(){
        username = str;
        userInstances.child(uid).child("controller").setValue(1);
    }

    public void getValuesFromDatabase(){

        userInstances.child(uid).child("isLobbyLeader").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot);
                isLobbyLeader = snapshot.getValue(Boolean.class);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Available").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                available = (Boolean) snapshot.getValue();
                //  System.out.println(available);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.getValue(String.class);

                str = username;
                controller();

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
                // System.out.println(loseCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Average Points").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                averagePoint = snapshot.getValue(Double.class);
                // System.out.println(averagePoint);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userInstances.child(uid).child("Current Point").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentPoint = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Win Count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                winCount = snapshot.getValue(Integer.class);
                // System.out.println(winCount);
                controller();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void listenValuesAlways(){

        userInstances.child(uid).child("Average Points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                averagePoint = snapshot.getValue(Double.class);
                // System.out.println("ava 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Current Point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentPoint = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userInstances.child(uid).child("isLobbyLeader").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                isLobbyLeader = (Boolean) snapshot.getValue();
                //System.out.println("lobi 2");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Available").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                available = (Boolean) snapshot.getValue();
                //System.out.println("avail 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = (String) snapshot.getValue().toString();
                str = username;
                controller();
                //System.out.println("username 2 ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Lose Count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loseCount = snapshot.getValue(Integer.class);

                //System.out.println("lose 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userInstances.child(uid).child("Win Count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                winCount = snapshot.getValue(Integer.class);
                //System.out.println("win 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public String getName(){

        return username;
    }

    public void setName(String name){
        userInstances.child(getUID()).child("Username").setValue(name);
    }

    public boolean getChangeFlag(){
        return  change_flag;
    }

    public void setChangeFlag(boolean bool){
        change_flag= bool;
    }

    public double getAveragePoint(){
        return averagePoint;
    }

    public void setAveragePoint(double point){
        userInstances.child(getUID()).child("Average Points").setValue(point);
    }

    public void setCurrentPoint(int point){
        userInstances.child(getUID()).child("Current Point").setValue(point);

        DocumentReference x = FirebaseFirestore.getInstance().collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference);
        x.collection("Users").document(playTabActivity.FirestoreUserReference).update("Point", Integer.toString(point));

        Map<String, Object> data = new HashMap<>();
        data.put("currentPoint", point);
        FirebaseFirestore.getInstance().collection("Users").document(getUID()).update(data);

    }

    public int getCurrentPoint(){
        return currentPoint;
    }

    public String getUID(){
        return uid;
    }

    public int getLoseCount (){
        return loseCount;
    }

    public int getWinCount(){
        return winCount;
    }

    public void increaseWinCount(){userInstances.child(getUID()).child("Win Count").setValue(winCount+1);}

    public void increaseLoseCount(){userInstances.child(getUID()).child("Lose Count").setValue(loseCount+1);}

    /* public double[] getLocation(){
         //TODO use googlemaps
     }*/
    //Quiz sug.
    public void addRequest(String request){

        FirebaseDatabase.getInstance().getReference().child("Suggestions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestCount = (int) snapshot.getChildrenCount();
                FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug"+(requestCount+1)).setValue(request);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setAvailability(boolean available){
        userInstances.child(getUID()).child("Available").setValue(available);
    }

    public boolean getAvailability(){

        return available;
    }

    public double getAvgPoints() {
        return averagePoint;
    }

    public void setCurrentLobby(Lobby lobby)
    {
        currentLobby=lobby;
    }

    public Lobby getCurrentLobby()
    {
        return currentLobby;
    }

    //public void setEvent(Event event){}

    //public Event getEvent(){}

    //public void joinLobby(Lobby lobby){}
}
