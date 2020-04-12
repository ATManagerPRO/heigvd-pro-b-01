package com.heig.atmanager.taskLists;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;

/**
 *  Author : Teo Ferrari
 *  Date   : 06.04.2020
 *
 *  Class tasklist representing a list of tasks
 */
public class TaskList {

    public static TaskList defaultList = new TaskList("My Tasks");

    private Folder parent;
    private String name;
    private ArrayList<Task> tasks;

    public TaskList(String name){
        this.name = name;
        tasks = new ArrayList<>();
    }

    public TaskList(String name, Folder parent) {
        this(name);
        this.parent = parent;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
