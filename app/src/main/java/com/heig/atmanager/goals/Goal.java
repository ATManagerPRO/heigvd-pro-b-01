package com.heig.atmanager.goals;

import com.heig.atmanager.Interval;
import com.heig.atmanager.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 12.03.2020
 *
 * Goal of ATManager, a goal is represented with a unit, a quantity and an interval.
 * Here's some example :
 *     - 20 push-ups daily
 *     - 4 kms weekly
 *     - 5 healthy meals weekly
 *     - etc...
 *
 * The app will then generate the goals (cf GoalTodo) for the user to interact with.
 *
 */
public class Goal implements Serializable {

    // Bundle keys for goalTodo header
    public static final String TITLE_KEY       = "goal_title";
    public static final String INTERVAL_KEY    = "goal_interval";
    public static final String DATE_KEY        = "goal_date";
    public static final String SERIAL_GOAL_KEY = "goal_serial";

    private static final String TAG = "Goal";

    private long id;
    private String unit;
    private int quantity;
    private int intervalNumber;
    private Interval interval;
    private Date dueDate;
    private Date createDate;
    private ArrayList<GoalTodo> goalTodos;

    public Goal(long id, String unit, int quantity, int intervalNumber, Interval interval, Date dueDate, Date createDate) {
        this.id       = id;
        this.unit     = unit;
        this.quantity = quantity;
        this.intervalNumber = intervalNumber;
        this.interval = interval;
        this.dueDate  = dueDate;
        this.createDate = createDate;
        this.goalTodos = new ArrayList<>();
    }

    /**
     * Generates the GoalTodo of a Goal
     * TODO : Handle no due dates
     * @return list of GoalTodo
     */
    private ArrayList<GoalTodo> generateTodos() {
        ArrayList<GoalTodo> goals = new ArrayList<>();

        Calendar calendar = getCalendarInstance();

        // Adding new goalsTodo while it's equal or before the due date
        while(calendar.getTime().equals(dueDate) || calendar.getTime().before(dueDate)) {
            goals.add(new GoalTodo(-1, id, 0, calendar.getTime(), dueDate));
            calendar.add(interval.getCalendarInterval(),1);
        }

        return goals;
    }

    /**
     * Get the calendar instance for the generation of GoalTodos
     * @return Calendar set at the normalized time
     */
    private Calendar getCalendarInstance() {
        // todo : change end date to the set date
        //    (if set on a wednesday for a weekly goal it should end next wednesday)
        Calendar c = Calendar.getInstance();

        switch(interval) {
            case YEAR:
                // User has until December to finish a yearly goal
                c.set(Calendar.MONTH, 12);
            case MONTH:
                // User has until the end of the month to finish a monthly goal
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            case WEEK:
                // User has until Sunday to finish a weekly goal
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            case DAY:
                // User has until the end of the day to finish a daily goal
                c.set(Calendar.HOUR_OF_DAY, 23);
            case HOUR:
                // User has until the end of the hour to finish an hourly goal
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                break;
        }

        return c;
    }

    /**
     * Get the GoalTodo for a specific day
     * @param day : date filter
     * @return list of GoalTodo for that day
     */
    public ArrayList<GoalTodo> getGoalsTodoForDay(Date day) {
        ArrayList<GoalTodo> todayGoalsTodos = new ArrayList<>();
        for(GoalTodo goalTodo : goalTodos)
            if(Utils.getDay(goalTodo.getDoneDate()) == Utils.getDay(day)) {
                todayGoalsTodos.add(goalTodo);
            }

        return todayGoalsTodos;
    }

    /**
     *  Getters
     */

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public int getIntervalNumber() {
        return intervalNumber;
    }

    public Interval getInterval() {
        return interval;
    }

    public ArrayList<GoalTodo> getGoalTodos() {
        return goalTodos;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getOverallPercentage() {
        double percentage = 0;

        for(GoalTodo goalTodo : goalTodos)
            percentage += goalTodo.getQuantityDone();

        return percentage / goalTodos.size();
    }

    public void addGoalTodo(GoalTodo goalTodo) {
        goalTodos.add(goalTodo);
    }

    public long getId(){
        return id;
    }

    // TEMP : If we don't need to generate the left over goalTodo then we won't need this
    public long getTotalGoalTodo() {
        if(dueDate == null)
            return -1;

        // difference in milliseconds
        long diff = dueDate.getTime() - createDate.getTime();

        if(diff < 0)
            return -1;

        // Works only for daily (WIP for others)
        return (diff / (1000*60*60*24));
    }

}
