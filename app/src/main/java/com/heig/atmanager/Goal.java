package com.heig.atmanager;

import android.util.Log;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
 */
public class Goal {

    private static final String TAG = "Goal";
    
    private String unit;
    private int quantity;
    private Interval interval;
    private Date dueDate;
    private ArrayList<GoalTodo> goalTodos;

    public Goal(String unit, int quantity, Interval interval, Date dueDate) {
        this.unit     = unit;
        this.quantity = quantity;
        this.interval = interval;
        this.dueDate  = dueDate;

        // Generate the todos automatically (once) for the user
        // TODO: if there are no due dates, ...
        goalTodos = generateTodos();
    }

    private ArrayList<GoalTodo> generateTodos() {
        ArrayList<GoalTodo> goals = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Log.d(TAG, "generateTodaysTodos: GENERATING FOR " + unit + " --------------------------");
        Log.d(TAG, "generateTodaysTodos: calendar day : " + calendar.getTime());
        Log.d(TAG, "generateTodaysTodos: due date     : " + dueDate);

        // Adding new goalsTodo while it's equal or before the due date
        while(calendar.getTime().equals(dueDate) || calendar.getTime().before(dueDate)) {
            Log.d(TAG, "generateTodaysTodos: adding 1 todo for " + calendar.getTime());
            goals.add(new GoalTodo(this, 0, calendar.getTime(), dueDate));
            calendar.add(interval.getCalendarInterval(),1);
        }

        return goals;
    }

    public ArrayList<GoalTodo> getGoalTodos() {
        return goalTodos;
    }

    public ArrayList<GoalTodo> getGoalsTodoForDay(Date day) {
        Log.d(TAG, "generateTodaysTodos: FETCHING FOR " + unit + " ON " + day + " --------------------------");
        ArrayList<GoalTodo> todayGoalsTodos = new ArrayList<>();
        for(GoalTodo goalTodo : goalTodos)
            if(Utils.getDay(goalTodo.getDoneDate()) == Utils.getDay(day)) {
                Log.d(TAG, "getGoalsTodoForDay: fetching 1 todo for " + day);
                todayGoalsTodos.add(goalTodo);
            }

        return todayGoalsTodos;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public Interval getInterval() {
        return interval;
    }

}
