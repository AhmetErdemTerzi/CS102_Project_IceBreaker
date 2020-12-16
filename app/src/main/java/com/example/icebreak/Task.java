package com.example.icebreak;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Task implements StatusOfGame {

    private Timer timer;
    private String taskText;
    private boolean taskCompleted;

    public Task(String taskText)
    {
        this.taskText = taskText;
        taskCompleted = false;
    }

    public void setAndStartTimer(int seconds)
    {
        timer = new Timer();
        timer.schedule(new TaskTimer(), seconds*1000);
    }

    public Timer getTime()
    {
        return timer;
    }

    public String getTaskText()
    {
        return taskText;
    }

    public void taskOver()
    {
        timer.cancel();

    }

    public class TaskTimer extends TimerTask{

        @Override
        public void run()
        {
            taskOver();
        }
    }
}
