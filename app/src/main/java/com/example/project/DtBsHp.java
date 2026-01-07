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

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
