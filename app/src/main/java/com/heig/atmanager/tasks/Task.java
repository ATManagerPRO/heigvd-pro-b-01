package com.heig.atmanager.tasks;

import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task object
 */
public class Task {

    private long id;
    private String title;
    private String description;
    private boolean done;
    private boolean favorite;
    private Date dueDate;
    private Date doneDate;
    private Date reminderDate;

    public Task(long id, String title, String description, boolean done, boolean favorite,
                Date dueDate, Date doneDate, Date reminderDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
        this.favorite = favorite;
        this.dueDate = dueDate;
        this.doneDate = doneDate;
        this.reminderDate = reminderDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean status) {
        this.done = status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
