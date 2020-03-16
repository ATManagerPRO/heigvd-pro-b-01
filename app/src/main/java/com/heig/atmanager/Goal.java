package com.heig.atmanager;

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

    private String unit;
    private int quantity;
    private Interval interval;
    private Date dueDate;

    public Goal(String unit, int quantity, Interval interval, Date dueDate) {
        this.unit     = unit;
        this.quantity = quantity;
        this.interval = interval;
        this.dueDate  = dueDate;

        // Generate the todos automatically (once) for the user
        // TODO: rem : if there are no due dates, ...
        generateTodos();
    }

    public ArrayList<GoalTodo> generateTodos() {
        ArrayList<GoalTodo> goals = new ArrayList<>();

        /*
        PSEUDO-CODE :
        while(today + interval < due date) {
            Add new goal to list with today + interval date
             => new GoalTodo(this, 0, )
            today += interval
        }*/
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();



        return goals;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

}
