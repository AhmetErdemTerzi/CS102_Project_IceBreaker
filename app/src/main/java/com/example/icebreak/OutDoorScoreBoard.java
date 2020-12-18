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

    public OutDoorScoreBoard(Lobby gameLobby, OutdoorEvent event){
        super(gameLobby);
        this.outDoorEvent = event;

        Notifications = new ArrayList<>();
        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Direct_Task"); //task receiver adı
        reference2 = database.getReference().child("Users");
        reference1 = database.getReference().child("Notifications"); //notification için
""

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


    /*
    public void sendTaskRequest(User taskGiver, User taskReceiver)
    {
        this.TaskGiver = taskGiver;
        this.TaskReceiver = taskReceiver;
        reference.child("TaskReceiver").setValue(taskReceiver.getName());
        reference.child("TaskGiver").setValue(taskGiver.getName());
        reference2.child(user.getUId).child("Request").setValue(1);
    }

    public void responseTaskRequest(int i)
    {
        // EĞER 1 VERİLİRSE KABUL -1 İSE RET
        reference2.child(TaskGiver.getUId).child("Response").setValue(i);
        reference.child("Random").setValue((int) (Math.random()*4));
    }
*/
}
