package com.heig.atmanager;

import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Author : Chau Ying Kot
 * Date   : 29.03.2020
 **/


public class DummyData {

    final static User dummyUser = new User("Joe", "GoogleToken");


    public static User initData(){

        // Add goals
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        dummyUser.addTask(new Task("Task1", "This is a really useful task.", true));
        dummyUser.addTask(new Task("Task2", "Rendre labo 1 :\n> Fiche technique\n> Rapport (10 pages)\n> Code source (C++)", calendar));
        dummyUser.addTask(new Task("Task3", "...", calendar));
        Goal daily_goal1 = new Goal("SQUATS", 20, 1,Interval.DAY, calendar);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dummyUser.addTask(new Task("Task4", "...", calendar));
        dummyUser.addTask(new Task("Task5", "...", calendar));
        Goal daily_goal2 = new Goal("FRUITS", 5, 1,Interval.DAY, calendar);
        calendar.add(Calendar.WEEK_OF_MONTH, 4);
        dummyUser.addTask(new Task("Task6", "...", calendar));
        dummyUser.addTask(new Task("Task7", "...", calendar));
        dummyUser.addTask(new Task("Task8", "...", calendar));
        dummyUser.addTask(new Task("Task9", "...", calendar));
        Goal weekly_goal3 = new Goal("KMS", 4, 1,Interval.WEEK, calendar);
        calendar.add(Calendar.MONTH, 3);
        Goal monthly_goal4 = new Goal("GIT PUSH", 4, 1,Interval.MONTH, calendar);

        dummyUser.addGoal(daily_goal1);
        dummyUser.addGoal(daily_goal2);
        dummyUser.addGoal(weekly_goal3);
        dummyUser.addGoal(monthly_goal4);

        // Add Tasks

        dummyUser.setTags(new ArrayList<>(Arrays.asList("Urgent", "Normal")));

        dummyUser.setDirectories(new ArrayList<>(Arrays.asList("Personal", "School")));

        return dummyUser;
    }
}
