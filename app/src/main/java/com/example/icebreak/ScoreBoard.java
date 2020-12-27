package com.example.icebreak;

import android.os.Handler;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScoreBoard {

    ArrayList<User> usersInScoreBoard;
    Lobby gameLobby;
    ArrayList<User> tempList;
    User temp;
    int maxIndex;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lobby");
    DatabaseReference referenceCurrentPoint = FirebaseDatabase.getInstance().getReference().child("Users");

    FirebaseFirestore x;
    FirebaseDatabase firebaseDatabase;


    public ScoreBoard(Lobby gameLobby)
    {
        this.gameLobby = gameLobby;
        x =FirebaseFirestore.getInstance();
        usersInScoreBoard = new ArrayList<>();
        tempList = new ArrayList<>();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sortUsers();
            }
        }, 1750);

    }

    public ArrayList<User> sortingUsers(){
        return tempList;
    }


    public void sortUsers(ArrayList<User> bom)
    {
        maxIndex = 0;

        if(bom.size() == 0)
        {

                firebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("UPDATER").setValue(Math.random()*5);
                if(tempList.get(0).getUID().equals(UserTab.userClass.getUID())){
                    UserTab.userClass.increaseWinCount();
                }
                else
                    UserTab.userClass.increaseLoseCount();

                    FirebaseDatabase.getInstance().getReference().child("Lobby").child(gameLobby.getLobbyCode()).child("isOver").setValue(true);
            }


        else {
            temp = bom.get(0);
            for (int i = 0; i < bom.size(); i++)
            {
                if (temp.getCurrentPoint() < bom.get(i).getCurrentPoint())
                {//EMRECAN BU getScore değil getCurrentPoint olcak
                    temp = bom.get(i);
                    maxIndex = i;
                }
            }
            tempList.add(temp);
            bom.remove(maxIndex);
            sortUsers(bom);
        }
    }






    public void sortUsers(){

        x.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot doc : task.getResult())
                {
                    usersInScoreBoard.add(new User(doc.getString("Uid"), Integer.parseInt(doc.getString("Point")), doc.getString("Name")));
                }
                sortUsers(usersInScoreBoard);
            }

        });
    }

}