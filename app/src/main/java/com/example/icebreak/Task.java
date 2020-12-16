package com.example.icebreak;

import java.util.Timer;
import java.util.TimerTask;

public interface Task {
    public void setAndStartTimer(int seconds);
    public Timer getTime();
    public String getTaskText();
    public void taskOver();


}
