package com.heig.atmanager;

import java.util.ArrayList;
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
 */
public class Goal {

    private String unit;
    private int quantity;
    private Interval interval;
    private Date dueDate;

    public Goal(String unit, int quantity, Interval interval, Date dueDate) {
        this.unit = unit;
        this.quantity = quantity;
        this.interval = interval;
        this.dueDate = dueDate;

        // Generate the todos automatically (once) for the user
        // TODO: rem : if there are no due dates, ...
        generateTodos();
    }

    /*public int getPercentage() {
        return (int) (((float) current / quantity) * 100);
    }

    public String getUnit() {
        return unit;
    }

    public String getStringCurrent() {
        return (current < 10 ? "0" : "") + current;
    }

    public String getStringTotal() {
        return (quantity < 10 ? "0" : "") + quantity;
    }*/

    public ArrayList<GoalTodo> generateTodos() {
        ArrayList<GoalTodo> goals = new ArrayList<>();

        /*while(today + interval < due date) {
            Add new goal to list with today + interval date
             => new GoalTodo(this, 0, )
            today += interval
        }*/

        return goals;
    }

}
