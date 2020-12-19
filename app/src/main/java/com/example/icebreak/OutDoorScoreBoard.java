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
    String str;

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

    }

    public ArrayList<String> getNotifications(){
        return Notifications;
    }



    public void sendTaskRequest(User taskReceiver)
    {
        this.TaskReceiver = taskReceiver;
        reference2.child(taskReceiver.getUID()).child("Outdoor").child("senderUID").setValue(UserTab.userClass.getUID());
        reference.child("TaskReceiver").setValue(taskReceiver.getName());
        reference.child("TaskGiver").setValue(UserTab.userClass.getName());
        reference2.child(taskReceiver.getUID()).child("Outdoor").child("outdoorRequestReceived").setValue(true);
    }

    public void responseTaskRequest(int i,String uid)
    {
        // EĞER 1 VERİLİRSE KABUL -1 İSE RET
        reference2.child(uid).child("Outdoor").child("Response").setValue(i);

        if(i == 1){
            reference.child("Random").setValue((int) (Math.random()*4));
        }
    }

}
