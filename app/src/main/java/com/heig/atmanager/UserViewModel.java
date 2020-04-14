package com.heig.atmanager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : Chau Ying Kot
 * Date   : 09.04.2020
 **/
public class UserViewModel extends ViewModel {

    private String userName;
    private String googleToken;
    private MutableLiveData<ArrayList<Task>> tasks;
    private MutableLiveData<ArrayList<TaskList>> taskLists;
    private MutableLiveData<ArrayList<Goal>> goals;
    private MutableLiveData<ArrayList<String>> tags;
    private MutableLiveData<ArrayList<Folder>> folders;

    public UserViewModel() {
        tasks     = new MutableLiveData<>();
        taskLists = new MutableLiveData<>();
        goals     = new MutableLiveData<>();
        tags      = new MutableLiveData<>();
        folders   = new MutableLiveData<>();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks.setValue(tasks);
    }

    public void setTaskLists(ArrayList<TaskList> taskLists) {
        this.taskLists.setValue(taskLists);
    }

    public void setGoals(ArrayList<Goal> goals) {
        this.goals.setValue(goals);
    }

    public void setTags(ArrayList<String> tags) {
        this.tags.setValue(tags);
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders.setValue(folders);
    }

    public String getUserName() {
        return userName;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public ArrayList<Task> getTasks() {
        return tasks.getValue();
    }

    public ArrayList<Goal> getGoals() {
        return goals.getValue();
    }

    public ArrayList<String> getTags() {
        return tags.getValue();
    }

    public ArrayList<Folder> getFolders() {
        return folders.getValue();
    }

    public int getMaxActivityPerDay() {
        Map.Entry<LocalDate, Integer> maxEntry = null;
        Map<LocalDate, Integer> hm = new HashMap<>();

        // Count tasks recurrences for each date
        for(Task task : tasks.getValue()) {
            LocalDate date = task.getLocalDueDate();
            Integer j = hm.get(date);
            hm.put(date, (j == null) ? 1 : j + 1);
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

    public ArrayList<Task> getTasksOfDay(Date day) {
        ArrayList<Task> tasksOfDay = new ArrayList<>();

        for (Task task : tasks.getValue()) {
            if (task.getDueDate() != null && isSameSimpleDate(task.getDueDate(), day)) {
                tasksOfDay.add(task);
            }
        }

        return tasksOfDay;
    }

    public int getTotalTasksForDay(Date day) {
        return getTasksOfDay(day).size();
    }

    public ArrayList<GoalTodo> getGoalTodosOfDay(Date day) {
        ArrayList<GoalTodo> goalTodosOfDay = new ArrayList<>();

        for(Goal goal : goals.getValue()) {
            for(GoalTodo goalTodo : goal.getGoalTodos()) {
                if (goalTodo.getDoneDate() != null && isSameSimpleDate(goalTodo.getDoneDate(), day)) {
                    goalTodosOfDay.add(goalTodo);
                }
            }
        }

        return goalTodosOfDay;
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
