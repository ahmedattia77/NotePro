package com.example.notepro;

public class Note {

private String title;
private String description;
private String date;

    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}