package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class playTabActivity extends AppCompatActivity {

    private static final String TAG = "playTabActivity";
    Spinner gameType, dateTime, numOfPlayers;
    EditText lobbyCode;
    Button joinBtn, createBtn, btnUser, btnPlay, btnNotifications;
    int random1,random2,random3,random4,random5,random6;

    boolean codeCorrect;
    boolean admin;
    public static Event event;
    Lobby lobby;
    ArrayList<String> lobbyCodes;
    FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_tab);

        gameType = (Spinner) this.findViewById(R.id.gameType);
        numOfPlayers = (Spinner) this.findViewById(R.id.numOfPlayers);

        lobbyCode = (EditText) this.findViewById(R.id.lobbyCode);
        joinBtn = (Button) this.findViewById(R.id.joinBtn);
        createBtn = (Button) this.findViewById(R.id.createBtn);
        btnUser = (Button) this.findViewById(R.id.btnUser);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNotifications = (Button) this.findViewById(R.id.btnNotifications);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameType.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.num_of_players, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numOfPlayers.setAdapter(adapter1);




        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLobbyCodes(lobbyCode.getText().toString());

                //lobby = new Lobby(lobbyCode.getText().toString());
                //event = new Event(lobby.getGameType(),lobby.getOfficial(),0);
                //event.setLobby(lobby);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( playTabActivity.this, UserTab.class);
                startActivity(intent);
            }
        });


        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( playTabActivity.this, notifActivity.class);
                startActivity(intent);
            }
        });

        //isAdmin();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gametype = gameType.getSelectedItem().toString();
                String num_Players = numOfPlayers.getSelectedItem().toString();




                if ( gametype.equals("SELECT GAME TYPE")  || num_Players.equals("SELECT PLAYER COUNT") ) {
                    Toast.makeText(playTabActivity.this, "Please set all of the game settings.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(playTabActivity.this, "Congrats! You created a game.", Toast.LENGTH_SHORT).show();
                    //TODO: SERVER'A BAĞLI Bİ LOBİ OLUŞCAK.
                    if (admin)
                        event = new Event(gametype, true, Integer.parseInt(num_Players));
                    else
                        event = new Event( gametype, false, Integer.parseInt(num_Players));

                    UserTab.userClass.setCurrentLobby(event.getLobby());
                    Map<String, String> code = new HashMap<>();
                    code.put("LobbyCode", event.getLobbyCode());
                    firebaseFirestore.collection("LobbyCodes").add(code).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "lobby code eklendi: " + documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    // RANDOMİZATİON OF THE QUIZ QUESTTOINS
                    random1 = ThreadLocalRandom.current().nextInt(0, 20);

                    do {
                        random2 = ThreadLocalRandom.current().nextInt(0, 20);
                    }while(random2 == random1);

                    do {
                        random3 = ThreadLocalRandom.current().nextInt(0, 20);
                    }while(random3 == random2);


                    do {
                        random4 = ThreadLocalRandom.current().nextInt(0, 20);
                    }while(random4 == random3);


                    do {
                        random5 = ThreadLocalRandom.current().nextInt(0, 20);
                    }while(random5 == random4);         // Aynı sayılar glemesin diye

                    do {
                        random6 = ThreadLocalRandom.current().nextInt(0, 20);
                    }while(random6 == random5);         // Aynı sayılar glemesin diye


                    reference = FirebaseDatabase.getInstance().getReference().child("Lobby").child(event.getLobbyCode()).child("Quiz");

                    reference.child("Random1").setValue(random1);
                    reference.child("Random2").setValue(random2);
                    reference.child("Random3").setValue(random3);
                    reference.child("Random4").setValue(random4);
                    reference.child("Random5").setValue(random5);
                    reference.child("Random6").setValue(random6);

                    createLobby();
                }

            }
        });


    }

    public void isAdmin(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID().toString()).child("isAdmin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals("1"))
                    admin = true;
                else
                    admin = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createLobby(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("isLobbyLeader").setValue(true);
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(event.getLobbyCode()).child("Players").child(UserTab.userClass.getUID()).setValue(UserTab.userClass.getUID());
        Intent intent = new Intent(playTabActivity.this, LobbyActivity.class);
        startActivity(intent);
    }

    public void joinLobby(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("isLobbyLeader").setValue(false);
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobbyCode.getText().toString()).child("Players").child(UserTab.userClass.getUID()).setValue(UserTab.userClass.getUID());
        Intent intent = new Intent(playTabActivity.this, LobbyActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_lobby);
    }

    private void getLobbyCodes(String joinRequestCode)
    {
        lobbyCodes = new ArrayList<String>();
        codeCorrect=false;

        firebaseFirestore.collection("LobbyCodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot questions = task.getResult();

                for(QueryDocumentSnapshot doc : questions)
                {
                    lobbyCodes.add(doc.getString("LobbyCode"));
                }

                checkCodeBoolean(joinRequestCode);

            }
        });
    }

    private void checkCodeBoolean(String code)
    {
        codeCorrect = lobbyCodes.contains(code);
        Toast.makeText(this, "code is" + codeCorrect, Toast.LENGTH_LONG).show();
        if(codeCorrect)
        {
            lobby = new Lobby(lobbyCode.getText().toString());
            event = new Event(lobby);
            UserTab.userClass.setCurrentLobby(lobby);
            joinLobby();
        }
        else
            Toast.makeText(playTabActivity.this, "You entered wrong lobby code!", Toast.LENGTH_LONG).show();
    }

}