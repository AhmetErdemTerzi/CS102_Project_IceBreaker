package com.example.icebreak;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OutdoorScoreboardActivity extends AppCompatActivity {

    FirebaseDatabase data;
    TextView user1, user2, user3, user4, user5, user6, user7, user8;
    TextView score1, score2, score3, score4, score5, score6, score7, score8;
    Button button1, button2, button3, button4, button5, button6, button7, button8;
    Button exit;
    OutDoorScoreBoard outDoorScoreBoard;
    DatabaseReference reference;
    ArrayList<User> sortedUsers;
    String taskGiverUID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_scoreboard);

        data = FirebaseDatabase.getInstance();
        reference = data.getReference().child("Users");

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

        button1 = findViewById(R.id.Button1);
        button2 = findViewById(R.id.Button2);
        button3 = findViewById(R.id.Button3);
        button4 = findViewById(R.id.Button4);
        button5 = findViewById(R.id.Button5);
        button6 = findViewById(R.id.Button6);
        button7 = findViewById(R.id.Button7);
        button8 = findViewById(R.id.Button8);

        exit = findViewById(R.id.Exit);

       // outDoorScoreBoard = new OutDoorScoreBoard(lobbyClass.getLoby());  //LOBY STATİK OLACAK ÇEKİLECEK !!!


        //TASK GIVERIN UID SINI ALMA. TASK RECEIVERIN TASK GIVERI HABERDAR ETMESI ICIN
        reference.child(UserTab.userClass.getUID()).child("Outdoor").child("SenderUID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskGiverUID = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //GELEN TASK REQUESTLER
        reference.child(UserTab.userClass.getUID()).child("Outdoor").child("outdoorRequestReceived").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(Boolean.class) && UserTab.userClass.getAvailability()){
                    AlertDialog dialog = new AlertDialog.Builder(OutDoorScoreBoardActivity.this)
                            .setMessage("You received a request!")
                            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reference.child(UserTab.userClass.getUID()).child("outdoorRequestReceived").setValue(false);
                                    reference.child(UserTab.userClass.getUID()).child("Availability").setValue(false);
                                    outDoorScoreBoard.responseTaskRequest(1,taskGiverUID);
                                    Intent intent = new Intent(OutDoorScoreBoardActivity.this, TaskReceiverActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reference.child(UserTab.userClass.getUID()).child("outdoorRequestReceived").setValue(false);
                                    outDoorScoreBoard.responseTaskRequest(-1,taskGiverUID);
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //Gonderilen request cevaplari
        reference.child(UserTab.userClass.getUID()).child("Outdoor").child("Response").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(Integer.class) == 1){
                    Intent intent = new Intent(OutDoorScoreBoardActivity.this, TaskGiverActivity.class);
                    reference.child(UserTab.userClass.getUID()).child("Availability").setValue(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        sortedUsers = outDoorScoreBoard.sortingUsers();

        //EGER DAHA AZ KİŞİ VARSA ORALAR GORUNMEZ YAP
        if(sortedUsers.size() < 8){
            setUnvisible(button8,score8,user8);
        }
        else if(sortedUsers.size() <7){
            setUnvisible(button8,score8,user8);
            setUnvisible(button7,score7,user7);
        }
        else if(sortedUsers.size() <6){
            setUnvisible(button8,score8,user8);
            setUnvisible(button7,score7,user7);
            setUnvisible(button6,score6,user6);
        }
        else if(sortedUsers.size() <5){
            setUnvisible(button8,score8,user8);
            setUnvisible(button7,score7,user7);
            setUnvisible(button6,score6,user6);
            setUnvisible(button5,score5,user5);
        }
        else if(sortedUsers.size() <4){
            setUnvisible(button8,score8,user8);
            setUnvisible(button7,score7,user7);
            setUnvisible(button6,score6,user6);
            setUnvisible(button5,score5,user5);
            setUnvisible(button4,score4,user4);
        }
        else if(sortedUsers.size() <3){
            setUnvisible(button8,score8,user8);
            setUnvisible(button7,score7,user7);
            setUnvisible(button6,score6,user6);
            setUnvisible(button5,score5,user5);
            setUnvisible(button4,score4,user4);
            setUnvisible(button3,score3,user3);
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

        View.OnClickListener listen = new Listener();

        button1.setOnClickListener(listen);
        button2.setOnClickListener(listen);
        button3.setOnClickListener(listen);
        button4.setOnClickListener(listen);
        button5.setOnClickListener(listen);
        button6.setOnClickListener(listen);
        button7.setOnClickListener(listen);
        button8.setOnClickListener(listen);

        exit.setOnClickListener(listen);
    }

    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int x = v.getId();

            if(x == button1.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(0));
            }
            else if(x == button2.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(1));
            }
            else if(x == button3.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(2));
            }
            else if(x == button4.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(3));
            }
            else if(x == button5.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(4));
            }
            else if(x == button6.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(5));
            }
            else if(x == button7.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(6));
            }
            else if(x == button8.getId()){
                outDoorScoreBoard.sendTaskRequest(sortedUsers.get(7));
            }

            else if(x == exit.getId()){
                Intent intent = new Intent(OutDoorScoreBoardActivity.this, UserTab.class);
                startActivity(intent);
            }

        }
    }

    private void setUnvisible(Button button, TextView score, TextView user){
        button.setClickable(false);
        button.setBackgroundColor(Color.rgb(43,43,43));
        user.setBackgroundColor(Color.rgb(43,43,43));
        score.setBackgroundColor(Color.rgb(43,43,43));
        user.setTextColor(Color.rgb(43,43,43));
        score.setTextColor(Color.rgb(43,43,43));
    }


}