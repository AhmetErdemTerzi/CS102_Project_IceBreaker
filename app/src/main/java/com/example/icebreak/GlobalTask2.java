package com.example.icebreak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.*;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;



import java.util.*;


public class GlobalTask2 extends AppCompatActivity implements Task, SensorEventListener{

    //instances for sensor
    //private User currentUser = UserTab.userClass;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private boolean isStepCounter;
    private int step = 0;
    private CircularProgressBar circularProgressBar;

    private int totalStep;
    private Timer time;
    private int seconds;
    private final int upperStep = 600;
    private final int lowerStep = 500;
    private static boolean taskSucessful2 = false;
    private int step_number;
    private int prev_step;
    private boolean checkk = false;

    TextView max_step;
    TextView step_taken;
    TextView step_text;
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

    public void setAndStartTimer(int seconds){
        this.seconds = seconds;
        time = new Timer();
        time.schedule(new TaskTimer(), seconds*1000);
    }


    public Timer getTime() {
        return time;
    }



    public void taskOver() {
        time.cancel();
        if(taskSucessful2){

        }

    }

    public class TaskTimer extends TimerTask {

        @Override
        public void run()
        {
            taskOver();
        }
    }


    // random total atılacak adım sayısı
    public int randomTotalStep(){
        return (int)(Math.random()*(upperStep-lowerStep)) + lowerStep;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_task2);
        int a = this.randomTotalStep();
        step_number = a;
        step_text = findViewById(R.id.textViewStepCounter);
        max_step = findViewById(R.id.textView3);
        max_step.setText("" + a );
        step_taken = findViewById(R.id.textViewNumberOfStep);
        circularProgressBar = findViewById(R.id.myCircularProgressBar);
        //circularProgressBar.setProgress(65f);
        float f = Float.intBitsToFloat(a);
        circularProgressBar.setProgressMax(f);
        //


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if((sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)) != null){
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isStepCounter = true;
        }else{
            step_taken.setText("0");
            isStepCounter = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor == stepCounter){

            if(!checkk){

                prev_step = (int) (event.values[0]);
                checkk = true;
            }

            step = (int) (event.values[0]) -prev_step;
            if(step >= step_number) {
                taskSucessful2 = true;
                step_taken.setText(String.valueOf(step_number));
                float step_full = Float.intBitsToFloat(step_number);
                circularProgressBar.setProgress(step_full);
            }
            else {
                step_taken.setText(String.valueOf(step));
                float step_f = Float.intBitsToFloat(step);
                circularProgressBar.setProgressWithAnimation(step_f);
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
}