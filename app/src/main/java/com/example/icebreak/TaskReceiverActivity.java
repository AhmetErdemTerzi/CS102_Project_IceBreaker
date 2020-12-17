package com.example.icebreak;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Timer;

import static android.content.ContentValues.TAG;

public class TaskReceiverActivity extends AppCompatActivity {

    DirectTask directTask;
    CountDownTimer countDownTimer;
    TextView time, user, task, complet;
    FirebaseDatabase datacı;
    DatabaseReference reference;
    int complt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskreceiver_activity);

        datacı = FirebaseDatabase.getInstance();
        reference = datacı.getReference().child("Direct_Task");

        reference.child("Update").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                task.setText(directTask.getTaskText());
                user.setText(directTask.getTaskReceiver());
                countDownTimer.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference.child("Completion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complt = snapshot.getValue(Integer.class);
                countDownTimer.cancel();
                if(complt == -1){
                    complet.setBackgroundColor(Color.RED);
                    complet.setTextColor(Color.WHITE);
                    complet.setText("You have failed to finish");
                }

                else if(complt == 1){
                    complet.setBackgroundColor(Color.BLUE);
                    complet.setTextColor(Color.WHITE);
                    complet.setText("You have succesfully finished");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        directTask = new DirectTask();


        directTask.setTaskGiver("emrecaaan");
        directTask.setTaskReceiver("ASDEQWQ");
        directTask.setRandom((int) (Math.random()*4));


        time = (TextView) findViewById(R.id.Timer);
        user = (TextView) findViewById(R.id.User);
        task = (TextView) findViewById(R.id.Task);
        complet = (TextView) findViewById(R.id.Completion);


        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String strr;
                if(millisUntilFinished % 60000 < 10000){
                    strr = "0" + String.valueOf(millisUntilFinished % 60000).substring(0,1);
                }
                else if(millisUntilFinished % 60000 < 1000){
                    strr = "00";
                }
                else{
                    strr = String.valueOf(millisUntilFinished % 60000).substring(0,2);
                }
                time.setText(String.valueOf(millisUntilFinished / 60000) + ":" + strr);
            }

            @Override
            public void onFinish() {
                complet.setBackgroundColor(Color.RED);
                complet.setTextColor(Color.WHITE);
                complet.setText("You have failed to finish");

            }
        };
        time.setText("5:00");
    }
}
