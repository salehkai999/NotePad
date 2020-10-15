package com.saleh.notepad;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable,Comparable<Note> {
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

    @Override
    public int compareTo(Note o) {
        if (dateEdited.before(o.dateEdited))
            return 1;
        else if (dateEdited.after(o.dateEdited))
            return -1;
        else return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        //return super.equals(obj);
        Note objNote = (Note) obj;
        if(this.title.equals(objNote.getTitle())) {
            if(this.getDetails().equals(objNote.getDetails()))
                return true;
        }

        return false;
    }
}
