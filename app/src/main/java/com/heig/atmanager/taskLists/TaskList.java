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

    public static TaskList defaultList = new TaskList("My Tasks");

    private Folder parent;

    public TaskList(String name){
        super(name);
    }

    public TaskList(String name, Folder parent) {
        this(name);
        this.parent = parent;
    }

    public void setParent(Folder folder) {
        this.parent = folder;
    }

    public boolean isStandalone() {
        return parent == null;
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}
