package com.example.icebreak;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskGiverActivity extends AppCompatActivity {

    DirectTask directTask;
    CountDownTimer countDownTimer;
    TextView time, user, task, complet;
    Button success, fail;
    View.OnClickListener listen = new Listener();
    FirebaseDatabase datacı;
    DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskgiver_activity);

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
        directTask = new DirectTask();


        directTask.setTaskGiver("emrecaaan");
        directTask.setTaskReceiver("ASDEQWQ");
        directTask.setRandom((int) (Math.random()*4));


        time = (TextView) findViewById(R.id.Timer);
        user = (TextView) findViewById(R.id.User);
        task = (TextView) findViewById(R.id.Task);
        complet = (TextView) findViewById(R.id.Completion);
        success = (Button) findViewById(R.id.Success);
        fail = (Button) findViewById(R.id.Fail);
        success.setOnClickListener(listen);
        fail.setOnClickListener(listen);


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

                directTask.setFailed();
                complet.setBackgroundColor(Color.RED);
                complet.setTextColor(Color.WHITE);
                complet.setText("Your opponent have failed to finish");

            }
        };
        time.setText("5:00");

    }


    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int x = v.getId();
            countDownTimer.cancel();

            if (x == success.getId()) {
                directTask.setCompleted();
                success.setClickable(false);
                fail.setClickable(false);

                complet.setBackgroundColor(Color.BLUE);
                complet.setTextColor(Color.WHITE);
                complet.setText("Your opponent have succesfully finished");
            }

            else if (x == fail.getId()) {
                directTask.setFailed();
                success.setClickable(false);
                fail.setClickable(false);
                complet.setBackgroundColor(Color.RED);
                complet.setTextColor(Color.WHITE);
                complet.setText("Your opponent have failed to finish");
            }
        }
    }
}
