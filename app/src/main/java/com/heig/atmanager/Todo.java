package com.heig.atmanager;

import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task object
 */
public class Todo {
    private String title;
    private String description;
    private boolean done;
    private boolean favorite;
    private Date dueDate;
    private Date doneDate;
    private Date reminderDate;
    private String directory;


    public Todo(String title, String description) {
        this(title, description, null, null, false);
    }

    public Todo(String title, String description, Date dueDate) {
        this(title, description, dueDate, null, false);
    }

    public Todo(String title, String description, boolean favorite) {
        this(title, description, null,null, favorite);
    }

    public Todo(String title, String description, Date dueDate, String directory) {
        this(title, description, dueDate, directory, false);
    }

    public Todo(String title, String description, Date dueDate, String directory, boolean favorite) {
        this.title       = title;
        this.description = description;
        this.dueDate     = dueDate;
        this.directory   = directory;
        this.favorite    = favorite;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
