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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

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
    static String FirestoreLobbyReference, FirestoreUserReference;
    String str;

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
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
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
                str = lobbyCode.getText().toString().toUpperCase();
                str = str.toUpperCase();
                getLobbyCodes(str);

                //lobby = new Lobby(lobbyCode.getText().toString().toUpperCase());
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


                // RANDOMİZATİON OF THE QUIZ QUESTTOINS
                random1 = (int) ((Math.random()*990) / 50);

                do {
                    random2 = (int) ((Math.random()*990) / 50);
                }while(random2 == random1);

                do {
                    random3 = (int) ((Math.random()*990) / 50);
                }while(random3 == random2);


                do {
                    random4 = (int) ((Math.random()*990) / 50);
                }while(random4 == random3);


                do {
                    random5 = (int) ((Math.random()*990) / 50);
                }while(random5 == random4);         // Aynı sayılar glemesin diye

                do {
                    random6 = (int) ((Math.random()*990) / 50);
                }while(random6 == random5);         // Aynı sayılar glemesin diye

                while(random3 == random1)
                {
                    random3 = (int) ((Math.random()*990) / 50);
                }

                while(random4 == random1)
                {
                    random4 = (int) ((Math.random()*990) / 50);
                }

                while(random5 == random1)
                {
                    random5 = (int)((Math.random()*990) / 50);
                }

                while(random6 == random1)
                {
                    random6 = (int) ((Math.random()*990) / 50);
                }

                while(random4 == random2)
                {
                    random4 = (int) ((Math.random()*990) / 50);
                }

                while(random5 == random2)
                {
                    random5 = (int) ((Math.random()*990) / 50);
                }

                while(random6 == random2)
                {
                    random6 = (int) ((Math.random()*990) / 50);
                }

                while(random5 == random3)
                {
                    random5 = (int) ((Math.random()*990) / 50);
                }

                while(random6 == random3)
                {
                    random6 = (int) ((Math.random()*990) / 50);
                }

                while(random6 == random4)
                {
                    random6 = (int) ((Math.random()*990) / 50);
                }




                if ( gametype.equals("SELECT GAME TYPE")  || num_Players.equals("SELECT PLAYER COUNT") ) {
                    Toast.makeText(playTabActivity.this, "Please set all of the game settings.", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(playTabActivity.this, "Congrats! You created a game.", Toast.LENGTH_SHORT).show();
                    //TODO: SERVER'A BAĞLI Bİ LOBİ OLUŞCAK.
                    if (admin) {
                        event = new Event(gametype, true, Integer.parseInt(num_Players));
                        ((AdminUser)UserTab.userClass).setNotifications("OFFICIAL EVENT TIME!! JOIN LOBBY: " + event.getLobbyCode());
                    }
                    else
                        event = new Event( gametype, false, Integer.parseInt(num_Players));


                    UserTab.userClass.setCurrentLobby(event.getLobby());


                    Map<String, Object> code = new HashMap<>();
                    code.put("LobbyCode", event.getLobbyCode());
                    code.put("GameType", gametype);
                    code.put("Random1", Integer.toString(random1));
                    code.put("Random2", Integer.toString(random2));
                    code.put("Random3", Integer.toString(random3));
                    code.put("Random4", Integer.toString(random4));
                    code.put("Random5", Integer.toString(random5));
                    code.put("Random6", Integer.toString(random6));
                    firebaseFirestore.collection("LobbyCodes").document(event.getLobbyCode()).set(code).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "lobby code eklendi: ");
                            FirestoreLobbyReference = event.getLobby().getLobbyCode();
                            createLobby();
                        }
                    });


                //           .add  .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                //       @Override
                //       public void onSuccess(DocumentReference documentReference) {
                //           Log.d(TAG, "lobby code eklendi: " + documentReference.getId());
                //
                //       }
                //   }).addOnFailureListener(new OnFailureListener() {
                //       @Override
                //       public void onFailure(@NonNull Exception e) {

                //       }
                //   });




                    reference = FirebaseDatabase.getInstance().getReference().child("Lobby").child(event.getLobbyCode()).child("Quiz");

                    reference.child("Random1").setValue(random1);
                    reference.child("Random2").setValue(random2);
                    reference.child("Random3").setValue(random3);
                    reference.child("Random4").setValue(random4);
                    reference.child("Random5").setValue(random5);
                    reference.child("Random6").setValue(random6);


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

        DocumentReference x = FirebaseFirestore.getInstance().collection("LobbyCodes").document(event.getLobbyCode());
        Map<String, String> uid = new HashMap<>();
        uid.put("Uid", UserTab.userClass.getUID());
        uid.put("Point", "0");
        uid.put("Name", UserTab.userClass.getName());
        x.collection("Users").add(uid).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "lobbye uid eklendi: " + documentReference.getId());
                FirestoreUserReference = documentReference.getId();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        //currentPoint - Avg.Point- number of games played in firestore.
        Map<String, Object> data = new HashMap<>();
        data.put("currentPoint", 0);
        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("Current Point").setValue(0);
        FirebaseFirestore.getInstance().collection("Users").document(UserTab.userClass.getUID()).update(data);
        Intent intent = new Intent(playTabActivity.this, LobbyActivity.class);
        startActivity(intent);
    }

    public void joinLobby(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("isLobbyLeader").setValue(false);
        FirebaseDatabase.getInstance().getReference().child("Lobby").child(lobbyCode.getText().toString().toUpperCase()).child("Players").child(UserTab.userClass.getUID()).setValue(UserTab.userClass.getUID());


        FirestoreLobbyReference = lobbyCode.getText().toString().toUpperCase();

        DocumentReference x = FirebaseFirestore.getInstance().collection("LobbyCodes").document(lobbyCode.getText().toString().toUpperCase());
        Map<String, String> uid = new HashMap<>();
        uid.put("Uid", UserTab.userClass.getUID());
        uid.put("Point", "0");
        uid.put("Name", UserTab.userClass.getName());
        x.collection("Users").add(uid).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "lobbye uid AMA OLMADII: " + documentReference.getId());
                FirestoreUserReference = documentReference.getId();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //currentPoint - Avg.Point- number of games played in firestore.
        Map<String, Object> data = new HashMap<>();
        data.put("currentPoint", 0);
        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID()).child("Current Point").setValue(0);
        FirebaseFirestore.getInstance().collection("Users").document(UserTab.userClass.getUID()).update(data);
        Intent intent = new Intent(playTabActivity.this, LobbyActivity.class);
        startActivity(intent);
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
        Toast.makeText(this, "code is " + codeCorrect, Toast.LENGTH_LONG).show();
        if(codeCorrect)
        {
            lobby = new Lobby(lobbyCode.getText().toString().toUpperCase().toUpperCase());
            event = new Event(lobby);
            UserTab.userClass.setCurrentLobby(lobby);
            joinLobby();
        }
        else
            Toast.makeText(playTabActivity.this, "You entered wrong lobby code!", Toast.LENGTH_LONG).show();
    }

    public static Event getEvent(){
        return event;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}