package com.heig.atmanager;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task object (tmp)
 */
public class Task {
    private String title;
    private String description;

    public Task(String title, String description) {
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
