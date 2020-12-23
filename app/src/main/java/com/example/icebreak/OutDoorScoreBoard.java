package com.example.icebreak;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OutDoorScoreBoard extends ScoreBoard{

    Event outDoorEvent;
    ArrayList<String> notifications;
    FirebaseDatabase database;
    DatabaseReference reference, reference1,reference2;
    User TaskGiver;
    User TaskReceiver;
    ArrayList<String> Notifications;
    String str, code, all;
    static String directTaskCode;

    public OutDoorScoreBoard(Lobby gameLobby){
        super(gameLobby);

        Notifications = new ArrayList<>();
        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Direct_Task"); //task receiver adı
        reference2 = database.getReference().child("Users");
        reference1 = database.getReference().child("Notifications"); //notification için


        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str = snapshot.getValue(String.class);
                Notifications.add(str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference2.child(UserTab.userClass.getUID()).child("Outdoor").child("DirectTaskCode").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                directTaskCode = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public ArrayList<String> getNotifications(){
        return Notifications;
    }



    public void sendTaskRequest(User taskReceiver)
    {
        code = "";
         all = "ABCDEFGHIJKLMONPQRSTUVWXYZ1234567890";
        for(int n = 0; n < 6; n++)
        {
            int i = (int) (all.length() * Math.random());
            code = code + all.charAt(i);
        }

        directTaskCode = code;


        this.TaskReceiver = taskReceiver;
        reference2.child(taskReceiver.getUID()).child("Outdoor").child("senderUID").setValue(UserTab.userClass.getUID());
        reference.child(directTaskCode).child("TaskReceiver").setValue(taskReceiver.getName());
        reference.child(directTaskCode).child("TaskGiver").setValue(UserTab.userClass.getName());
        reference2.child(taskReceiver.getUID()).child("Outdoor").child("outdoorRequestReceived").setValue(true);
        reference2.child(taskReceiver.getUID()).child("Outdoor").child("DirectTaskCode").setValue(directTaskCode);
    }



    public void responseTaskRequest(int i,String uid)
    {

        reference2.child(uid).child("Outdoor").child("Response").setValue(i);

        if(i == 1){
            reference.child(directTaskCode).child("Random").setValue((int) (Math.random()*4));
        }
    }

}
