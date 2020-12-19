package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notifActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUser, btnPlay, btnNotifications, btnSEND, del2, del3, del4;
    TextView notif1, notif2, notif3, notif4;
    ArrayList<String> notifications;
    DatabaseReference databaseReference;

    int numOfNotifications;
    boolean admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        btnUser = (Button) this.findViewById(R.id.btnUser);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNotifications = (Button) this.findViewById(R.id.btnNotifications);
        btnSEND = (Button) this.findViewById(R.id.SEND);

        notif1 = (TextView) this.findViewById(R.id.notif1);
        notif2 = (TextView) this.findViewById(R.id.notif2);
        notif3 = (TextView) this.findViewById(R.id.notif3);
        notif4 = (TextView) this.findViewById(R.id.notif4);

        del2 = (Button) this.findViewById(R.id.del2);
        del3 = (Button) this.findViewById(R.id.del3);
        del4 = (Button) this.findViewById(R.id.del4);



        notifications = new ArrayList<String>();

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( notifActivity.this, UserTab.class);
                startActivity(intent);
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( notifActivity.this, playTabActivity.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserTab.userClass.getUID().toString()).child("isAdmin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals("1")){
                    btnSEND.setEnabled(true);
                    del2.setVisibility(View.VISIBLE);
                    del3.setVisibility(View.VISIBLE);
                    del4.setVisibility(View.VISIBLE);
                    admin = true;
                }
                else {
                    btnSEND.setEnabled(false);
                    del2.setVisibility(View.GONE);
                    del3.setVisibility(View.GONE);
                    del4.setVisibility(View.GONE);
                    admin = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSEND.setOnClickListener(this);
        del2.setOnClickListener(this);
        del3.setOnClickListener(this);
        del4.setOnClickListener(this);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        getNotifications();


    }

    public void getNotifications(){
        databaseReference.child("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notifications = new ArrayList<String>();
                numOfNotifications = (int) snapshot.getChildrenCount();

                if(admin)
                    ((AdminUser) UserTab.userClass).setNumOfNotifications(numOfNotifications);

                for(DataSnapshot child : snapshot.getChildren()){
                    notifications.add(child.getValue(String.class));
                    System.out.println("eleman: " + child.getValue(String.class));
                }
                if(notifications.size() == 1) {
                    notif2.setText(notifications.get(numOfNotifications-1));
                }
                else if(notifications.size() == 2) {
                    notif2.setText(notifications.get(numOfNotifications-1));
                    notif3.setText(notifications.get(numOfNotifications-2));
                }else if(notifications.size() >= 3) {
                    notif2.setText(notifications.get(numOfNotifications-1));
                    notif3.setText(notifications.get(numOfNotifications-2));
                    notif4.setText(notifications.get(numOfNotifications-3));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.del2 ){
            if(numOfNotifications >=1) {
                ((AdminUser) UserTab.userClass).deleteNotification(numOfNotifications);
                numOfNotifications--;
            }
        }
        else if (v.getId() == R.id.del3){
            if(numOfNotifications>=2) {
                ((AdminUser) UserTab.userClass).deleteNotification(numOfNotifications - 1);
                numOfNotifications--;
            }
        }
        else if (v.getId() == R.id.del4){
            if(numOfNotifications>=3) {
                ((AdminUser) UserTab.userClass).deleteNotification(numOfNotifications - 2);
                numOfNotifications--;
            }
        }
        else if(v.getId() == R.id.SEND){
            if(!notif1.getText().equals(""))
                ((AdminUser) UserTab.userClass).setNotifications( notif1.getText().toString() );

        }

        if(numOfNotifications==1){
            notif3.setText("");
            notif4.setText("");
        }
        else if(numOfNotifications==2){

            notif4.setText("");
        }
        else if(numOfNotifications==0){
            notif2.setText("");
            notif3.setText("");
            notif4.setText("");
        }


    }
}