package com.example.icebreak;

public class Event {
    //Lobby lobby
    Task currentTask;
    int numOfPlayers;
    boolean gameStatus;
    boolean gameType;
    String dateTime;
    String lobbyCode;
    boolean isEventOfficial;

    public Event(boolean gameType, boolean isEventOfficial, String dateTime, String lobbyCode, int numOfPlayers)
    {
        this.gameType = gameType;
        this.isEventOfficial = isEventOfficial;
        this.dateTime = dateTime;
        this.lobbyCode = lobbyCode;
        this.numOfPlayers = numOfPlayers;
    }

    public void updatePoints()
    {

    }

    public String getLobbyCode()
    {
        return lobbyCode;
    }

    public void setTask(Task task)
    {
        currentTask = task;
    }

    public void setGameStatus(boolean gameStatus)
    {
        this.gameStatus = gameStatus;
    }

}
