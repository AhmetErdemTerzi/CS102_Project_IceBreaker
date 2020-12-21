package com.example.icebreak;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdminUser extends User{
    int numOfNotifications, numOfSuggestions;
    FirebaseFirestore db;
    String strSug;

    public void respondRequest(int requestIndex, boolean response){

        if(response == false) {

            System.out.println("TOPLAN SUGGESTION ELEMAN SAYISI " + numOfSuggestions);
            if (requestIndex == numOfSuggestions) {
                FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug" + requestIndex).removeValue();
            } else {

                for (int i = requestIndex + 1; i <= numOfSuggestions; i++) {
                    int ii = i;

                    FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug" + (i)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug" + (ii - 1)).setValue(snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug" + numOfSuggestions).removeValue();
            }
            numOfSuggestions--;
        }
        else{
            db = FirebaseFirestore.getInstance();

            FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug" + requestIndex).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    strSug = snapshot.getValue().toString();
                    Map<String, Object> data = new HashMap<>();
                    data.put("objective", strSug );
                    db.collection("Missions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            int docCount = task.getResult().size();
                            db.collection("Missions").document("Mission" + (docCount + 1)).set(data);

                        }
                    });
                    respondRequest(requestIndex, false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


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
    public void setNumOfSuggestions(int num){
        numOfSuggestions = num;
    }

    public void removePlayerFromEvent(String uid){

    }

}
