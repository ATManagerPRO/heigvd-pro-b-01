package com.heig.atmanager.tasks;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.time.LocalDate;
import java.util.Calendar;
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
    private Calendar calendarDueDate;
    private Date reminderDate;
    private String directory;

    public Task(String title, String description) {
        this(title, description, null, null, false);
    }

    public Task(String title, String description, Calendar calendarDueDate) {
        this(title, description, calendarDueDate, null, false);
    }

    public Task(String title, String description, boolean favorite) {
        this(title, description, null,null, favorite);
    }

    public Task(String title, String description, Calendar calendarDueDate, String directory) {
        this(title, description, calendarDueDate, directory, false);
    }

    public Task(String title, String description, Calendar calendarDueDate, String directory, boolean favorite) {
        this.title       = title;
        this.description = description;
        this.calendarDueDate     = calendarDueDate;
        this.directory   = directory;
        this.favorite    = favorite;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setCalendarDueDate(Calendar calendarDueDate) {
        this.calendarDueDate = calendarDueDate;
    }

    public Date getDueDate() {
        if(calendarDueDate == null)
            return null;

        return calendarDueDate.getTime();
    }

    public LocalDate getLocalDueDate() {
        if(calendarDueDate == null)
            return null;

        return LocalDate.of(calendarDueDate.get(Calendar.YEAR),
                calendarDueDate.get(Calendar.MONTH), calendarDueDate.get(Calendar.DAY_OF_MONTH));
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

    public void setDone(boolean status) {
        this.done = status;
    }

    public boolean isFavorite() {
        return favorite;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                ", favorite=" + favorite +
                ", calendarDueDate=" + (calendarDueDate == null ? "none" : calendarDueDate.get(Calendar.DAY_OF_MONTH) + "/" + calendarDueDate.get(Calendar.MONTH) + "/" + calendarDueDate.get(Calendar.YEAR)) +
                ", reminderDate=" + reminderDate +
                ", directory='" + directory + '\'' +
                '}';
    }
}
