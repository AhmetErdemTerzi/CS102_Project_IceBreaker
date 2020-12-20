package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserTab extends AppCompatActivity {

    EditText enterSuggestion;
    TextView welcome, txtUsername, winCount, gamesPlayed, averagePoints, quizSuggestion;
    Button btnUser, btnPlay, btnNotifications, btnSuggestion, btnLogout,userInfo,revSug;
    LinearLayout adminPart;
    public static User userClass;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tab);

        userClass = new User();

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference();
        System.out.println(userClass.getUID());
        DatabaseReference isAdmin = root.child("Users").child(userClass.getUID()).child("isAdmin");

        isAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals("1")){

                    adminPart.setVisibility(View.VISIBLE);
                    userClass = new AdminUser();

                }
                else {
                    System.out.println("falsch");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        enterSuggestion = (EditText) this.findViewById(R.id.enterSuggestion);

        welcome = (TextView) this.findViewById(R.id.welcome);
        txtUsername = (TextView) this.findViewById(R.id.txtUsername);
        winCount = (TextView) this.findViewById(R.id.winCount);
        gamesPlayed = (TextView) this.findViewById(R.id.gamesPlayed);
        averagePoints = (TextView) this.findViewById(R.id.averagePoints);
        quizSuggestion = (TextView) this.findViewById(R.id.quizSuggestion);



        userInfo= (Button) this.findViewById(R.id.userInfo);
        revSug= (Button) this.findViewById(R.id.revSug);
        adminPart = (LinearLayout) this.findViewById(R.id.adminPart);

        revSug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTab.this, reviewSuggestionsActivity.class);
                startActivity(intent);
            }
        });

        adminPart.setVisibility(View.GONE);

        btnUser = (Button) this.findViewById(R.id.btnUser);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNotifications = (Button) this.findViewById(R.id.btnNotifications);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( UserTab.this, playTabActivity.class);
                startActivity(intent);
            }
        });


        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( UserTab.this, notifActivity.class);
                startActivity(intent);
            }
        });

        btnSuggestion = (Button) this.findViewById(R.id.btnSuggestion);

        btnSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.addRequest(enterSuggestion.getText().toString());
                enterSuggestion.setText("");
            }
        });

        btnLogout = (Button) this.findViewById(R.id.btnLogout);

        root.child(userClass.getUID()).child("controller").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtUsername.setText(userClass.getName());
                winCount.setText("Win Count: " + userClass.getWinCount());
                gamesPlayed.setText("Games Played: " + (userClass.getWinCount() + userClass.getLoseCount()));
                averagePoints.setText("Average Points: " + userClass.getAvgPoints());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        root.child("Users").child(userClass.getUID()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                txtUsername.setText(userClass.getName());
                winCount.setText("Win Count: " + userClass.getWinCount());
                gamesPlayed.setText("Games Played: " + (userClass.getWinCount() + userClass.getLoseCount()));
                averagePoints.setText("Average Points: " + userClass.getAvgPoints());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LOG OUT HERE
                Toast.makeText(UserTab.this,"Goodbye " + userClass.getName(), Toast.LENGTH_SHORT).show();
                firebaseAuth.getInstance().signOut();//logs out
                Intent intent = new Intent(UserTab.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}