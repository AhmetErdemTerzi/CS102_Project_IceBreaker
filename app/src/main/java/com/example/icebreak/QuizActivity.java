package com.example.icebreak;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import io.perfmark.Tag;

public class QuizActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    Button A,B,C,D;
    TextView time, quest,count, sco;
    View.OnClickListener listen = new Listener();
    FirebaseFirestore store;
    ArrayList<Question> questionList, SelectedQuestionList;
    Question question;
    int countere, score;
    boolean flag;
    char selected;
    int random1, random2, random3, random4, random5;
    ArrayList<Integer> randoms;
    ArrayList<String> lobbyList,randomko;
    FirebaseDatabase database;
    DatabaseReference reference;
    int index;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;

    private static final String TAG = "GlobalTask1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        A = (Button) findViewById(R.id.A);
        B = (Button) findViewById(R.id.B);
        C = (Button) findViewById(R.id.C);
        D = (Button) findViewById(R.id.D);
        time = (TextView) findViewById(R.id.Timer);
        quest = (TextView) findViewById(R.id.Question);
        count = (TextView) findViewById(R.id.counter);
        sco =  (TextView) findViewById(R.id.score);

        A.setOnClickListener(listen);
        B.setOnClickListener(listen);
        C.setOnClickListener(listen);
        D.setOnClickListener(listen);

        firebaseFirestore = FirebaseFirestore.getInstance();


        questionList = new ArrayList<Question>();
        lobbyList = new  ArrayList<String>();
        randomko = new ArrayList<>();

        store = FirebaseFirestore.getInstance();
        index = 0;

        countere = 0;
        score = 0;
        randoms = new ArrayList<>();

        getRandoms();

        countDownTimer = new CountDownTimer(8700,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();

                A.setClickable(false);
                B.setClickable(false);
                C.setClickable(false);
                D.setClickable(false);

                if(flag)
                {
                    if (question.tryThis(selected)) {
                        score++;
                        if(selected =='A'){
                            A.setBackgroundColor(Color.GREEN);}
                        else if(selected == 'B'){
                            B.setBackgroundColor(Color.GREEN);}
                        else if(selected == 'C'){
                            C.setBackgroundColor(Color.GREEN);}
                        else if(selected == 'D'){
                            D.setBackgroundColor(Color.GREEN);}
                    }

                    else{
                        if(selected =='A'){
                            A.setBackgroundColor(Color.RED);}
                        else if(selected == 'B'){
                            B.setBackgroundColor(Color.RED);}
                        else if(selected == 'C'){
                            C.setBackgroundColor(Color.RED);}
                        else if(selected == 'D'){
                            D.setBackgroundColor(Color.RED);}


                        if (question.getCorrect() == 'A') {
                            A.setBackgroundColor(Color.GREEN);
                        } else if (question.getCorrect() == 'B') {
                            B.setBackgroundColor(Color.GREEN);
                        } else if (question.getCorrect() == 'C') {
                            C.setBackgroundColor(Color.GREEN);
                        } else if (question.getCorrect() == 'D') {
                            D.setBackgroundColor(Color.GREEN);
                        }
                    }

                }

                else {
                    time.setText("TIME IS UP");
                    time.setTextColor(Color.RED);
                    if (question.getCorrect() == 'A') {
                        A.setBackgroundColor(Color.GREEN);
                    } else if (question.getCorrect() == 'B') {
                        B.setBackgroundColor(Color.GREEN);
                    } else if (question.getCorrect() == 'C') {
                        C.setBackgroundColor(Color.GREEN);
                    } else if (question.getCorrect() == 'D') {
                        D.setBackgroundColor(Color.GREEN);
                    }
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                    }
                }, 2000);
            }
        };

        /*database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Lobby").child(UserTab.userClass.getCurrentLobby().getLobbyCode()).child("Quiz");
        reference.child("Upd").setValue(ThreadLocalRandom.current().nextInt(0, 20));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnap : snapshot.getChildren()) {
                    randoms.add(dataSnap.getValue(Integer.class));
                }
                setRandoms();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                reference.child("Upd").setValue(ThreadLocalRandom.current().nextInt(0, 20));
                System.out.println("ABOOOAAAAAAAAA");
            }
        });*/

    }

    public void getRandoms(){
          store.collection("LobbyCodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            for(QueryDocumentSnapshot doc : task.getResult()){
                lobbyList.add(doc.getString("LobbyCode"));
                randomko.add(doc.getString("Random1"));
                randomko.add(doc.getString("Random2"));
                randomko.add(doc.getString("Random3"));
                randomko.add(doc.getString("Random4"));
                randomko.add(doc.getString("Random5"));
                randomko.add(doc.getString("Random6"));

                System.out.println(lobbyList.size());
                System.out.println(randomko.size());
            }
            setRandoms();
            getQuestionList();
        }
    });
    }


    public void setRandoms(){

        //yeni
        index = 0;
        for(int i = 0; i < lobbyList.size(); i++){
            if(UserTab.userClass.getCurrentLobby().getLobbyCode().equals(lobbyList.get(i))){
                index = i;
                break;
            }
        }

        index = (index) * 6;
        for(int i = 0; i < 5; i++) {
            randoms.add(Integer.valueOf(randomko.get(index)));
            index++;
        }



        random1 = randoms.get(0);
        while(randoms.get(1) == randoms.get(2) && randoms.get(2) == randoms.get(3) && randoms.get(3) == randoms.get(4) && randoms.get(4) == randoms.get(5) && randoms.get(5) == randoms.get(6)){

            reference.child("Upd").setValue(ThreadLocalRandom.current().nextInt(0, 20));
            System.out.println("ABOOO");
        }



        if(randoms.get(1) != random1){
            random2 = randoms.get(1);}
        else{
            random2 = randoms.get(5);}

        if(randoms.get(2) != random2){
            random3 = randoms.get(2);}
        else{
            random3 = randoms.get(5);}

        if(randoms.get(3) != random3){
            random4 = randoms.get(3);}
        else{
            random4 = randoms.get(5);}

        if(randoms.get(4) != random4){
            random5 = randoms.get(4);}
        else{
            random5 = randoms.get(5);}
    }

    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int x = v.getId();
            flag = true;

            if (x == A.getId()) {
                A.setBackgroundColor(Color.YELLOW);
                A.setTextColor(Color.BLACK);
                A.setClickable(false);
                B.setClickable(false);
                C.setClickable(false);
                D.setClickable(false);
                selected = 'A';
            } else if (x == B.getId()) {
                B.setBackgroundColor(Color.YELLOW);
                B.setTextColor(Color.BLACK);
                A.setClickable(false);
                B.setClickable(false);
                C.setClickable(false);
                D.setClickable(false);
                selected = 'B';
            } else if (x == C.getId()) {
                C.setBackgroundColor(Color.YELLOW);
                C.setTextColor(Color.BLACK);
                C.setClickable(false);
                B.setClickable(false);
                A.setClickable(false);
                D.setClickable(false);
                selected = 'C';
            } else if (x == D.getId()) {
                D.setBackgroundColor(Color.YELLOW);
                D.setTextColor(Color.BLACK);
                B.setClickable(false);
                D.setClickable(false);
                C.setClickable(false);
                A.setClickable(false);
                selected = 'D';
            }
        }
    }



    private void getQuestionList()
    {
        questionList = new ArrayList<Question>();

        store.collection("Quiz").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot questions = task.getResult();

                for(QueryDocumentSnapshot doc : questions){
                    questionList.add(new Question(doc.getString("Question"),
                            doc.getString("A"),doc.getString("B"),
                            doc.getString("C"),doc.getString("D"),
                            doc.getString("correct").charAt(0)));
                }

                setSelectedQuestionList();
                changeQuestion();
            }
        });
    }

    private void setSelectedQuestionList(){
        SelectedQuestionList = new ArrayList<>();
        SelectedQuestionList.add(questionList.get(random1));
        SelectedQuestionList.add(questionList.get(random2));
        SelectedQuestionList.add(questionList.get(random3));
        SelectedQuestionList.add(questionList.get(random4));
        SelectedQuestionList.add(questionList.get(random5));
    }

    private void setQuestion(){
        time.setText("10");
        time.setTextColor(Color.WHITE);
        question = SelectedQuestionList.get(countere);
        quest.setText(question.getQuestion());
        A.setText(question.getA());
        A.setTextColor(Color.WHITE);
        B.setText(question.getB());
        B.setTextColor(Color.WHITE);
        C.setText(question.getC());
        C.setTextColor(Color.WHITE);
        D.setText(question.getD());
        D.setTextColor(Color.WHITE);
        flag = false;
        count.setText(Integer.toString(countere+1) + "/" + Integer.toString(SelectedQuestionList.size()));
        sco.setText(Integer.toString(score));
        A.setClickable(true);
        B.setClickable(true);
        C.setClickable(true);
        D.setClickable(true);

        A.setBackgroundColor(Color.rgb(128,128,128));
        B.setBackgroundColor(Color.rgb(128,128,128));
        C.setBackgroundColor(Color.rgb(128,128,128));
        D.setBackgroundColor(Color.rgb(128,128,128));

        Timer();
    }

    private void Timer(){
        countDownTimer.start();
    }

    private void changeQuestion(){

        if( countere < SelectedQuestionList.size())
        {
            setQuestion();
            countere++;

            time.setText(String.valueOf(10));
            Timer();

        }

        else
        {
            // Go to Score Activity
            // GO TO SCOREBOARD
            UserTab.userClass.setCurrentPoint(score);
            this.finish();
            Intent intent = new Intent(QuizActivity.this, ScoreBoardActivity.class);
            startActivity(intent);
            setContentView(R.layout.activity_score_board);
        }

    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();

        AlertDialog dialog = new AlertDialog.Builder(QuizActivity.this)
                .setMessage("Would you like to leave?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        firebaseFirestore.collection("LobbyCodes").document(playTabActivity.FirestoreLobbyReference).delete();
                        firebaseDatabase.getReference().child("Lobby").child(UserTab.userClass.getCurrentLobby().getLobbyCode()).removeValue();
                      Intent intent = new Intent(QuizActivity.this, playTabActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.start();
                    }
                })
                .show();
    }

}