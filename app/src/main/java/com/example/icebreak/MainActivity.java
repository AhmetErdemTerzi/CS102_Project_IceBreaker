package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button button;
    TextView signUp;
    ValidateInput validateInput;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        email = (EditText)this.findViewById(R.id.email);
        password = (EditText)this.findViewById(R.id.password);
        signUp = (TextView) this.findViewById(R.id.signUp);
        button = (Button)this.findViewById(R.id.signupButton);

        button.setOnClickListener(this);
        signUp.setOnClickListener(this);

        validateInput= new ValidateInput(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.signupButton:
                //code
                loginAction();
                break;

            case R.id.signUp:
                //code
                signupAction();
                break;

        }
    }

    public void loginAction(){
        String mail = email.getText().toString();
        String pw = password.getText().toString();
        if(validateInput.isEmailValid(mail) && validateInput.isPasswordValid(pw)){
            Toast.makeText(this,"Valid email and password", Toast.LENGTH_SHORT).show();

            mAuth.signInWithEmailAndPassword(mail,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(MainActivity.this,"Logged in user " + mail, Toast.LENGTH_SHORT).show();
                        Intent mainMenu = new Intent(MainActivity.this, UserTab.class);
                        startActivity(mainMenu);
                    }
                    else{

                        Toast.makeText(MainActivity.this,"ERROR: " + task.getException(), Toast.LENGTH_LONG).show();

                    }
                }
            });

        }


    }

    public void signupAction(){
        Toast.makeText(this,"Sign up here", Toast.LENGTH_SHORT).show();
        Intent signupActivity = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signupActivity);
    }
}