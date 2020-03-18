package com.heig.atmanager;

import android.util.Log;

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
 * DoneDate : date the goal needs to be done, will change depending on the interval :
 *       Interval | Displayed if it is equal to...
 *     - Day      | the same day               (12.4.2020)
 *     - Week     | the first day of the week  (Monday)
 *     - Month    | the first day of the Month (1st)
 *     - Year     | the year                   (2020)
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
        Log.d(TAG, "GoalTodo: GoalTodo generated ----------------------------------");
        Log.d(TAG, "\t> goal         : " + goal.getUnit() + " (" + goal.getInterval() + ")");
        Log.d(TAG, "\t> quantityDone : " + quantityDone);
        Log.d(TAG, "\t> doneDate     : " + doneDate);
        Log.d(TAG, "\t> dueDate      : " + dueDate);
        this.goal = goal;
        this.quantityDone = quantityDone;
        this.doneDate = doneDate;
        this.dueDate = dueDate;
    }

    public void addQuantity(int quantity) {
        quantityDone += quantity;
    }

    public void removeQuantity(int quantity) {
        quantityDone += quantity;
    }

    public int getPercentage() {
        return (int) (((float) quantityDone / goal.getQuantity()) * 100);
    }

    public String getUnit() {
        return goal.getUnit();
    }

    public String getStringCurrent() {
        return Utils.formatNumber(quantityDone);
    }

    public String getStringTotal() {
        return Utils.formatNumber(goal.getQuantity());
    }

    public String getTimerValue() {
        Calendar calendarNow = Calendar.getInstance();
        long millisLeft = doneDate.getTime() - calendarNow.getTimeInMillis();
        int timeLeft = -1;
        String unit = "";

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

}
