package com.heig.atmanager.folders;

import android.view.View;

import com.heig.atmanager.taskLists.TaskList;

import java.util.ArrayList;

public class Folder {

    private String name;
    private ArrayList<TaskList> taskLists;

    public Folder(String name){
        this.name = name;
        this.taskLists = new ArrayList<TaskList>();
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
     * @param name Name of the list to add
     * @return success of the operation
     */
    public boolean addList(String name){
        for(TaskList list : taskLists){
            if(list.getName().equals(name)){
                return false;
            }
        }
        taskLists.add(new TaskList(name));
        return true;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TaskList> getTaskLists() {
        return taskLists;
    }
}