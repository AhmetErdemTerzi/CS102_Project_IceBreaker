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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
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
        updateAvgPoint_and_gameCount();

    }

    private void updateAvgPoint_and_gameCount() {
        FirebaseFirestore.getInstance().collection("Users").document(UserTab.userClass.getUID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                long count = (long) documentSnapshot.get("numOfGamesPlayed");
                long currentPoint = (long) documentSnapshot.get("currentPoint");
                double averagePoint = (double) documentSnapshot.get("averagePoint");

                averagePoint = (count * averagePoint + currentPoint)/(count+1);
                UserTab.userClass.setAveragePoint(averagePoint);

                Map<String, Object> data = new HashMap<>();
                data.put("averagePoint", averagePoint);
                data.put("currentPoint", 0);
                data.put("numOfGamesPlayed", (count + 1));
                FirebaseFirestore.getInstance().collection("Users").document(UserTab.userClass.getUID()).update(data);


            }
        });


    }

    private void creatorstation(){


            sortedUsers = scoreBoard.sortingUsers();



            for (int i = 0; i < sortedUsers.size(); i++) {
                userNameandScores.add(sortedUsers.get(i).getName());
                userNameandScores.add(Integer.toString(sortedUsers.get(i).getCurrentPoint()));
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userNameandScores);
            usersAndScores.setAdapter(adapter);
      }


    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int x = v.getId();


            if(x == exit.getId()){
                Intent intent = new Intent(ScoreBoardActivity.this, playTabActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScoreBoardActivity.this, UserTab.class);
        startActivity(intent);
    }


}
