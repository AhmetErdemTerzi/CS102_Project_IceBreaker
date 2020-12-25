package com.example.icebreak;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OutdoorScoreboardActivity extends AppCompatActivity {

    FirebaseDatabase data;
    Button exit, gamebutton;
    OutDoorScoreBoard outDoorScoreBoard;
    DatabaseReference reference;
    ArrayList<User> sortedUsers;
    ArrayList<String> userNameandScores;
    String taskGiverUID;
    GridView usersAndScores;
    String item;
    int noOfUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_scoreboard);
        System.out.println(playTabActivity.event.getLobby());

        outDoorScoreBoard = new OutDoorScoreBoard(playTabActivity.event.getLobby());  //LOBY STATİK OLACAK ÇEKİLECEK !!!
        sortedUsers = new ArrayList<>();
        userNameandScores = new ArrayList<>();
        userNameandScores.add("USERS");
        userNameandScores.add("POINTS");

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        exit = findViewById(R.id.Exit);
        Listener listen = new Listener();
        exit.setOnClickListener(listen);

        gamebutton = findViewById(R.id.btnGame);
        gamebutton.setOnClickListener(listen);

        data = FirebaseDatabase.getInstance();
        data.getReference().child("Users").child(UserTab.userClass.getUID()).child("UPDATER").addValueEventListener(new ValueEventListener() {//to update.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                creatorstation();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        data.getReference().child("Users").child(UserTab.userClass.getUID()).child("Outdoor").child("outdoorRequestReceived").setValue(false);
        data.getReference().child("Users").child(UserTab.userClass.getUID()).child("Outdoor").child("senderUID").setValue("");
        data.getReference().child("Users").child(UserTab.userClass.getUID()).child("Outdoor").child("Response").setValue(0);

        reference = data.getReference().child("Users");

        sortedUsers = outDoorScoreBoard.sortingUsers();


        usersAndScores = (GridView) findViewById(R.id.table);

        usersAndScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();

                noOfUser = sortedUsers.size();

             //  for(int i = 2; i < (usersAndScores.size()-2/2); i++){
             //      if(position == i){
             //          outDoorScoreBoard.sendTaskRequest(sortedUsers.get(i));
             //      }
             //  }

                if(position == 2 || position == 3){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(0));

                }


                else if(position == 4 || position == 5){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(1));
                }

                else if(position == 6 || position == 7){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(2));
                }

                else if(position == 8 || position == 9){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(3));
                }

                else if(position == 10 || position == 11){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(4));
                }

                else if(position == 12 || position == 13){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(5));
                }

                else if(position == 14 || position == 15){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(6));
                }

                else if(position == 16 || position == 17){
                    outDoorScoreBoard.sendTaskRequest(sortedUsers.get(7));
                }
            }
        });




        //TASK GIVERIN UID SINI ALMA. TASK RECEIVERIN TASK GIVERI HABERDAR ETMESI ICIN
        reference.child(UserTab.userClass.getUID()).child("Outdoor").child("senderUID").addValueEventListener(new ValueEventListener() {
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
                    AlertDialog dialog = new AlertDialog.Builder(OutdoorScoreboardActivity.this)
                            .setMessage("You received a request!")
                            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reference.child(UserTab.userClass.getUID()).child("outdoorRequestReceived").setValue(false);
                                    reference.child(UserTab.userClass.getUID()).child("Availability").setValue(false);
                                    outDoorScoreBoard.responseTaskRequest(1,taskGiverUID);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(OutdoorScoreboardActivity.this, TaskReceiverActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 2000);

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
                    reference.child(UserTab.userClass.getUID()).child("Availability").setValue(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(OutdoorScoreboardActivity.this, TaskGiverActivity.class);
                            startActivity(intent);
                        }
                    }, 2500);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

    private void creatorstation(){

        sortedUsers = outDoorScoreBoard.sortingUsers();

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
                Intent intent = new Intent(OutdoorScoreboardActivity.this, UserTab.class);
                startActivity(intent);
            }

            else if(x == gamebutton.getId()){
                Intent intent = new Intent(OutdoorScoreboardActivity.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }

        }
    }

}