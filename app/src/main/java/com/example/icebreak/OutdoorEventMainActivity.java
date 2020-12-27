package com.example.icebreak;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.SortedMap;

public class OutdoorEventMainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imageButtonGoogleMaps, imageButtonPedometer;
    Button btnSend, btnGame, btnScoreboard;
    EditText codeInput;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> oCodes = new ArrayList<>();
    ArrayList<User> tempList;
    User temp;
    int maxIndex;
    ArrayList<User> usersInScoreBoard;
    FirebaseFirestore x;
    ArrayList<Integer> ints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_outdoor_event_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        x =FirebaseFirestore.getInstance();
        usersInScoreBoard = new ArrayList<>();
        tempList = new ArrayList<>();
        ints = new ArrayList<>();
        imageButtonGoogleMaps = this.findViewById(R.id.mapImageButton);
        imageButtonPedometer = this.findViewById(R.id.walkImageButton);
        btnSend = this.findViewById(R.id.sendButton);
        btnGame = this.findViewById(R.id.btnGame);
        btnScoreboard = this.findViewById(R.id.btnScoreboard);
        codeInput = this.findViewById(R.id.codeEditText);
        codeInput.setTextColor(Color.BLACK);

        imageButtonPedometer.setOnClickListener(this);
        imageButtonGoogleMaps.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        oCodes.add("ICEBREAK");
        oCodes.add("OzcanHocaIsBest");
        oCodes.add("CS102IsTheBest");
        oCodes.add("KINGKONGDONKEYBEST");
        oCodes.add("BILKENT");
        oCodes.add("JAVACODE");
        oCodes.add("B_Building");
        oCodes.add("HARAMBE");



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

    private void gameIsOver()
    {

        Intent intent = new Intent(OutdoorEventMainActivity.this, ScoreBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        sortUsers();
        if(id == imageButtonGoogleMaps.getId())
        {
            Intent intent = new Intent(OutdoorEventMainActivity.this, GlobalTask1.class);
            startActivity(intent);
        }

        else if(id == imageButtonPedometer.getId())
        {
            Intent intent = new Intent(OutdoorEventMainActivity.this, GlobalTask2.class);
            startActivity(intent);
        }

        else if(id == btnScoreboard.getId())
        {
            Intent intent = new Intent(OutdoorEventMainActivity.this, OutdoorScoreboardActivity.class);
            startActivity(intent);
        }

        else if(id == btnSend.getId())
        {
            String str = codeInput.getText().toString();
            if(oCodes.contains(str))
            {
                oCodes.remove(str);
                UserTab.userClass.setCurrentPoint(UserTab.userClass.getCurrentPoint() + 10);
                AlertDialog dialog = new AlertDialog.Builder(OutdoorEventMainActivity.this)
                        .setMessage("Congrats! You entered a valid code!")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                codeInput.setText("");
            }
            else
            {
                Toast.makeText(this, "The code is invalid!", Toast.LENGTH_SHORT).show();
                codeInput.setText("");
            }
        }

    }

    public void sortUsers(){

        x.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot doc : task.getResult())
                {
                    System.out.println("denemee" + doc.getString("Name") + " " + doc.getString("Point") );
                    ints.add(Integer.parseInt(doc.getString("Point")));
                }
                look(ints);
            }

        });
    }

    private void look(ArrayList<Integer> ints){

        for(int i = 0; i < ints.size(); i++)
                if (ints.get(i) >= 80) {
                    System.out.println("EN YUKEEK PUAN" + ints);
                    FirebaseDatabase.getInstance().getReference().child("Lobby").child(playTabActivity.getEvent().getLobby().getLobbyCode()).child("isOver").setValue(true);
                }
                }


    @Override
    public void onBackPressed() {
    }

}