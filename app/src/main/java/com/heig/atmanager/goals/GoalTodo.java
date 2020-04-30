package com.heig.atmanager.goals;

import com.heig.atmanager.MainActivity;
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

    private long goal_id;
    private int quantityDone;
    private Date doneDate;
    private Date dueDate;

    public GoalTodo(long goal_id, int quantityDone, Date doneDate, Date dueDate) {
        this.goal_id = goal_id;
        this.quantityDone = quantityDone;
        this.doneDate = doneDate;
        this.dueDate = dueDate;
    }

    public void addQuantity(int quantity) {
        quantityDone += quantity;
    }

    public int getPercentage() {
        return (int) (((float) quantityDone / MainActivity.getUser().getGoal(goal_id).getQuantity()) * 100);
    }

    public String getUnit() {
        return MainActivity.getUser().getGoal(goal_id).getUnit();
    }

    public int getQuantityDone() {
        return quantityDone;
    }

    public int getTotalQuantity() {
        return MainActivity.getUser().getGoal(goal_id).getQuantity();
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

    public Date getDueDate() {
        return dueDate;
    }

}
