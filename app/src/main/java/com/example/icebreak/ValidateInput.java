package com.example.icebreak;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;


public class ValidateInput {
    Context context;

    public ValidateInput(Context context){
        this.context = context;

    }

    public boolean isEmailValid(String email){

        if(email.length() == 0){
            Toast.makeText(context,"You should enter an E-mail.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context,"You should enter a proper E-mail.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }

    public boolean isPasswordValid(String password){

        if(password.length() <= 6){
            Toast.makeText(context,"Password length should be more than 6", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }
}
