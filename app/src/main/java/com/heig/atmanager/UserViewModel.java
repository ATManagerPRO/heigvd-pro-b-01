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

    private String userName;
    private String googleToken;

    private MutableLiveData<ArrayList<TaskList>> taskLists;

    private MutableLiveData<ArrayList<Task>> tasks;

    private MutableLiveData<ArrayList<Goal>> goals;

    private MutableLiveData<ArrayList<String>> tags;

    private MutableLiveData<ArrayList<Folder>> folders;

    public String getUserName() {
        return userName;
    }

    public UserViewModel() {
    }

    public UserViewModel(User user) {
        userName = user.getUserName();
        googleToken = user.getGoogleToken();

        taskLists = new MutableLiveData<>();
        if (user.getTaskLists() != null) {
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

    }

    public String getGoogleToken() {
        return googleToken;
    }

    public MutableLiveData<ArrayList<Task>> getTasks() {
        if (tasks == null) {
            tasks = new MutableLiveData<>();
        }
        return tasks;
    }

    public MutableLiveData<ArrayList<Goal>> getGoals() {
        if (goals == null) {
            goals = new MutableLiveData<>();
        }
        return goals;
    }

    public MutableLiveData<ArrayList<String>> getTags() {
        if (tags == null) {
            tags = new MutableLiveData<>();
        }
        return tags;
    }

    public MutableLiveData<ArrayList<Folder>> getFolders() {
        if (folders == null) {
            folders = new MutableLiveData<>();
        }

        return folders;
    }

    public void addTask(Task task){
        tasks.getValue().add(task);
    }

    public void addGoal(Goal goal){
        goals.getValue().add(goal);
    }
}
