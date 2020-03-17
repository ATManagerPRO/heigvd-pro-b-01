package com.heig.atmanager;

import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 16.03.2020
 *
 * GoalTodos are generated from a goal in order to let the user interact with his/her goals.
 * This class also helps keeping track of the user's progress.
 */
public class GoalTodo {

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
        return (quantityDone < 10 ? "0" : "") + quantityDone;
    }

    public String getStringTotal() {
        return (goal.getQuantity() < 10 ? "0" : "") + goal.getQuantity();
    }

    public Date getDoneDate() {
        return doneDate;
    }

}
