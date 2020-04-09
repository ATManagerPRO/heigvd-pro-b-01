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

    private static User dummyUser = new User("Joe", "GoogleToken");

    private static UserViewModel user;

    public static UserViewModel getUser() {
        if (user == null){
            // Add goals
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 5);
            Date dueDateGoal1 = calendar.getTime();
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date dueDateGoal2 = calendar.getTime();
            calendar.add(Calendar.WEEK_OF_MONTH, 4);
            Date dueDateGoal3 = calendar.getTime();
            calendar.add(Calendar.MONTH, 3);
            Date dueDateGoal4 = calendar.getTime();

            Goal daily_goal1 = new Goal("SQUATS", 20, 1,Interval.DAY, dueDateGoal1);
            Goal daily_goal2 = new Goal("FRUITS", 5, 1,Interval.DAY, dueDateGoal2);
            Goal weekly_goal3 = new Goal("KMS", 4, 1,Interval.WEEK, dueDateGoal3);
            Goal monthly_goal4 = new Goal("GIT PUSH", 4, 1,Interval.MONTH, dueDateGoal4);
            dummyUser.addGoal(daily_goal1);
            dummyUser.addGoal(daily_goal2);
            dummyUser.addGoal(weekly_goal3);
            dummyUser.addGoal(monthly_goal4);

            // Add Tasks
            dummyUser.addTask(new Task("Task1", "This is a really useful task.", true));
            dummyUser.addTask(new Task("Task2", "Rendre labo 1 :\n> Fiche technique\n> Rapport (10 pages)\n> Code source (C++)"));
            dummyUser.addTask(new Task("Task3", "..."));
            dummyUser.addTask(new Task("Task4", "..."));

            dummyUser.setTags(new ArrayList<>(Arrays.asList("Urgent", "Normal")));

            dummyUser.setDirectories(new ArrayList<>(Arrays.asList("Personal", "School")));

            user  = new UserViewModel(dummyUser);
        }
        return user;
    }
}
