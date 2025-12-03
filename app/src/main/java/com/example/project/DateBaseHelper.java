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

public class DateBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private String databasePath;

    public DateBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
        copyDatabaseIfNeeded();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Do nothing because we are using pre-populated database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Optional: handle database upgrade if needed
    }

    // Copy database from assets folder to internal storage
    private void copyDatabaseIfNeeded() {
        File dbFile = new File(databasePath);
        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();
            try (InputStream input = context.getAssets().open(DATABASE_NAME);
                 FileOutputStream output = new FileOutputStream(dbFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                output.flush();

            } catch (IOException e) {
                throw new RuntimeException("Error copying database", e);
            }
        }
    }

    // Open database manually (optional)
    public SQLiteDatabase openDatabase() throws SQLException {
        return SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    // Check user credentials
    public boolean CheckUser(String us, String pass) {
        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Users WHERE user = us  AND password = pass",
                new String[]{us, pass}
        );

        boolean exists = cursor.moveToFirst(); // true if user found
        cursor.close();
        db.close();
        return exists;
    }
}
