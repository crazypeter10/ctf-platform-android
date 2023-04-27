package com.example.platforma_ctf;

public class Challenge {
    private int id;
    private String title;
    private String description;
    private String flag;
    private int points;

    public Challenge(int id, String title, String description, String flag, int points) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.flag = flag;
        this.points = points;
    }

    // Getters and setters for the properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
