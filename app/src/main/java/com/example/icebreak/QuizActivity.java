package com.example.icebreak;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    Button A,B,C,D;
    TextView time, quest,count, sco;
    View.OnClickListener listen = new Listener();
    FirebaseFirestore store;
    ArrayList<Question> questionList;
    Question question;
    int countere, score;
    boolean flag;
    char selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
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

        questionList = new ArrayList<Question>();
        store = FirebaseFirestore.getInstance();
        getQuestionList();

        countere = 0;
        score = 0;


        countDownTimer = new CountDownTimer(10500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
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
                changeQuestion();
            }
        });
    }

    private void setQuestion(){
        time.setText("10");
        time.setTextColor(Color.WHITE);
        question = questionList.get(countere);
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
        count.setText(Integer.toString(countere+1) + "/" + Integer.toString(questionList.size()-1));
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

        if( countere < questionList.size() - 1)
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
            //QuestionActivity.this.finish();
        }

    }

}