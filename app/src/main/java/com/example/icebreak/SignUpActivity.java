package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    EditText email, password, passwordAgain, username;
    Button signupButton, goBackButton;
    ValidateInput validateInput;
    String userName;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText)this.findViewById(R.id.email);
        password = (EditText)this.findViewById(R.id.password);
        passwordAgain = (EditText)this.findViewById(R.id.passwordAgain);
        username = (EditText) this.findViewById(R.id.username);
        signupButton = (Button)this.findViewById(R.id.signupButton);
        goBackButton = (Button) this.findViewById(R.id.btnGoBack);

        validateInput = new ValidateInput(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpAction();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void signUpAction() {
        String mail = email.getText().toString();
        String pw = password.getText().toString();
        String pw_again = passwordAgain.getText().toString();
        userName = username.getText().toString();

        if(userName.isEmpty())
            Toast.makeText(SignUpActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
        else {
            if (validateInput.isEmailValid(mail) && validateInput.isPasswordValid(pw)) {

                if (pw.equals(pw_again)) {
                    mAuth.createUserWithEmailAndPassword(mail, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignUpActivity.this, "Signed up successfully: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                saveUsernameFirebaseDatabase(mAuth.getCurrentUser()); //FirebaseUser inside method
                            } else
                                Toast.makeText(SignUpActivity.this, "ERROR " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void saveUsernameFirebaseDatabase(FirebaseUser user) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference rootReference = firebaseDatabase.getReference();

        DatabaseReference usernameReference = rootReference.child("Users").child(user.getUid()).child("Username");
        usernameReference.setValue(userName);
        //TODO Add other instances to user database
        rootReference.child("Users").child(user.getUid()).child("isAdmin").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Available").setValue(true);
        rootReference.child("Users").child(user.getUid()).child("isLobbyLeader").setValue(false);
        rootReference.child("Users").child(user.getUid()).child("Win Count").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Lose Count").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Average Points").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Current Point").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Outdoor").child("outdoorRequestReceived").setValue(false);
        rootReference.child("Users").child(user.getUid()).child("Outdoor").child("senderUID").setValue("");
        rootReference.child("Users").child(user.getUid()).child("Outdoor").child("Response").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Kicked").setValue(false);

        //currentPoint - Avg.Point- number of games played in firestore.
        Map<String, Object> data = new HashMap<>();
        data.put("averagePoint", 0.0);
        data.put("currentPoint", 0);
        data.put("numOfGamesPlayed", 0);
        FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).set(data);
    }
}