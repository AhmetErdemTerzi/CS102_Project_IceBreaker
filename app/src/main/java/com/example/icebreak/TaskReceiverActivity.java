package com.example.icebreak;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    DatabaseReference reference, reference1;
    int complt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_task_receiver);

        datacı = FirebaseDatabase.getInstance();
        reference = datacı.getReference().child("Direct_Task").child(OutDoorScoreBoard.directTaskCode);

        reference.child("Update").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                task.setText(directTask.getTaskText());
                user.setText(directTask.getTaskGiver());
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
                    openLoseDialog();
                }

                else if(complt == 1){
                    openWinDialog();
                    UserTab.userClass.setCurrentPoint(UserTab.userClass.getCurrentPoint() + 10);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        directTask = new DirectTask();




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
                openLoseDialog();
            }
        };
        time.setText("5:00");

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

    private void openWinDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_winnotification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button OKButton = dialog.findViewById(R.id.OKButton);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskReceiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskReceiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void openLoseDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_losenotification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button OKButton = dialog.findViewById(R.id.OKButton);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskReceiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskReceiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }


    private void gameIsOver()
    {
        if(countDownTimer != null)
            countDownTimer.cancel();
        Intent intent = new Intent(TaskReceiverActivity.this, ScoreBoardActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
    }
}
