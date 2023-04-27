package com.example.platforma_ctf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class ChallengeManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private ArrayList<Challenge> challenges;

    public ChallengeManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
        this.challenges = new ArrayList<>();

        // Add some sample challenges
        addSampleChallenges();
    }

    public ArrayList<Challenge> getChallenges() {
        if (challenges.isEmpty()) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM challenges", null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String flag = cursor.getString(cursor.getColumnIndex("flag"));
                    int points = cursor.getInt(cursor.getColumnIndex("points"));

                    Challenge challenge = new Challenge(id, title, description, flag, points);
                    challenges.add(challenge);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }

        return challenges;
    }

    private void addSampleChallengeToDatabase(Challenge challenge) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", challenge.getId());
        values.put("title", challenge.getTitle());
        values.put("description", challenge.getDescription());
        values.put("flag", challenge.getFlag());
        values.put("points", challenge.getPoints());

        db.insert("challenges", null, values);
        db.close();
    }

    private void addSampleChallenges() {
        challenges.add(new Challenge(1, "Challenge 1", "This is challenge 1 description", "flag1", 100));
        challenges.add(new Challenge(2, "Challenge 2", "This is challenge 2 description", "flag2", 200));
        challenges.add(new Challenge(3, "Challenge 3", "This is challenge 3 description", "flag3", 300));
    }
    public boolean isFlagCorrect(int challengeId, String submittedFlag) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT flag FROM challenges WHERE id = ?", new String[]{String.valueOf(challengeId)});
        boolean isCorrect = false;

        if (cursor.moveToFirst()) {
            String correctFlag = cursor.getString(cursor.getColumnIndex("flag"));
            isCorrect = correctFlag.equals(submittedFlag);
        }

        cursor.close();
        db.close();

        return isCorrect;
    }
}
