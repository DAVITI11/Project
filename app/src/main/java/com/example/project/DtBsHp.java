package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DtBsHp extends SQLiteOpenHelper {

    final static String DataBaseName="";
    final static int DataBaseVersion=1;
    String sql;
    public DtBsHp(Context context){
        super(context, DataBaseName,null,DataBaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT UNIQUE, password TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE items (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, category TEXT, type TEXT, location TEXT, date TEXT, image TEXT, user_id INTEGER, status TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }
}
