package com.example.icebreak;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.*;

import static android.content.ContentValues.TAG;

public class DirectTask{

    FirebaseFirestore store;
    String task;
    FirebaseDatabase database;
    String taskGiver;
    String taskReceiver;
    boolean taskSuccess;
    boolean taskOver;
    ArrayList<String> taskList;
    DatabaseReference reference;
    Timer time;
    int random;
    String str;
    int x;
    boolean flag;

    public  DirectTask(){
        flag = false;
        store = FirebaseFirestore.getInstance();
        taskList = new ArrayList<>();
        getTasklist();


        x = 0;
        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Direct_Task");


        taskSuccess = false;
        taskOver = false;
        reference.child("Completion").setValue(0);
        reference.child("Update").setValue(0);

        reference.child("Completion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = snapshot.getValue(Integer.class);
                if( x == 1)
                {
                    taskSuccess = true;}
                else if( x == -1){
                    taskSuccess = false;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference.child("TaskReceiver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskReceiver = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference.child("TaskGiver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskGiver = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference.child("Random").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                random = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getTasklist() {

        taskList = new ArrayList<>();

        store.collection("Missions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot doc : task.getResult()) {
                    str = doc.getString("objective").toString();
                    Log.d(TAG, "DocumentSnapshot data: " + str);
                    taskList.add(str);
                    Log.d(TAG, "size " + taskList.size());
                    Log.d(TAG, "rando " + random);
                }
                setTask();
            }
        });
    }


    public boolean getFlag(){
        return flag;
    }

    public void setTask(){
        task = taskList.get(random);
        reference.child("Update").setValue(1);
    }

    public void setCompleted(){     // If task receiver completed sucessfully
        reference.child("Completion").setValue(1);
    }

    public void setFailed(){
        reference.child("Completion").setValue(-1);
    }

    public void setTaskGiver(String giver){
        reference.child("TaskGiver").setValue(giver);}

    public void setTaskReceiver(String receiver){
        reference.child("TaskReceiver").setValue(receiver);
    }

    public String getTaskGiver(){
        return taskGiver;
    }

    public String getTaskReceiver(){
        return taskReceiver;
    }

    public void setRandom(int random){
        reference.child("Random").setValue(random);
    }

    public void setAndStartTimer(int seconds){

    }

    public Timer getTime(){
        return time;

    }

    public String getTaskText(){
        return task;

    }

    public void taskOver(){
        taskOver = true;
    }

    public boolean isTaskSuccess(){
        return taskSuccess;
    }


}