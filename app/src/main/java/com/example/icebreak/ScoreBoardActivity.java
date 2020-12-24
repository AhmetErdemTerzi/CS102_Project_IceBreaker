package com.example.icebreak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import android.widget.ArrayAdapter;
import android.widget.GridView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    Button exit;
    ScoreBoard scoreBoard;
    ArrayList<User> sortedUsers;
    GridView usersAndScores;
    ArrayList<String> userNameandScores;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);


        firebaseFirestore = FirebaseFirestore.getInstance();

        scoreBoard = new ScoreBoard(playTabActivity.event.getLobby());
        sortedUsers = new ArrayList<>();



        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Users").child(UserTab.userClass.getUID()).child("UPDATER").addValueEventListener(new ValueEventListener() {//to update.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                     creatorstation();
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        usersAndScores = (GridView) findViewById(R.id.table);
        userNameandScores = new ArrayList<>();
        userNameandScores.add("USERS");
        userNameandScores.add("POINTS");

        exit = findViewById(R.id.Exit);
        exit.setOnClickListener(new Listener());
    }

    private void creatorstation(){

            System.out.println("ananınammmmmcığı");

            sortedUsers = scoreBoard.sortingUsers();



            for (int i = 0; i < sortedUsers.size(); i++) {
                userNameandScores.add(sortedUsers.get(i).getName());
                System.out.println("babacık" + sortedUsers.get(i).getName());
                userNameandScores.add(Integer.toString(sortedUsers.get(i).getCurrentPoint()));
                System.out.println("annecik" + sortedUsers.get(i).getCurrentPoint());
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userNameandScores);
            usersAndScores.setAdapter(adapter);
      }


    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int x = v.getId();


            if(x == exit.getId()){
                firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).delete();
                firebaseDatabase.getReference().child("Lobby").child(UserTab.userClass.getCurrentLobby().getLobbyCode()).removeValue();
                Intent intent = new Intent(ScoreBoardActivity.this, UserTab.class);
                startActivity(intent);
            }

        }
    }

}
