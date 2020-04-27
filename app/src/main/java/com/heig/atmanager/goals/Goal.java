package com.heig.atmanager.goals;

import com.heig.atmanager.Interval;
import com.heig.atmanager.Utils;

import java.time.LocalDate;
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
public class Goal {

    private static final String TAG = "Goal";
    
    private String unit;
    private int quantity;
    private int intervalNumber;
    private Interval interval;
    private Date dueDate;
    private ArrayList<GoalTodo> goalTodos;

    public Goal(String unit, int quantity, int intervalNumber, Interval interval, Date dueDate) {
        this.unit     = unit;
        this.quantity = quantity;
        this.intervalNumber = intervalNumber;
        this.interval = interval;
        this.dueDate  = dueDate;

        // Generate the todos automatically (once) for the user
        goalTodos = generateTodos();
    }

    /**
     * Generates the GoalTodo of a Goal
     * TODO : Handle no due dates
     * @return list of GoalTodo
     */
    private ArrayList<GoalTodo> generateTodos() {
        ArrayList<GoalTodo> goals = new ArrayList<>();

        Calendar calendar = getCalendarInstance();
        Calendar calendarDueDate = Calendar.getInstance();
        calendarDueDate.setTime(dueDate);

        // Adding new goalsTodo while it's equal or before the due date
        while(calendar.equals(calendarDueDate) || calendar.before(calendarDueDate)) {
            goals.add(new GoalTodo(this, 0, calendar.getTime(), dueDate));
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



}
