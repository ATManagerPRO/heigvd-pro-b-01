package com.heig.atmanager;


import android.util.Log;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;

import java.time.LocalDate;
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
        this.userName    = userName;
        this.googleToken = googleToken;
        this.goals       = new ArrayList<>();
        this.taskLists   = new ArrayList<>();
        this.tasks       = new ArrayList<>();
        this.folders     = new ArrayList<>();

        // Goals
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date dueDateGoal1 = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dueDateGoal2 = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_MONTH, 4);
        Date dueDateGoal3 = calendar.getTime();
        calendar.add(Calendar.MONTH, 3);
        Date dueDateGoal4 = calendar.getTime();
        Goal daily_goal1 = new Goal("SQUATS", 20, 1,Interval.DAY, dueDateGoal1);
        Goal daily_goal2 = new Goal("FRUITS", 5, 1,Interval.DAY, dueDateGoal2);
        Goal weekly_goal3 = new Goal("KMS", 4, 1,Interval.WEEK, dueDateGoal3);
        Goal monthly_goal4 = new Goal("GIT PUSH", 4, 1,Interval.MONTH, dueDateGoal4);
        this.addGoal(daily_goal1);
        this.addGoal(daily_goal2);
        this.addGoal(weekly_goal3);
        this.addGoal(monthly_goal4);
        // Folders
        Folder f1 = new Folder("HEIG-VD");
        Folder f2 = new Folder("Home stuff");
        // TaskLists
        this.addTaskList(TaskList.defaultList);
        TaskList tl1 = new TaskList("SIO");
        TaskList tl2 = new TaskList("GEN");
        TaskList tl3 = new TaskList("RES");
        TaskList tl4 = new TaskList("Chores");
        TaskList tl5 = new TaskList("Groceries");
        f1.addList(tl1);
        f1.addList(tl2);
        f1.addList(tl3);
        f2.addList(tl4);
        f2.addList(tl5);
        // Tasks
        TaskList.defaultList.addTask(new Task("Task1", "This is a really useful task.", true));
        TaskList.defaultList.addTask(new Task("Task2", "Rendre labo 1 :\n> Fiche technique\n> Rapport (10 pages)\n> Code source (C++)"));
        TaskList.defaultList.addTask(new Task("Task3", "..."));
        TaskList.defaultList.addTask(new Task("Task4", "..."));
        tl1.addTask(new Task("Send report X", "Must DO!!!", dueDateGoal1));
        tl1.addTask(new Task("Task test", "this is a test", dueDateGoal1));
        tl2.addTask(new Task("Task test", "this is a test", dueDateGoal2));
        tl2.addTask(new Task("Task test", "this is a test", dueDateGoal2));
        tl3.addTask(new Task("Task test", "this is a test", dueDateGoal3));
        tl3.addTask(new Task("Task test", "this is a test", dueDateGoal3));
        tl3.addTask(new Task("Task test", "this is a test", dueDateGoal4));
        tl4.addTask(new Task("Task test", "this is a test", dueDateGoal4));
        tl5.addTask(new Task("Task test", "this is a test", dueDateGoal4));
        tl5.addTask(new Task("Task test", "this is a test", dueDateGoal4));

        // Tags
        this.setTags(new ArrayList<>(Arrays.asList("Urgent", "Normal")));

        // Add the data to the user from the folders (folders, tasklists and tasks)
        this.addAllFromFolder(f1);
        this.addAllFromFolder(f2);

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
        tasks.add(task);
    }

    public void addTaskList(TaskList taskList) {
        taskLists.add(taskList);
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

    public void addAllFromFolder(Folder folder) {
        folders.add(folder);

        for (TaskList taskList : folder.getTaskLists()) {
            addTaskList(taskList);
            for (Task task : taskList.getTasks())
                addTask(task);
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
        for(Task task : tasks) {
            Date date = task.getDueDate();
            Integer j = hm.get(date);
            hm.put(date, (j == null) ? 1 : j + 1);
        }

        // Count goals recurrences for each date
        for(Goal goal : goals) {
            for(GoalTodo goalTodo : goal.getGoalTodos()) {
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

}