package com.example.icebreak;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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



    public ScoreBoard(Lobby gameLobby)
    {
        this.gameLobby = gameLobby;

        sortUsers();

    }

    public ArrayList<User> sortingUsers(){
        sortUsers(usersInScoreBoard);
        return tempList;
    }

    public void sortUsers(ArrayList<User> bom)
    {
        System.out.println("başladı");
        maxIndex = 0;

        if(bom == null)
        {
            System.out.println("\n\n\n\n\n BOM NULL ÇIKTI");
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

        reference.child(gameLobby.getLobbyCode()).child("Players").addValueEventListener(new ValueEventListener() {//TO GET PLAYERS; FIRST, FIND PLAYERS.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersInScoreBoard.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    System.out.println("FORA GIRDI!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    String uid = child.getValue().toString();
                    int point = Integer.parseInt(child.child(uid).child("Current Point").getValue().toString());
                    //System.out.println("İÇERDEYİM BEEEEEE: " + uid);
                    User user = new User(uid, point);
                    usersInScoreBoard.add(user);

                }
                System.out.println("\n\n\n" + usersInScoreBoard.size() + "\n\n\n");
                sortUsers(usersInScoreBoard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}
