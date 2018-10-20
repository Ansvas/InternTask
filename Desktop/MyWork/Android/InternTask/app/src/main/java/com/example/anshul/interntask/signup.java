package com.example.anshul.interntask;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Databasehelper dh;
    Button btnsignup;
    Button gotosignin;
    EditText txtname,txtemail,txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        openHelper=new Databasehelper(this);
        dh=new Databasehelper(this);
        txtname=(EditText)findViewById(R.id.editTextName);
        txtemail=(EditText)findViewById(R.id.editTextEmail);
        txtpassword=(EditText)findViewById(R.id.editTextPassword);
        btnsignup=(Button) findViewById(R.id.buttonSignup);
        gotosignin=(Button) findViewById(R.id.btngotoSignin);


        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });




        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String name=txtname.getText().toString();
                String email=txtemail.getText().toString();
                String pass=txtpassword.getText().toString();


                if(name==""||email==""||pass=="")
                {
                    Toast.makeText(getApplicationContext(),"fields cant be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    if(dh.checkmail(email))
                    {
                        Toast.makeText(getApplicationContext(),"email already exist",Toast.LENGTH_LONG).show();
                    }


                    insertdata(name, email, pass);
                    Toast.makeText(getApplicationContext(), "register successful", Toast.LENGTH_LONG).show();
                }
            }



        });


    }

    public void insertdata(String name,String email,String pass)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(Databasehelper.COL_2,name);
        contentValues.put(Databasehelper.COL_3,email);
        contentValues.put(Databasehelper.COL_4,pass);

        long id= db.insert(Databasehelper.TABLE_NAME,null,contentValues);
        if(id==-1)
        {
            Toast.makeText(getApplicationContext(), "something goes wrong, values not inserted", Toast.LENGTH_LONG).show();
        }

    }

}
