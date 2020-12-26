package com.example.icebreak;

public class Event {
    Lobby lobby;
    Task currentTask;
    int numOfPlayers;
    boolean gameStatus;
    String gameType;
    String lobbyCode;
    boolean isEventOfficial;

    public Event(String gameType, boolean isEventOfficial,  int numOfPlayers)//LOBBYCODE CONSTRUCTOR'DAN SİLİNDİ.
    // TÜRETİLMEDEN NASIL İÇİNE KOYCAZ Kİ ZATEN
    {
        this.gameType = gameType;
        this.isEventOfficial = isEventOfficial;
        generateLobbyCode();
        this.numOfPlayers = numOfPlayers;

            lobby = new Lobby(isEventOfficial,  lobbyCode);
            lobby.setGameType(gameType);

        if(isEventOfficial)
            ((AdminUser) UserTab.userClass).setNotifications("OFFICIAL EVENT TIME!! JOIN LOBBY: "+ lobbyCode);

    }


    // for joiners
    public Event( Lobby lobby)
    {
        this.lobby = lobby;
        lobbyCode = lobby.getLobbyCode();
    }



    public void updatePoints()
    {

    }

    public Lobby getLobby(){
        return lobby;
    }

    public void setLobby(Lobby lobby){
        this.lobby = lobby;
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


    //Barkın'ın kodu
    public void generateLobbyCode()//generates a random String with 6 letters/numbers
    {
        String code = "";
        String all = "ABCDEFGHIJKLMONPQRSTUVWXYZ1234567890";
        for(int n = 0; n < 6; n++)
        {
            int i = (int) (all.length() * Math.random());
            code = code + all.charAt(i);
        }
        lobbyCode = code;
    }

}
