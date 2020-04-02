package com.heig.atmanager;


import java.util.ArrayList;

public class User {

    private String userName;
    private  ArrayList<String> directories;
    private String googleToken;

    private ArrayList<Todo> tasks;

    private ArrayList<Goal> goals;

    public String getUserName() {
        return userName;
    }

    public  ArrayList<String> getDirectories() {
        return directories;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public User(String userName, String googleToken) {
        this.userName = userName;
        this.googleToken = googleToken;
        directories = new ArrayList<>();
        tasks = new ArrayList<>();
        goals = new ArrayList<>();
    }

    public ArrayList<Todo> getTodos() {
        return tasks;
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void addTask(Todo task){
        tasks.add(task);
    }

    public void addGoal(Goal goal){
        goals.add(goal);
    }
}
