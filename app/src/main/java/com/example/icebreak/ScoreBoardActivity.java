package com.example.icebreak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import android.widget.ArrayAdapter;
import android.widget.GridView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    TextView user1, user2, user3, user4, user5, user6, user7, user8;
    TextView score1, score2, score3, score4, score5, score6, score7, score8;
    Button exit;
    ScoreBoard scoreBoard;
    ArrayList<User> sortedUsers;
    GridView usersAndScores;
    ArrayList<String> userNameandScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);


        usersAndScores = (GridView) findViewById(R.id.table);



        exit = findViewById(R.id.Exit);

        scoreBoard = new ScoreBoard(playTabActivity.event.getLobby());

        sortedUsers = scoreBoard.sortingUsers();


        userNameandScores.add("Users");
        userNameandScores.add("Points");
        for(int i = 0; i < 2*sortedUsers.size(); i++){
            userNameandScores.add(sortedUsers.get(i).getName());
            userNameandScores.add(Integer.toString(sortedUsers.get(i).getCurrentPoint()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userNameandScores);
        usersAndScores.setAdapter(adapter);



        exit.setOnClickListener(new Listener());
    }


    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int x = v.getId();


            if(x == exit.getId()){
                Intent intent = new Intent(ScoreBoardActivity.this, UserTab.class);
                startActivity(intent);
            }

        }
    }

}
