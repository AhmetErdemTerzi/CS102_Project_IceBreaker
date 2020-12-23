package com.example.icebreak;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScoreBoard {

    ArrayList<User> usersInScoreBoard;
    Lobby gameLobby;
    ArrayList<User> tempList;
    User temp;
    int maxIndex;



    public ScoreBoard(Lobby gameLoby)
    {
        this.gameLobby = gameLobby;
        gameLoby.findPlayers();
        usersInScoreBoard = new ArrayList<>();
        usersInScoreBoard = gameLobby.getPlayers();
    }

    public ArrayList<User> sortingUsers(){
        sortUsers(usersInScoreBoard);
        return tempList;
    }

    public void sortUsers(ArrayList<User> bom)
    {
        maxIndex = 0;

        if(bom.size() == 0){
        }

        else {
            temp = bom.get(0);
            for (int i = 0; i < bom.size(); i++) {
                if (temp.getCurrentPoint() < bom.get(i).getCurrentPoint()) {//EMRECAN BU getScore değil getCurrentPoint olcak
                    temp = bom.get(i);
                    maxIndex = i;
                }
            }
            tempList.add(temp);
            bom.remove(maxIndex);
            sortUsers(bom);
        }
    }


}
