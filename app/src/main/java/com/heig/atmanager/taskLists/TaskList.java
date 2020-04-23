package com.heig.atmanager.taskLists;

/**
 *  Author : Teo Ferrari
 *  Date   : 06.04.2020
 *
 *  Class tasklist representing a list of tasks
 */
public class TaskList {

    private long id;
    private String name;
    private long folder_id;

    public TaskList(long id, String name, long folder_id){
        this.id        = id;
        this.name      = name;
        this.folder_id = folder_id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
