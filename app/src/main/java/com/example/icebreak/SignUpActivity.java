package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class SignUpActivity extends AppCompatActivity {

    EditText email, password, passwordAgain, username;
    Button signupButton;
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

        validateInput = new ValidateInput(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpAction();
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
            Toast.makeText(SignUpActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "pw dont match", Toast.LENGTH_SHORT).show();

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
        rootReference.child("Users").child(user.getUid()).child("Available").setValue(false);
        rootReference.child("Users").child(user.getUid()).child("isLobbyLeader").setValue(false);
        rootReference.child("Users").child(user.getUid()).child("Win Count").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Lose Count").setValue(0);
        rootReference.child("Users").child(user.getUid()).child("Average Points").setValue(0);

    }
}