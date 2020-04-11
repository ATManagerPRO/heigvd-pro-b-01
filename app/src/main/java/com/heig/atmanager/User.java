package com.heig.atmanager;


import android.icu.text.CaseMap;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;

public class User {

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

    public ArrayList<Task> getTodos() {
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}