package com.heig.atmanager.userData;

import android.util.Log;

import com.heig.atmanager.Interval;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static final String TAG = "User";

    private String userName;
    private String googleToken;
    private ArrayList<Goal> goals;
    private ArrayList<TaskList> taskLists;
    private ArrayList<Task> tasks;
    private ArrayList<String> tags;
    private ArrayList<Folder> folders;

    public User(String userName, String googleToken) {
        this.userName = userName;
        this.googleToken = googleToken;
        this.goals       = new ArrayList<>();
        this.taskLists   = new ArrayList<>();
        this.tasks       = new ArrayList<>();
        this.folders     = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public ArrayList<TaskList> getTaskLists() {
        return taskLists;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void addTask(Task task) {
        Log.d(TAG, "add a task: <" + task.getTitle() + "> for " + getUserName());
        tasks.add(task);
    }

    public void addTaskList(TaskList taskList) {
        Log.d(TAG, "addFolder: Create : add tasklist <" + taskList.getName() + "> for " + getUserName());
        taskLists.add(taskList);
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public void addFolder(Folder folder) {
        Log.d(TAG, "addFolder: Create : add folder <" + folder.getName() + "> for " + getUserName());
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

    public void addAllFromFolder(Folder folder) {
        folders.add(folder);

        for (TaskList taskList : folder.getTaskLists()) {
            addTaskList(taskList);
        }
    }

    public int getTotalTasksForDay(Date day) {
        int totalTasks = 0;

        for (Task task : tasks) {
            if (task.getDueDate() != null && isSameSimpleDate(task.getDueDate(), day)) {
                totalTasks++;
            }
        }

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
        Map.Entry<Date, Integer> maxEntry = null;
        Map<Date, Integer> hm = new HashMap<>();

        // Count tasks recurrences for each date
        for (Task task : tasks) {
            Date date = task.getDueDate();
            Integer j = hm.get(date);
            hm.put(date, (j == null) ? 1 : j + 1);
        }

        // Count goals recurrences for each date
        for (Goal goal : goals) {
            for (GoalTodo goalTodo : goal.getGoalTodos()) {
                Date date = goalTodo.getDueDate();
                Integer j = hm.get(date);
                hm.put(date, (j == null) ? 1 : j + 1);
            }
        }

        // Find max
        for (Map.Entry<Date, Integer> entry : hm.entrySet()) {
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

    public ArrayList<Task> getTasksForDay(Date day) {
        ArrayList<Task> tasksForDay = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDueDate() != null && isSameSimpleDate(task.getDueDate(), day)) {
                tasksForDay.add(task);
            }
        }

        return tasksForDay;
    }

    public ArrayList<Task> getTasksWithoutDate() {
        ArrayList<Task> tasksForDay = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDueDate() == null) {
                tasksForDay.add(task);
            }
        }

        return tasksForDay;
    }

    public ArrayList<Task> getTasksDone() {
        ArrayList<Task> tasksForDay = new ArrayList<>();

        for (Task task : tasks) {
            if (task.isDone()) {
                tasksForDay.add(task);
            }
        }

        return tasksForDay;
    }

    public Goal getGoal(long goal_id) {
        for(Goal goal : goals) {
            if(goal.getId() == goal_id)
                return goal;
        }

        return null;
    }

    public boolean hasGoal(long goal_id) {
        return getGoal(goal_id) != null;
    }



}