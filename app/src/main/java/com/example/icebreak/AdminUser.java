package com.example.icebreak;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class AdminUser extends User {
    int numOfNotifications;


    public void respondRequest(int requestIndex, boolean response){


    }

    public void setNotifications(String notification){

        FirebaseDatabase.getInstance().getReference().child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numOfNotifications = (int) snapshot.getChildrenCount();
                FirebaseDatabase.getInstance().getReference().child("Notifications").child("Notif" + (numOfNotifications+1)).setValue(notification);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void deleteNotification(int notificationIndex){
        System.out.println("TOPLAN NOTIFICATION ELEMAN SAYISI " +numOfNotifications);
        if (notificationIndex == numOfNotifications) {
            FirebaseDatabase.getInstance().getReference().child("Notifications").child("Notif" + notificationIndex).removeValue();
        }
        else {

            for (int i = notificationIndex+1; i <= numOfNotifications;i++ ){
                int ii = i;

                FirebaseDatabase.getInstance().getReference().child("Notifications").child("Notif"+(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        FirebaseDatabase.getInstance().getReference().child("Notifications").child("Notif"+(ii-1)).setValue(snapshot.getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            FirebaseDatabase.getInstance().getReference().child("Notifications").child("Notif" + numOfNotifications).removeValue();
        }
        numOfNotifications--;

    }


    public void setNumOfNotifications(int num){
        numOfNotifications = num;
    }

    public void removePlayerFromEvent(String uid){

    }

}
