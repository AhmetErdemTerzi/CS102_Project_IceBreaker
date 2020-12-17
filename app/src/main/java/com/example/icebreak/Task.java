package com.example.icebreak;

import java.util.Timer;
import java.util.TimerTask;

public interface Task {
    void setAndStartTimer(int seconds);
    Timer getTime();
    String getTaskText();
    void taskOver();


}
