package com.example.platforma_ctf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

    public class UserManager {
        private DatabaseHelper dbHelper;
        private User currentUser;

        public UserManager(Context context) {
            dbHelper = new DatabaseHelper(context);
        }

        public boolean registerUser(String username, String password) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            values.put("score", 0); // Set initial score to 0

            long result = db.insert("users", null, values);
            db.close();

            return result != -1;
        }

        public boolean authenticateUser(String username, String password) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] columns = {"id", "username", "password", "score"};
            String selection = "username = ? AND password = ?";
            String[] selectionArgs = {username, password};

            Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
            boolean isAuthenticated = cursor.getCount() > 0;

            if (isAuthenticated) {
                cursor.moveToFirst();
                currentUser = new User(
                        cursor.getString(cursor.getColumnIndex("username")),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getInt(cursor.getColumnIndex("score"))
                );
            }

            cursor.close();
            db.close();

            return isAuthenticated;
        }

        public void updateUserScore(String username, int challengeId, int points) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            int userId = getUserIdByUsername(username);
            if (userId == -1) {
                Log.d("UserManager", "updateUserScore: User not found");
                db.close();
                return;
            }

            String[] columns = {"score"};
            String selection = "username = ?";
            String[] selectionArgs = {username};

            Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                int currentScore = cursor.getInt(cursor.getColumnIndex("score"));

                // Check if user has already solved the challenge
                String[] progressColumns = {"user_id", "challenge_id"};
                String progressSelection = "user_id = ? AND challenge_id = ?";
                String[] progressSelectionArgs = {String.valueOf(userId), String.valueOf(challengeId)};
                Cursor progressCursor = db.query("progress", progressColumns, progressSelection, progressSelectionArgs, null, null, null);
                if (progressCursor.getCount() > 0) {
                    Log.d("UserManager", "updateUserScore: Challenge already solved by user");
                    progressCursor.close();
                    db.close();
                    return;
                }
                progressCursor.close();

                int newScore = currentScore + points;

                ContentValues values = new ContentValues();
                values.put("score", newScore);

                SQLiteDatabase dbWritable = dbHelper.getWritableDatabase();
                int rowsUpdated = dbWritable.update("users", values, selection, selectionArgs);
                dbWritable.close();

                Log.d("UserManager", "updateUserScore: Updated score for " + username + ": " + newScore + ", Rows updated: " + rowsUpdated);
            } else {
                Log.d("UserManager", "updateUserScore: User not found");
            }

            cursor.close();
            db.close();
        }


        public User getCurrentUser() {
            return currentUser;
        }

        public ArrayList<User> getLeaderboardUsers() {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            ArrayList<User> leaderboardUsers = new ArrayList<>();

            String query = "SELECT users.id, users.username,score " +
                    "FROM users " +
                    "JOIN progress ON users.id = progress.user_id " +
                    "GROUP BY users.id, users.username " +
                    "ORDER BY score DESC";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    String username = cursor.getString(cursor.getColumnIndex("username"));
                    int score = cursor.getInt(cursor.getColumnIndex("score"));

                    User user = new User(username, null, score);
                    leaderboardUsers.add(user);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

            return leaderboardUsers;
        }


        public int getUserIdByUsername(String username) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] columns = {"id"};
            String selection = "username = ?";
            String[] selectionArgs = {username};

            Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

            int userId = -1;
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex("id"));
            }

            cursor.close();

            return userId;
        }
        public boolean saveChallengeProgress(String username, int challengeId) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            boolean success = false;
            try {
                int userId = getUserIdByUsername(username);
                if (userId == -1) {
                    Log.d("UserManager", "saveChallengeProgress: User not found");
                    return false;
                }

                String[] columns = {"user_id", "challenge_id"};
                String selection = "user_id = ? AND challenge_id = ?";
                String[] selectionArgs = {String.valueOf(userId), String.valueOf(challengeId)};
                Cursor cursor = db.query("progress", columns, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    Log.d("UserManager", "saveChallengeProgress: Challenge already solved by user");
                    cursor.close();
                    return false;
                }
                cursor.close();

                ContentValues values = new ContentValues();
                values.put("user_id", userId);
                values.put("challenge_id", challengeId);
                long result = db.insert("progress", null, values);

                success = result != -1;
                Log.d("UserManager", "saveChallengeProgress: " + (success ? "Success" : "Failed"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
            return success;
        }

    }
