package com.heig.atmanager;


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
        this.userName = userName;
        this.googleToken = googleToken;
        // TaskLists always has MyTasks by default
        taskLists = new ArrayList<>();
        taskLists.add(TaskList.defaultList);

        tasks = new ArrayList<>();
        goals = new ArrayList<>();
        tags = new ArrayList<>();
        folders = new ArrayList<>();
        // Add goals
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Goal daily_goal1 = new Goal("SQUATS", 20, 1, Interval.DAY, calendar);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Goal daily_goal2 = new Goal("FRUITS", 5, 1, Interval.DAY, calendar);

        calendar.add(Calendar.WEEK_OF_MONTH, 4);
        Goal weekly_goal3 = new Goal("KMS", 4, 1, Interval.WEEK, calendar);

        calendar.add(Calendar.MONTH, 3);
        Goal monthly_goal4 = new Goal("GIT PUSH", 4, 1, Interval.MONTH, calendar);

        this.addGoal(daily_goal1);
        this.addGoal(daily_goal2);
        this.addGoal(weekly_goal3);
        this.addGoal(monthly_goal4);

        // Add Tasks
        Calendar taskCal = Calendar.getInstance();
        taskCal.add(Calendar.DAY_OF_MONTH, 1);
        Calendar taskCal1 = Calendar.getInstance();
        taskCal1.add(Calendar.DAY_OF_MONTH, 1);
        Calendar taskCal2 = Calendar.getInstance();
        taskCal1.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar taskCal3 = Calendar.getInstance();
        taskCal1.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar taskCal4 = Calendar.getInstance();
        taskCal4.add(Calendar.DAY_OF_MONTH, 3);
        Calendar taskCal5 = Calendar.getInstance();
        taskCal5.add(Calendar.WEEK_OF_MONTH, 2);
        Calendar taskCal6 = Calendar.getInstance();
        taskCal6.add(Calendar.DAY_OF_MONTH, 9);
        this.addTask(new Task("Task1", "...", taskCal));
        this.addTask(new Task("Task2", "...", taskCal1));
        this.addTask(new Task("Task3", "...", taskCal2));
        this.addTask(new Task("Task4", "...", taskCal3));
        this.addTask(new Task("Task5", "...", taskCal1));
        this.addTask(new Task("Task6", "...", taskCal));
        this.addTask(new Task("Task7", "...", taskCal1));
        this.addTask(new Task("Task8", "...", taskCal4));
        this.addTask(new Task("Task9", "...", taskCal5));
        this.addTask(new Task("Task10", "...", taskCal6));

        this.setTags(new ArrayList<>(Arrays.asList("Urgent", "Normal")));

        //create folders
        Folder folder1 = new Folder("Ecole");
        folder1.addList("first list");
        folder1.addList("second list");
        folder1.addList("third list");
        Folder folder2 = new Folder("Travail");
        folder2.addList("fourth list");
        folder2.addList("fifth list");
        Folder folder3 = new Folder("Jeux");
        folder3.addList("list");
        folder3.addList("list1");
        folder3.addList("list2");
        folder3.addList("list3");
        folder3.addList("list4");
        folder3.addList("list5");
        folder3.addList("list6");
        folder3.addList("list7");
        folder3.addList("list8");
        folder3.addList("list9");
        folder3.addList("list0");
        folder3.addList("list10");
        folder3.addList("list11");
        folder3.addList("list22");
        folder3.addList("list24");
        folder3.addList("list33");
        folder3.addList("list55");
        folder3.addList("list66");
        folder3.addList("list77");
        folder3.addList("list88");
        folder3.addList("list99");
        this.addFolder(folder1);
        this.addFolder(folder2);
        this.addFolder(folder3);

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