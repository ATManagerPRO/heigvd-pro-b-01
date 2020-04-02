package com.heig.atmanager;


import android.util.Log;

import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private static final String TAG = "User";
    
    private String userName;
    private  ArrayList<String> directories;
    private String googleToken;

    private ArrayList<Task> tasks;

    private ArrayList<Goal> goals;

    private ArrayList<String> tags;

    public User(String userName, String googleToken) {
        this.userName = userName;
        this.googleToken = googleToken;
        directories = new ArrayList<>();
        tasks = new ArrayList<>();
        goals = new ArrayList<>();
        tags = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public  ArrayList<String> getDirectories() {
        return directories;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public ArrayList<Task> getTodos() {
        return tasks;
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void addGoal(Goal goal){
        goals.add(goal);
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setDirectories(ArrayList<String> directories) {
        this.directories = directories;
    }

    public int getTotalTasksForDay(Date day) {
        int totalTasks = 0;

        Log.d(TAG, "getTotalTasksForDay: " + tasks.size());
        
        for(Task task : tasks)
            if(task.getDueDate() != null && task.getDueDate().equals(day))
                totalTasks++;

        return totalTasks;
    }

    public int getTotalGoalsForDay(Date day) {
        int totalGoal = 0;

        for(Goal goal : goals)
            for(GoalTodo goalTodo : goal.getGoalTodos())
                if(goalTodo.getDoneDate() != null && goalTodo.getDoneDate().equals(day))
                    totalGoal++;

        return totalGoal;
    }

    public int getTotalActivityForDay(Date day) {
        return getTotalTasksForDay(day) + getTotalGoalsForDay(day);
    }
}
