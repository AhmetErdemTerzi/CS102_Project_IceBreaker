package com.example.icebreak;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OutdoorEvent extends Event{

    double[] coordinates;

    public OutdoorEvent(String gameType, boolean isEventOfficial, String dateTime, String lobbyCode, int numOfPlayers){
        super(gameType,isEventOfficial,dateTime,numOfPlayers);//LOBBY CODE SİLİNDİ
        coordinates = new double[2];
    }

    public void setCoordinates(double[] coordinates)
    {
        this.coordinates = coordinates;
    }

    public double[] getPlace()
    {
        return coordinates;
    }
}
