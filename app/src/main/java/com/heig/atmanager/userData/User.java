package com.heig.atmanager.userData;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User {

    private static final String TAG = "User";

    private long userId;
    private String userName;
    private String googleToken;
    private String backEndToken;
    private String email;
    private ArrayList<Goal> goals;
    private ArrayList<TaskList> taskLists;
    private ArrayList<Task> tasks;
    private ArrayList<String> tags;
    private ArrayList<Folder> folders;

    public User(String userName, String googleToken, String email) {
        this.userName = userName;
        this.googleToken = googleToken;
        this.email = email;
        this.goals       = new ArrayList<>();
        this.taskLists   = new ArrayList<>();
        this.tasks       = new ArrayList<>();
        this.folders     = new ArrayList<>();
        this.tags        = new ArrayList<>();
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

    public ArrayList<Folder> getFolders() {
        return folders;
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

    private boolean isSameSimpleDate(Date d1, Date d2) {
        if(d1 == null || d2 == null)
            return false;

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

    public TaskList getTaskList(long tasklist_id) {
        for(TaskList taskList : taskLists) {
            if(taskList.getId() == tasklist_id)
                return taskList;
        }

        return null;
    }

    public boolean hasGoal(long goal_id) {
        return getGoal(goal_id) != null;
    }

    public boolean hasTaskList(long tasklist_id) {
        return getTaskList(tasklist_id) != null;
    }

    public ArrayList<GoalTodo> getGoalTodosOfDay(Date day) {
        ArrayList<GoalTodo> goalTodos = new ArrayList<>();
        for(Goal goal : goals) {
            for(GoalTodo goalTodo : goal.getGoalTodos()) {
                if(isSameSimpleDate(goalTodo.getDoneDate(), day)) {
                    goalTodos.add(goalTodo);
                }
            }
        }

        return goalTodos;
    }
    
    public void addTag(String tagName){
        tags.add(tagName);
    }

    public String getBackEndToken() {
        return backEndToken;
    }

    public void setBackEndToken(String backEndToken) {
        this.backEndToken = backEndToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}