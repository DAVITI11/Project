package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "DataBase.db";
    public DataBaseHelper(Context context) {
        super(context, DataBaseName, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE UserInfo(FirstName TEXT, LastName TEXT, Email TEXT, Number TEXT);";
        db.execSQL(sql);
        sql="CREATE TABLE User(Name TEXT, Password TEXT);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public Boolean CheckUser(String nm,String pss){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM UserInfo WHERE Name = ? AND Password = ?",
                new String[]{nm,pss});
        return cursor.getCount() > 0;
    }
    public void AddNewUser(String fn,String ln,String em,String num){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO UserInfo VALUES('"+fn+"','"+ln+"','"+em+"','"+num+"');");
        db.close();
    }
    public void AddUser(String nm,String pss){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO User VALUES('"+nm+"','"+pss+"');");
        db.close();
    }
}
