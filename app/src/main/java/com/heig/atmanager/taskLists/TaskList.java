package com.heig.atmanager.taskLists;

import com.heig.atmanager.DrawerObject;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;

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

    public TaskList(long id, String name) {
        super(name);
        this.id = id;
    }

    public TaskList(long id, String name, long folder_id) {
        this(id, name);
        this.folder_id = folder_id;
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

    public long getId() {
        return id;
    }
}
