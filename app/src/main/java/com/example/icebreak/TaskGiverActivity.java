package com.example.icebreak;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
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
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_task_giver);
        directTask = new DirectTask();


        datacı = FirebaseDatabase.getInstance();
        reference = datacı.getReference().child("Direct_Task").child(OutDoorScoreBoard.directTaskCode);

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


    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int x = v.getId();
            countDownTimer.cancel();

            if (x == success.getId()) {
                directTask.setCompleted();
                success.setClickable(false);
                fail.setClickable(false);

                openWinDialog();
            }

            else if (x == fail.getId()) {
                directTask.setFailed();
                success.setClickable(false);
                fail.setClickable(false);
                openLoseDialog();
            }
        }
    }

    private void openWinDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_winnotification_taskgiver);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button OKButton = dialog.findViewById(R.id.OKButton);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskGiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskGiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void openLoseDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_losenotification_taskgiver);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button OKButton = dialog.findViewById(R.id.OKButton);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskGiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TaskGiverActivity.this, OutdoorScoreboardActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }


    private void gameIsOver()
    {
        if(countDownTimer != null)
            countDownTimer.cancel();
        Intent intent = new Intent(TaskGiverActivity.this, ScoreBoardActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
    }
}
