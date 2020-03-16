package com.heig.atmanager;

import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task object (tmp)
 */
public class Todo {
    private String title;
    private String description;
    private boolean done;
    private Date dueDate;
    private Date doneDate;
    private Date reminderDate;

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
