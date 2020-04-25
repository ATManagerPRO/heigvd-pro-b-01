package com.heig.atmanager.folders;

import com.heig.atmanager.DrawerObject;
import com.heig.atmanager.taskLists.TaskList;

import java.util.ArrayList;

/**
 *  Author : Teo Ferrari
 *  Date   : 06.04.2020
 *
 *  Class folder representing a folder containing different tasklists for organisation
 */
public class Folder extends DrawerObject {

    private ArrayList<TaskList> taskLists;

    public Folder(String name){
        super(name);
        this.taskLists = new ArrayList<>();
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
     * @param list : tasklist to add
     * @return success of the operation
     */
    public boolean addList(TaskList list){
        for(TaskList taskList : taskLists){
            if(taskList.getName().equals(list.getName())){
                return false;
            }
        }

        taskLists.add(list);
        list.setParent(this);
        return true;
    }

    public ArrayList<TaskList> getTaskLists() {
        return taskLists;
    }


    @Override
    public boolean isFolder() {
        return true;
    }
}