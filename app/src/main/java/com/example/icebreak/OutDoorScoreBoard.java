package com.example.icebreak;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OutDoorScoreBoard extends ScoreBoard{

    Event outDoorEvent;
    ArrayList<String> notifications;
    FirebaseDatabase database;
    DatabaseReference reference, reference1;

    public OutDoorScoreBoard(Lobby gameLobby, OutdoorEvent event){
        super(gameLobby);
        this.outDoorEvent = event;

        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Direct_Task"); //task alıp verme
        reference1 = database.getReference().child("Notifications"); //notification için

        //BURASI SEND TASK NEREDE İSE ORADA OLACAK
        reference.child("Response").setValue(0);
        reference.child("Request").setValue(0);
    }

    public ArrayList<String> getNotifications(){

    }

    /*
    public void sendTaskRequest(User user)
    {
        reference.child("TaskReceiver").setValue(user.getName());
        reference.child("Request").setValue("1");
    }

    public void responseTaskRequest(int i)
    {
        // EĞER 1 VERİLİRSE KABUL -1 İSE RET
        reference.child("Response").setValue(i);
    }
*/
}
