package com.example.icebreak;

import java.util.Timer;

public abstract class Task implements StatusOfGame {

    private Timer timer;
    private String taskText;

    public void setTime(Timer time)
    {
        this.timer = time;
    }

    public Timer getTime()
    {
        return timer;
    }

    public String getTaskText()
    {
        return taskText;
    }

}
