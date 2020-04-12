package com.heig.atmanager.tasks;

import com.heig.atmanager.taskLists.TaskList;

import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task object
 */
public class Task {
    private String title;
    private String description;
    private boolean done;
    private boolean favorite;
    private Date dueDate;
    private Date doneDate;
    private Date reminderDate;

    public Task(String title, String description) {
        this(title, description, null, false);
    }

    public Task(String title, String description, Date dueDate) {
        this(title, description, dueDate, false);
    }

    public Task(String title, String description, boolean favorite) {
        this(title, description, null, favorite);
    }

    public Task(String title, String description, Date dueDate, TaskList taskList) {
        this(title, description, dueDate, false);
    }

    public Task(String title, String description, Date dueDate, boolean favorite) {
        this.title       = title;
        this.description = description;
        this.dueDate     = dueDate;
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

    public void setDone(boolean status) {
        this.done = status;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
