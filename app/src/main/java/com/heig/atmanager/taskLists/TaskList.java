package com.heig.atmanager.taskLists;

import com.heig.atmanager.DrawerObject;
import com.heig.atmanager.MainActivity;
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
    private long folder_id;

    public TaskList(String name, long folder_id) {
        this(-1, name, folder_id);
    }

    public TaskList(String name) {
        this(-1, name, -1);
    }

    public TaskList(long id, String name) {
        this(id, name, -1);
    }

    public TaskList(long id, String name, long folder_id) {
        super(name);
        this.id = id;
        this.folder_id = folder_id;
    }

    public void addTask(Task task) {
        task.setTasklist(this);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        for(Task task : MainActivity.getUser().getTasks())
            if(task.getTasklist().getId() == id)
                tasks.add(task);

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

    public long getFolderId() {
        return folder_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return id == taskList.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
