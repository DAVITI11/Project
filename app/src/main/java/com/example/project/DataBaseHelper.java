package com.example.project;

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
        String sql="CREATE TABLE UserInfo(Name TEXT, Password TEXT, FirstName TEXT, LastName TEXT, Email TEXT, Number TEXT);";
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
