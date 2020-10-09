package com.saleh.notepad;

import java.util.Date;

public class Note {
    private String title;
    private String details;
    private Date dateEdited;

    public Note() {
    }

    public Note(String title, String details, Date dateEdited) {
        this.title = title;
        this.details = details;
        this.dateEdited = dateEdited;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", dateEdited=" + dateEdited +
                '}';
    }
}
