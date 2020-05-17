package com.heig.atmanager.tasks;

import com.heig.atmanager.MainActivity;
import com.heig.atmanager.taskLists.TaskList;

import java.util.ArrayList;
import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task object
 */
public class Task {

    private static final long UNDEFINED_ID = -1;

    private long id;
    private String title;
    private String description;
    private boolean done;
    private boolean favorite;
    private Date dueDate;
    private Date doneDate;
    private Date reminderDate;
    private TaskList tasklist;
    private ArrayList<String> tags;

    public Task(String title, String description) {
        this(UNDEFINED_ID, title, description, false, false, null, null, null);
    }

    public Task(String title, String description, Date dueDate) {
        this(UNDEFINED_ID, title, description, false, false, dueDate, null, null);
    }

    public Task(String title, String description, boolean favorite) {
        this(UNDEFINED_ID, title, description, false, favorite, null, null, null);
    }

    public Task(long id, String title, String description, boolean done, boolean favorite,
                Date dueDate, Date doneDate, Date reminderDate) {
        this.id           = id;
        this.title        = title;
        this.description  = description;
        this.done         = done;
        this.favorite     = favorite;
        this.dueDate      = dueDate;
        this.doneDate     = doneDate;
        this.reminderDate = reminderDate;
        this.tags         = new ArrayList<>();
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

    public boolean isDone() {
        return done;
    }

    public void setTasklist(TaskList tasklist){
        this.tasklist = tasklist;
    }

    public TaskList getTasklist() {
        return tasklist;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public void addTag(String tag) {
        // Add the tag to the task and the user
        tags.add(tag);
        MainActivity.getUser().addTag(tag);
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public Date getDoneDate() {
        return doneDate;
    }
}
