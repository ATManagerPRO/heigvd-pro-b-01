package com.heig.atmanager.goals;

import com.heig.atmanager.Utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Author : Stéphane Bottin
 * Date   : 16.03.2020
 *
 * GoalTodos are generated from a goal in order to let the user interact with his/her goals.
 * This class also helps keeping track of the user's progress.
 *
 */
public class GoalTodo {

    private static final long MILLIS_IN_HOUR  = 3600000;
    private static final long MILLIS_IN_DAY   = 86400000;

    private static final String TAG = "GoalTodo";

    private Goal goal;
    private int quantityDone;
    private Date doneDate;
    private Date dueDate;

    public GoalTodo(Goal goal, int quantityDone, Date doneDate, Date dueDate) {
        this.goal = goal;
        this.quantityDone = quantityDone;
        this.doneDate = doneDate;
        this.dueDate = dueDate;
    }

    public void addQuantity(int quantity) {
        quantityDone += quantity;
    }

    public int getPercentage() {
        return (int) (((float) quantityDone / goal.getQuantity()) * 100);
    }

    public String getUnit() {
        return goal.getUnit();
    }

    public int getQuantityDone() {
        return quantityDone;
    }

    public int getTotalQuantity() {
        return goal.getQuantity();
    }

    /**
     * Get the Timer value for the goal (how much time is left to complete it)
     * @return the timer value as a string
     */
    public String getTimerValue() {
        Calendar calendarNow = Calendar.getInstance();
        long millisLeft = doneDate.getTime() - calendarNow.getTimeInMillis();
        int timeLeft;
        String unit;

        if(millisLeft < MILLIS_IN_HOUR) {
            timeLeft = (int) TimeUnit.MILLISECONDS.toMinutes(millisLeft);
            unit = "minute";
        } else if (millisLeft < MILLIS_IN_DAY) {
            timeLeft = (int) TimeUnit.MILLISECONDS.toHours(millisLeft);
            unit = "hour";
        } else {
            timeLeft = (int) TimeUnit.MILLISECONDS.toDays(millisLeft);
            unit = "day";
        }

        return Utils.formatNumber(timeLeft) + " " + unit +
                (timeLeft > 1 ? "s" : "") + " left";
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public Date getDueDate() { return dueDate; }

    /*public LocalDate getLocalDueDate() {
        return LocalDate.of(calendarDueDate.get(Calendar.YEAR),
                calendarDueDate.get(Calendar.MONTH), calendarDueDate.get(Calendar.DAY_OF_MONTH));
    }*/

}
