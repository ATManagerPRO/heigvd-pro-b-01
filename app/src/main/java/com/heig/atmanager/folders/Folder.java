package com.heig.atmanager.folders;

import com.heig.atmanager.taskLists.TaskList;

import java.util.ArrayList;

/**
 *  Author : Teo Ferrari
 *  Date   : 06.04.2020
 *
 *  Class folder representing a folder containing different tasklists for organisation
 */
public class Folder {

    private long id;
    private String name;
    private ArrayList<TaskList> taskLists;

    public Folder(long id, String name){
        this(id, name, new ArrayList<TaskList>());
    }

    public Folder(long id, String name, ArrayList<TaskList> taskLists){
        this.id = id;
        this.name = name;
        this.taskLists = taskLists;
    }

    /**
     * Deletes a list from folder
     *
     * @param name Name of the list to delete
     * @return success of the operation
     */
    public boolean deleteList(String name){
        for(TaskList list : taskLists){
            if(list.getName().equals(name)){
                taskLists.remove(list);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a list from folder
     *
     * @param taskList : taskList to add
     * @return success of the operation
     */
    public boolean addList(TaskList taskList){
        for(TaskList list : taskLists)
            if(list.getId() == taskList.getId())
                return false;

        taskLists.add(taskList);
        return true;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TaskList> getTaskLists() {
        return taskLists;
    }
}