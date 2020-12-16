package com.example.icebreak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.*;
import java.util.*;


public class GlobalTask2 extends AppCompatActivity implements Task {

    private int step;
    private int totalStep;
    private Timer time;
    private int seconds;
    private final int upperStep = 600;
    private final int lowerStep = 500;

    // databaseden gelecek olan kaç adım atması gerektiği eklenencek constructra ikinci bir parametre olarak
    public GlobalTask2(Timer time){
        this.totalStep = randomTotalStep();
        this.time = time;
    }


    public String setTaskText(){
        return "Global Task2";
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_task2);
    }
}