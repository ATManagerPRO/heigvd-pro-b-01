package com.heig.atmanager;


import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;

public class User {

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

        for(TaskList taskList : folder.getTaskLists()) {
            addTaskList(taskList);
            for(Task task : taskList.getTasks())
                addTask(task);
        }

    }
}