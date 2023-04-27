package com.example.platforma_ctf;


import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int score;
    private ArrayList<Integer> solvedChallenges;

    public User(String username, String password, int score) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.solvedChallenges = new ArrayList<>();

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public ArrayList<Integer> getSolvedChallenges() {
        return solvedChallenges;
    }

    public void setSolvedChallenges(ArrayList<Integer> solvedChallenges) {
        this.solvedChallenges = solvedChallenges;
    }

    public void addSolvedChallenge(int challengeId) {
        this.solvedChallenges.add(challengeId);
    }
}
