package com.heig.atmanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;

/**
 * Author : Chau Ying Kot
 * Date   : 09.04.2020
 **/
public class UserViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TaskList>> taskLists;

    private MutableLiveData<ArrayList<Task>> tasks;

    private MutableLiveData<ArrayList<Goal>> goals;

    private MutableLiveData<ArrayList<String>> tags;

    private MutableLiveData<ArrayList<Folder>> folders;

    private User user;

    public String getUserName() {
        return user.getUserName();
    }

    public UserViewModel() {
    }

    public UserViewModel(User user) {
        this.user = user;

        taskLists = new MutableLiveData<>();
        if (user.getFolders() != null) {
            taskLists.setValue(user.getTaskLists());
        }

        goals = new MutableLiveData<>();
        if (user.getGoals() != null) {
            goals.setValue(user.getGoals());
        }

        tags = new MutableLiveData<>();
        if (user.getTags() != null) {
            tags.setValue(user.getTags());
        }

        folders = new MutableLiveData<>();
        if (user.getFolders() != null) {
            folders.setValue(user.getFolders());
        }

        tasks = new MutableLiveData<>();
        if (user.getFolders() != null) {
            tasks.setValue(user.getTasks());
        }

    }

    public String getGoogleToken() {
        return user.getGoogleToken();
    }

    public MutableLiveData<ArrayList<TaskList>> getTaskLists() {
        return taskLists;
    }

    public MutableLiveData<ArrayList<Task>> getTasks() {
        return tasks;
    }

    public MutableLiveData<ArrayList<Goal>> getGoals() {

        return goals;
    }

    public MutableLiveData<ArrayList<String>> getTags() {

        return tags;
    }

    public MutableLiveData<ArrayList<Folder>> getFolders() {
        return folders;
    }

    public void addTask(Task task, String listName) {
        /*for (TaskList taskList : user.getTaskLists()) {
            if (taskList.getName().equals(listName)) {
                taskList.addTask(task);
                tasks.setValue(user.getTasks());
            }
        }*/
        user.addTask(task);
        tasks.setValue(user.getTasks());
    }

    public void addGoal(Goal goal) {
        goals.getValue().add(goal);
    }
}
