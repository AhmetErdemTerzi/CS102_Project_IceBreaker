package com.example.icebreak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    TextView user1, user2, user3, user4, user5, user6, user7, user8;
    TextView score1, score2, score3, score4, score5, score6, score7, score8;
    Button exit;
    ScoreBoard scoreBoard;
    ArrayList<User> sortedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        user1 = (TextView) findViewById(R.id.User1);
        user2 = (TextView) findViewById(R.id.User2);
        user3 = (TextView) findViewById(R.id.User3);
        user4 = (TextView) findViewById(R.id.User4);
        user5 = (TextView) findViewById(R.id.User5);
        user6 = (TextView) findViewById(R.id.User6);
        user7 = (TextView) findViewById(R.id.User7);
        user8 = (TextView) findViewById(R.id.User8);

        score1 = (TextView) findViewById(R.id.Score1);
        score2 = (TextView) findViewById(R.id.Score2);
        score3 = (TextView) findViewById(R.id.Score3);
        score4 = (TextView) findViewById(R.id.Score4);
        score5 = (TextView) findViewById(R.id.Score5);
        score6 = (TextView) findViewById(R.id.Score6);
        score7 = (TextView) findViewById(R.id.Score7);
        score8 = (TextView) findViewById(R.id.Score8);


        exit = findViewById(R.id.Exit);

        scoreBoard = new ScoreBoard(playTabActivity.event.getLobby());

        sortedUsers = scoreBoard.sortingUsers();

        //EGER DAHA AZ KİŞİ VARSA ORALAR GORUNMEZ YAP
        if(sortedUsers.size() < 8){
            setUnvisible(score8,user8);
        }
        else if(sortedUsers.size() <7){
            setUnvisible(score8,user8);
            setUnvisible(score7,user7);
        }
        else if(sortedUsers.size() <6){
            setUnvisible(score8,user8);
            setUnvisible(score7,user7);
            setUnvisible(score6,user6);
        }
        else if(sortedUsers.size() <5){
            setUnvisible(score8,user8);
            setUnvisible(score7,user7);
            setUnvisible(score6,user6);
            setUnvisible(score5,user5);
        }
        else if(sortedUsers.size() <4){
            setUnvisible(score8,user8);
            setUnvisible(score7,user7);
            setUnvisible(score6,user6);
            setUnvisible(score5,user5);
            setUnvisible(score4,user4);
        }
        else if(sortedUsers.size() <3){
            setUnvisible(score8,user8);
            setUnvisible(score7,user7);
            setUnvisible(score6,user6);
            setUnvisible(score5,user5);
            setUnvisible(score4,user4);
            setUnvisible(score3,user3);
        }

        user1.setText(sortedUsers.get(0).getName());
        user2.setText(sortedUsers.get(1).getName());
        user3.setText(sortedUsers.get(2).getName());
        user4.setText(sortedUsers.get(3).getName());
        user5.setText(sortedUsers.get(4).getName());
        user6.setText(sortedUsers.get(5).getName());
        user7.setText(sortedUsers.get(6).getName());
        user8.setText(sortedUsers.get(7).getName());

        score1.setText(sortedUsers.get(0).getCurrentPoint());
        score2.setText(sortedUsers.get(1).getCurrentPoint());
        score3.setText(sortedUsers.get(2).getCurrentPoint());
        score4.setText(sortedUsers.get(3).getCurrentPoint());
        score5.setText(sortedUsers.get(4).getCurrentPoint());
        score6.setText(sortedUsers.get(5).getCurrentPoint());
        score7.setText(sortedUsers.get(6).getCurrentPoint());
        score8.setText(sortedUsers.get(7).getCurrentPoint());

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

    private void setUnvisible(TextView score, TextView user){
        user.setBackgroundColor(Color.rgb(43,43,43));
        score.setBackgroundColor(Color.rgb(43,43,43));
        user.setTextColor(Color.rgb(43,43,43));
        score.setTextColor(Color.rgb(43,43,43));
    }

}
