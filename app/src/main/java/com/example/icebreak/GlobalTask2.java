package com.example.icebreak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;


import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.*;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;



import java.util.*;


public class GlobalTask2 extends AppCompatActivity implements Task, SensorEventListener,   View.OnClickListener{

    //instances for sensor
    //private User currentUser = UserTab.userClass;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private boolean isStepCounter;
    private int step = 0;
    private CircularProgressBar circularProgressBar;

    private int totalStep;
    private Timer time;
    private final int upperStep = 300;
    private final int lowerStep = 200;
    private static boolean taskSucessful2 = false;
    private int step_number;
    private int prev_step;


    //buttons
    private Button btnScoreboard, btnGame, btnGoBack;

    //to check if sensor is percieved first time
    private boolean checkk = false;


    //oyun bitti mi bitmedi ona baksın
    private boolean checkk2 = false;

    //for timer
    private CountDownTimer countDownTimer;
    private User currentUser;

    private Dialog dialog;

    TextView max_step;
    TextView step_taken;
    TextView step_text;
    TextView remainingTime2;

    //Atılması gereken adım sayısı databaseden çekilmeyecek, 500 ile 600 arasında random olarak oluşturulacak.
    public GlobalTask2(){
        //Timer time
        //this.totalStep = randomTotalStep();
        //this.time = time;
    }


    @Override
    public String getTaskText() {
        return null;
    }

    public void setAndStartTimer(int seconds) {
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime2.setText("Remaining: " + String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                taskOver();
            }
        };

        countDownTimer.start();
    }


    public Timer getTime() {
        return null;
    }

    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        switch (id) {
            case R.id.btnGame:
            case R.id.btnGoBack:
                intent = new Intent(GlobalTask2.this, OutdoorEventMainActivity.class);
                startActivity(intent);
                break;

            case R.id.btnScoreboard:
                intent = new Intent(GlobalTask2.this, OutDoorScoreBoard.class);
                startActivity(intent);
                break;
        }

    }



    public void taskOver() {
        // time.cancel();
        if (taskSucessful2)
        {
            if(countDownTimer != null)
                countDownTimer.cancel();

            //current user puan alınımı eklenecek sorun çıkardığı için comment atıldı
            //currentUser.setCurrentPoint(currentUser.getCurrentPoint() + 1);
            UserTab.userClass.setCurrentPoint(UserTab.userClass.getCurrentPoint() + 10);

            openWinDialog();

        }
        else
        {
            if(countDownTimer != null)
                countDownTimer.cancel();
            openLoseDialog();
        }
    }




    // random total atılacak adım sayısı
    public int randomTotalStep(){
        return (int)(Math.random()*(upperStep-lowerStep)) + lowerStep;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialog = new Dialog(this);


        //permission of user
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_task2);


        //creating stepgoal between 500-600
        int a = this.randomTotalStep();
        step_number = a;


        //adjusting textviews
        step_text = findViewById(R.id.textViewStepCounter);
        max_step = findViewById(R.id.textView3);
        max_step.setText("" + a );
        step_taken = findViewById(R.id.textViewNumberOfStep);
        circularProgressBar = findViewById(R.id.myCircularProgressBar);
        remainingTime2 = findViewById(R.id.textView4);
        btnScoreboard = this.findViewById(R.id.btnScoreboard);
        btnGame = this.findViewById(R.id.btnGame);
        btnGoBack = this.findViewById(R.id.btnGoBack);


        //adjustin the width of the circular bar
        circularProgressBar.setBackgroundProgressBarWidth(20f);
        circularProgressBar.setProgressBarWidth(20f);


        //listener to buttons
        btnGoBack.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);



        float f = Float.intBitsToFloat(a);
        circularProgressBar.setProgressMax(f);




        //getting step sensor of user
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if((sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)) != null){
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isStepCounter = true;
        }else{
            step_taken.setText("0");
            isStepCounter = false;
        }


        //starting time
        setAndStartTimer(300);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor == stepCounter){

            if(!checkk){

                prev_step = (int) (event.values[0]);
                checkk = true;
            }
            if(!checkk2) {

                step = (int) (event.values[0]) - prev_step;
                if (step >= step_number) {
                    taskSucessful2 = true;
                    step_taken.setText(String.valueOf(step_number));
                    float step_full = Float.intBitsToFloat(step_number);
                    circularProgressBar.setProgressWithAnimation(step_full);
                    checkk2 = true;
                    taskOver();

                } else {
                    step_taken.setText(String.valueOf(step));
                    float step_f = Float.intBitsToFloat(step);
                    circularProgressBar.setProgressWithAnimation(step_f);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if((sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)) != null)
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if((sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)) != null)
            sensorManager.unregisterListener(this, stepCounter);
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
                Intent intent = new Intent(GlobalTask2.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GlobalTask2.this, OutdoorEventMainActivity.class);
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
                Intent intent = new Intent(GlobalTask2.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GlobalTask2.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}