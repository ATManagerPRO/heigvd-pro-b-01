package com.heig.atmanager.addTaskGoal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.heig.atmanager.DummyData;
import com.heig.atmanager.R;
import com.heig.atmanager.User;

public class AddTaskGoalActivity extends AppCompatActivity  {

    User dummyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyUser = new DummyData().initData();
        setContentView(R.layout.activity_add_task_goal);


    }
}
