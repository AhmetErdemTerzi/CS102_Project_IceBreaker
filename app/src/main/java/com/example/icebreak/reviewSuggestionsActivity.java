package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class reviewSuggestionsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUser, btnPlay, btnNotifications, userInfo,revSug,
            accept1, accept2, accept3, accept4,
            dec1,dec2,dec3,dec4;
    TextView notif1, notif2, notif3, notif4;
    LinearLayout review1, review2, review3, review4;
    ArrayList<String> suggestions;
    int numOfSuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_review_suggestions);

        userInfo= (Button) this.findViewById(R.id.userInfo);
        revSug= (Button) this.findViewById(R.id.revSug);

        review1 = (LinearLayout) this.findViewById(R.id.review1) ;
        review2 = (LinearLayout) this.findViewById(R.id.review2) ;
        review3 = (LinearLayout) this.findViewById(R.id.review3) ;
        review4 = (LinearLayout) this.findViewById(R.id.review4) ;

        accept1 = (Button) this.findViewById(R.id.accept1);
        dec1 = (Button) this.findViewById(R.id.dec1);
        accept2 = (Button) this.findViewById(R.id.accept2);
        dec2= (Button) this.findViewById(R.id.dec2);
        accept3 = (Button) this.findViewById(R.id.accept3);
        dec3 = (Button) this.findViewById(R.id.dec3);
        accept4 = (Button) this.findViewById(R.id.accept4);
        dec4 = (Button) this.findViewById(R.id.dec4);

        notif1 = (TextView) this.findViewById(R.id.notif1);
        notif2 = (TextView) this.findViewById(R.id.notif2);
        notif3 = (TextView) this.findViewById(R.id.notif3);
        notif4 = (TextView) this.findViewById(R.id.notif4);
        notif1.setTextColor(Color.BLACK);
        notif2.setTextColor(Color.BLACK);
        notif3.setTextColor(Color.BLACK);
        notif4.setTextColor(Color.BLACK);

        accept1.setOnClickListener(this);
        accept2.setOnClickListener(this);
        accept3.setOnClickListener(this);
        accept4.setOnClickListener(this);

        dec1.setOnClickListener(this);
        dec2.setOnClickListener(this);
        dec3.setOnClickListener(this);
        dec4.setOnClickListener(this);

        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reviewSuggestionsActivity.this, UserTab.class);
                startActivity(intent);
            }
        });

        btnUser = (Button) this.findViewById(R.id.btnUser);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNotifications = (Button) this.findViewById(R.id.btnNotifications);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( reviewSuggestionsActivity.this, playTabActivity.class);
                startActivity(intent);
            }
        });


        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( reviewSuggestionsActivity.this, notifActivity.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug0").setValue("");
        getSuggestions();



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.accept1 ){
            if(numOfSuggestions >=1) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions, true);
                notif1.setText("");
                numOfSuggestions--;
            }
        }
        else if(v.getId() == R.id.accept2){
            if(numOfSuggestions >=2) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions-1, true);
                notif2.setText("");
                numOfSuggestions--;
            }
        }
        else if(v.getId() == R.id.accept3){
            if(numOfSuggestions >=3) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions-2, true);
                notif3.setText("");
                numOfSuggestions--;
            }
        }
        else if(v.getId() == R.id.accept4){
            if(numOfSuggestions >=4) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions-3, true);
                notif4.setText("");
                numOfSuggestions--;
            }
        }
        else if(v.getId() == R.id.dec1){
            if(numOfSuggestions >=1) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions, false);
                notif1.setText("");
                numOfSuggestions--;
            }
        }
        else if(v.getId() == R.id.dec2){
            if(numOfSuggestions >=2) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions - 1, false);
                notif2.setText("");
                numOfSuggestions--;
            }
        }
        else if(v.getId() == R.id.dec3){
            if(numOfSuggestions >=3) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions - 2, false);
                notif3.setText("");
                numOfSuggestions--;
            }
        }
        else if(v.getId() == R.id.dec4){
            if(numOfSuggestions >=4) {
                ((AdminUser) UserTab.userClass).respondRequest(numOfSuggestions - 3, false);
                notif4.setText("");
                numOfSuggestions--;
            }
        }

    }

    public void getSuggestions(){
        FirebaseDatabase.getInstance().getReference().child("Suggestions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suggestions = new ArrayList<String>();
                numOfSuggestions = (int) snapshot.getChildrenCount();

                ((AdminUser) UserTab.userClass).setNumOfSuggestions(numOfSuggestions);
                for(DataSnapshot child : snapshot.getChildren()){
                    suggestions.add(child.getValue(String.class));
                    System.out.println("eleman: " + child.getValue(String.class));
                }
                if(suggestions.size() == 1) {
                    notif1.setText(suggestions.get(numOfSuggestions-1));
                    notif2.setText("");
                    notif3.setText("");
                    notif4.setText("");
                }
                else if(suggestions.size() == 2) {
                    notif1.setText(suggestions.get(numOfSuggestions-1));
                    notif2.setText(suggestions.get(numOfSuggestions-2));
                    notif3.setText("");
                    notif4.setText("");
                }else if(suggestions.size() == 3) {
                    notif1.setText(suggestions.get(numOfSuggestions-1));
                    notif2.setText(suggestions.get(numOfSuggestions-2));
                    notif3.setText(suggestions.get(numOfSuggestions-3));
                    notif4.setText("");
                }else if(suggestions.size() == 4) {
                    notif1.setText(suggestions.get(numOfSuggestions-1));
                    notif2.setText(suggestions.get(numOfSuggestions-2));
                    notif3.setText(suggestions.get(numOfSuggestions-3));
                    notif4.setText(suggestions.get(numOfSuggestions-4));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Suggestions").child("Sug0").removeValue();
    }

    public void onBackPressed() {
    }

}