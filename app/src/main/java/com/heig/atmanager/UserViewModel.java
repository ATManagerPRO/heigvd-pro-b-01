package com.heig.atmanager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.tasks.Task;

/**
 * Author : Chau Ying Kot
 * Date   : 09.04.2020
 **/
public class UserViewModel extends ViewModel {

    private String userName;
    private MutableLiveData<String> directories;
    private String googleToken;

    private MutableLiveData<Task> tasks;

    private MutableLiveData<Goal> goals;

    private MutableLiveData<String> tags;
}
