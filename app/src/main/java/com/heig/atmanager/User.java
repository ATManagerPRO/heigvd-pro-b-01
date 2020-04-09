package com.heig.atmanager;


import android.icu.text.CaseMap;
import android.util.Log;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static final String TAG = "User";

    private String userName;
    private String googleToken;

    private ArrayList<Task> tasks;

    private ArrayList<Goal> goals;

    private ArrayList<String> tags;

    private ArrayList<Folder> folders;

    private ArrayList<String> directories;

    public User(String userName, String googleToken) {
        this.userName = userName;
        this.googleToken = googleToken;
        tasks = new ArrayList<>();
        goals = new ArrayList<>();
        tags = new ArrayList<>();
        folders = new ArrayList<>();
        directories = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public void addFolder(Folder folder) {
        folders.add(folder);
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }
    public  ArrayList<String> getDirectories() {
        return directories;
    }

    public void setDirectories(ArrayList<String> directories) {
        this.directories = directories;
    }

    public int getTotalTasksForDay(Date day) {
        int totalTasks = 0;

        Log.d(TAG, "getTotalTasksForDay: TASKS -------------------------------------");
        for (Task task : tasks) {
            Log.d(TAG, "getTotalTasksForDay: " + task);
            if (task.getDueDate() != null && isSameSimpleDate(task.getDueDate(), day))
                totalTasks++;
        }
        Log.d(TAG, "getTotalTasksForDay: -------------------------------------------");

        return totalTasks;
    }

    public int getTotalGoalsForDay(Date day) {
        int totalGoal = 0;

        for (Goal goal : goals)
            for (GoalTodo goalTodo : goal.getGoalTodos())
                if (goalTodo.getDoneDate() != null && isSameSimpleDate(goalTodo.getDoneDate(), day))
                    totalGoal++;

        return totalGoal;
    }

    public int getTotalActivityForDay(Date day) {
        return getTotalTasksForDay(day) + getTotalGoalsForDay(day);
    }

    public int getMaxActivityPerDay() {
        Map.Entry<LocalDate, Integer> maxEntry = null;
        Map<LocalDate, Integer> hm = new HashMap<>();

        // Count tasks recurrences for each date
        for(Task task : tasks) {
            LocalDate date = task.getLocalDueDate();
            Integer j = hm.get(date);
            hm.put(date, (j == null) ? 1 : j + 1);
        }

        // Count goals recurrences for each date
        for(Goal goal : goals) {
            for(GoalTodo goalTodo : goal.getGoalTodos()) {
                LocalDate date = goalTodo.getLocalDueDate();
                Integer j = hm.get(date);
                hm.put(date, (j == null) ? 1 : j + 1);
            }
        }

        // Find max
        for (Map.Entry<LocalDate, Integer> entry : hm.entrySet()) {
            if (maxEntry == null || entry.getValue()
                    .compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry == null ? 0 : maxEntry.getValue();
    }

    private boolean isSameSimpleDate(Date d1, Date d2) {
        Calendar calendar_d1 = Calendar.getInstance();
        Calendar calendar_d2 = Calendar.getInstance();
        calendar_d1.setTime(d1);
        calendar_d2.setTime(d2);
        return calendar_d1.get(Calendar.YEAR) == calendar_d2.get(Calendar.YEAR) &&
                calendar_d1.get(Calendar.MONTH) == calendar_d2.get(Calendar.MONTH) &&
                calendar_d1.get(Calendar.DAY_OF_MONTH) == calendar_d2.get(Calendar.DAY_OF_MONTH);
    }
}