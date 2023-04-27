package com.example.platforma_ctf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ctf.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, password TEXT, score INTEGER)";
        String CREATE_CHALLENGES_TABLE = "CREATE TABLE challenges (id INTEGER PRIMARY KEY, title TEXT, description TEXT, flag TEXT, points INTEGER)";
        String CREATE_PROGRESS_TABLE = "CREATE TABLE progress (user_id INTEGER, challenge_id INTEGER, FOREIGN KEY(user_id) REFERENCES users(id), FOREIGN KEY(challenge_id) REFERENCES challenges(id), PRIMARY KEY(user_id, challenge_id))";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CHALLENGES_TABLE);
        db.execSQL(CREATE_PROGRESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS challenges");
        db.execSQL("DROP TABLE IF EXISTS progress");
        onCreate(db);
    }
}
