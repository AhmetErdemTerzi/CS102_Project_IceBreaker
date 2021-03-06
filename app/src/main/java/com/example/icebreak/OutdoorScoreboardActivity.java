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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    User tempUser;
    String str, str1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_outdoor_scoreboard);
        if((playTabActivity.event.getLobby()!=null)){
            outDoorScoreBoard = new OutDoorScoreBoard(playTabActivity.event.getLobby()); } //LOBY STATİK OLACAK ÇEKİLECEK !!!
        sortedUsers = new ArrayList<>();
        userNameandScores = new ArrayList<>();
        userNameandScores.add("USERS");
        userNameandScores.add("POINTS");
        Toast.makeText(this, "Press a user to give task", Toast.LENGTH_SHORT).show();

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

                if(position == 2 || position == 3){
                    tempUser = sortedUsers.get(0);

                }

                else if(position == 4 || position == 5){
                    tempUser = sortedUsers.get(1);
                }

                else if(position == 6 || position == 7){
                    tempUser = sortedUsers.get(2);
                }

                else if(position == 8 || position == 9){
                    tempUser = sortedUsers.get(3);
                }

                else if(position == 10 || position == 11){
                    tempUser = sortedUsers.get(4);
                }

                else if(position == 12 || position == 13){
                    tempUser = sortedUsers.get(5);
                }

                else if(position == 14 || position == 15){
                    tempUser = sortedUsers.get(6);
                }

                else if(position == 16 || position == 17){
                    tempUser = sortedUsers.get(7);
                }

                if(!(UserTab.userClass.getName().equals(tempUser.getName()))) {
                    AlertDialog dialog = new AlertDialog.Builder(OutdoorScoreboardActivity.this)
                            .setMessage("Would you like to give task to " + tempUser.getName())
                            .setPositiveButton("Give Task", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    outDoorScoreBoard.sendTaskRequest(tempUser);
                                }
                            })
                            .setNegativeButton("Forget it", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();}
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

        reference.child(UserTab.userClass.getUID()).child("Outdoor").child("DirectTaskCode").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str1 = snapshot.getValue().toString();
                System.out.println("code" + str1);
                dinle();
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
                            .setMessage("You received a request by " + str + " !")
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
                                    reference.child(UserTab.userClass.getUID()).child("Availability").setValue(true);
                                    outDoorScoreBoard.responseTaskRequest(-1,taskGiverUID);
                                    data.getReference().child("Users").child(UserTab.userClass.getUID()).child("Outdoor").child("outdoorRequestReceived").setValue(false);
                                    data.getReference().child("Users").child(UserTab.userClass.getUID()).child("Outdoor").child("senderUID").setValue("");
                                    data.getReference().child("Users").child(UserTab.userClass.getUID()).child("Outdoor").child("Response").setValue(0);
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
                if(snapshot.getValue(Integer.class)> 0){
                    Toast.makeText(OutdoorScoreboardActivity.this, "Accepted" , Toast.LENGTH_LONG);
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
                else if(snapshot.getValue(Integer.class) < 0){
                    AlertDialog dialog = new AlertDialog.Builder(OutdoorScoreboardActivity.this)
                            .setMessage("Task request is rejected by " + tempUser.getName())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

       // updateAvgPoint_and_gameCount();


        FirebaseDatabase.getInstance().getReference().child("Lobby").child(playTabActivity.event.getLobbyCode()).child("isOver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((boolean)snapshot.getValue())
                    gameIsOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void dinle(){
        FirebaseDatabase.getInstance().getReference().child("Direct_Task").child(str1).child("TaskGiver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void gameIsOver()
    {
        Intent intent = new Intent(OutdoorScoreboardActivity.this, ScoreBoardActivity.class);
        startActivity(intent);
    }

    private void creatorstation(){

        sortedUsers = outDoorScoreBoard.sortingUsers();

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
                firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").document(playTabActivity.FirestoreUserReference).delete();
                firebaseDatabase.getReference().child("Lobby").child(UserTab.userClass.getCurrentLobby().getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).removeValue();
                Intent intent = new Intent(OutdoorScoreboardActivity.this, playTabActivity.class);
                startActivity(intent);
            }

            else if(x == gamebutton.getId()){
                Intent intent = new Intent(OutdoorScoreboardActivity.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onBackPressed() {
    }

    private void updateAvgPoint_and_gameCount() {
        FirebaseFirestore.getInstance().collection("Users").document(UserTab.userClass.getUID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                long currentPoint = (long) documentSnapshot.get("currentPoint");

                Map<String, Object> data = new HashMap<>();
                data.put("currentPoint", 0);
                FirebaseFirestore.getInstance().collection("Users").document(UserTab.userClass.getUID()).update(data);


            }
        });
    }


}