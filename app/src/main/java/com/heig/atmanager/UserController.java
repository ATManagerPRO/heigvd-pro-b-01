package com.heig.atmanager;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class UserController implements Serializable {

    private static final String TAG = "User";
    public static final String SERIAL_USER_KEY = "userKey";

    private String userName;
    private String googleToken;
    private ArrayList<Folder> folders;
    private ArrayList<TaskList> taskLists;
    private ArrayList<Task> tasks;
    private ArrayList<Goal> goals;
    private ArrayList<String> tags;

    private UserViewModel viewModel;

    public UserController(String userName, String googleToken) {
        this.userName    = userName;
        this.googleToken = googleToken;
        tasks            = new ArrayList<>();
        goals            = new ArrayList<>();
        tags             = new ArrayList<>();
        folders          = new ArrayList<>();
        viewModel        = new UserViewModel();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public void addFolder(Folder folder) {
        folders.add(folder);
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void updateViewModel() {
        viewModel.setUserName(userName);
        viewModel.setGoogleToken(googleToken);
        viewModel.setTaskLists(taskLists);
        viewModel.setFolders(folders);
        viewModel.setTasks(tasks);
        viewModel.setGoals(goals);
    }

    public UserViewModel getViewModel() {
        return viewModel;
    }

}