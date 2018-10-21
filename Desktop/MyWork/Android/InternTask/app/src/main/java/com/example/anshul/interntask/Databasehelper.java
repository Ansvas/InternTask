package com.example.anshul.interntask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="register";
    public static final String COL_1="USER_ID";
    public static final String COL_2="USER_NAME";
    public static final String COL_3="EMAIL";
    public static final String COL_4="PASSWORD";

    public static final String TABLE_NAMEL="setLocation";
    public static final String COL_1L="USER_ID";
    public static final String COL_2L="Latcor";
    public static final String COL_3L="Longcor";
    int USER_ID;


    public Databasehelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,USER_NAME TEXT,EMAIL TEXT,PASSWORD TEXT)");
        db.execSQL("create table " + TABLE_NAMEL + "(USER_ID INTEGER,Latcor double,Longcor double,CONSTRAINT fk_USER_ID FOREIGN KEY (USER_ID) REFERENCES register(USER_ID))");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAMEL);
        onCreate(db);

    }


    public Cursor alldata(){
        SQLiteDatabase db= getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from register",null );
        return cursor;
    }

    public boolean emailpass(String email,String pass)
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select USER_ID from register where email=? and password=?",new String[]{email,pass});
        if(cursor.getCount()>0){
 //           USER_ID=cursor.getInt(cursor.getColumnIndex("USER_ID"));
            return true;
        }
        else
            return false;

    }


    public boolean checkmail(String email)
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from register where email=?",new String[]{email});
        if(cursor.getCount()>0){
            return false;
        }
        else
            return true;
    }
}
