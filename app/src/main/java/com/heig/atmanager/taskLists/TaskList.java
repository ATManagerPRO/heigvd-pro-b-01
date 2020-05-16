package com.heig.atmanager.taskLists;

import com.heig.atmanager.DrawerObject;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *  Author : Teo Ferrari
 *  Date   : 06.04.2020
 *
 *  Class tasklist representing a list of tasks
 */
public class TaskList extends DrawerObject implements Serializable {

    public static String SERIAL_TASK_LIST_KEY = "taskList";
    public static TaskList defaultList = new TaskList(0, "My Tasks");

    private long id;
    private String name;
    private long folder_id;
    private ArrayList<Task> tasks;

    public TaskList(String name, long folder_id) {
        this(-1, name, folder_id);
    }

    public TaskList(long id, String name) {
        this(id, name, -1);
    }

    public TaskList(long id, String name, long folder_id) {
        super(name);
        this.id = id;
        this.folder_id = folder_id;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setFolderId(long id) {
        this.folder_id = id;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    public static TaskList getDefaultList() {
        return defaultList;
    }


    public long getId() {
        return id;
    }
}
