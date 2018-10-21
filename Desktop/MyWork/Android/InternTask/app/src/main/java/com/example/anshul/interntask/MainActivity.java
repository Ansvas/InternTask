package com.example.anshul.interntask;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    Databasehelper dbb;
    public Button login;
    public Button textsignup;
    EditText textemail, textpass;;
 //   int USER_ID;
    String entered_email,entered_pass;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        login = (Button)findViewById(R.id.buttonSignIn);
        textsignup = (Button)findViewById(R.id.btngotoSignup);
        textemail = (EditText)findViewById(R.id.editTextEmailLogin);
        textpass = (EditText)findViewById(R.id.editTextPasswordLogin);


        dbb=new Databasehelper(this);


//action on login button

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                entered_email=textemail.getText().toString();
                entered_pass=textpass.getText().toString();




                Log.d("Email",textemail.getText().toString());
                if (!isValidEmail(textemail.getText().toString()))
                    Toast.makeText(MainActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();




                if (entered_email.matches("") || entered_pass.matches("")) {

                     Toast.makeText(getApplicationContext(),"fields cant be null",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (dbb.emailpass(entered_email,entered_pass)) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "SignIn failed!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        //action on signup button


        textsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });
    }

    //define some methods

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
