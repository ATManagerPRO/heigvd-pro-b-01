package com.heig.atmanager;

import com.heig.atmanager.folders.Folder;
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
        if (user == null) {
            // Add goals
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 5);
            Goal daily_goal1 = new Goal("SQUATS", 20, 1, Interval.DAY, calendar);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Goal daily_goal2 = new Goal("FRUITS", 5, 1, Interval.DAY, calendar);

            calendar.add(Calendar.WEEK_OF_MONTH, 4);
            Goal weekly_goal3 = new Goal("KMS", 4, 1, Interval.WEEK, calendar);

            calendar.add(Calendar.MONTH, 3);
            Goal monthly_goal4 = new Goal("GIT PUSH", 4, 1, Interval.MONTH, calendar);

            dummyUser.addGoal(daily_goal1);
            dummyUser.addGoal(daily_goal2);
            dummyUser.addGoal(weekly_goal3);
            dummyUser.addGoal(monthly_goal4);

            // Add Tasks
            Calendar taskCal = Calendar.getInstance();
            taskCal.add(Calendar.DAY_OF_MONTH, 1);
            Calendar taskCal1 = Calendar.getInstance();
            taskCal1.add(Calendar.DAY_OF_MONTH, 1);
            Calendar taskCal2 = Calendar.getInstance();
            taskCal1.add(Calendar.WEEK_OF_MONTH, 1);
            Calendar taskCal3 = Calendar.getInstance();
            taskCal1.add(Calendar.WEEK_OF_MONTH, 1);
            Calendar taskCal4 = Calendar.getInstance();
            taskCal4.add(Calendar.DAY_OF_MONTH, 3);
            Calendar taskCal5 = Calendar.getInstance();
            taskCal5.add(Calendar.WEEK_OF_MONTH, 2);
            Calendar taskCal6 = Calendar.getInstance();
            taskCal6.add(Calendar.DAY_OF_MONTH, 9);
            dummyUser.addTask(new Task("Task1", "...", taskCal));
            dummyUser.addTask(new Task("Task2", "...", taskCal1));
            dummyUser.addTask(new Task("Task3", "...", taskCal2));
            dummyUser.addTask(new Task("Task4", "...", taskCal3));
            dummyUser.addTask(new Task("Task5", "...", taskCal1));
            dummyUser.addTask(new Task("Task6", "...", taskCal));
            dummyUser.addTask(new Task("Task7", "...", taskCal1));
            dummyUser.addTask(new Task("Task8", "...", taskCal4));
            dummyUser.addTask(new Task("Task9", "...", taskCal5));
            dummyUser.addTask(new Task("Task10", "...", taskCal6));

            dummyUser.setTags(new ArrayList<>(Arrays.asList("Urgent", "Normal")));

            //create folders
            Folder folder1 = new Folder("Ecole");
            folder1.addList("first list");
            folder1.addList("second list");
            folder1.addList("third list");
            Folder folder2 = new Folder("Travail");
            folder2.addList("fourth list");
            folder2.addList("fifth list");
            Folder folder3 = new Folder("Jeux");
            folder3.addList("list");
            folder3.addList("list1");
            folder3.addList("list2");
            folder3.addList("list3");
            folder3.addList("list4");
            folder3.addList("list5");
            folder3.addList("list6");
            folder3.addList("list7");
            folder3.addList("list8");
            folder3.addList("list9");
            folder3.addList("list0");
            folder3.addList("list10");
            folder3.addList("list11");
            folder3.addList("list22");
            folder3.addList("list24");
            folder3.addList("list33");
            folder3.addList("list55");
            folder3.addList("list66");
            folder3.addList("list77");
            folder3.addList("list88");
            folder3.addList("list99");
            dummyUser.addFolder(folder1);
            dummyUser.addFolder(folder2);
            dummyUser.addFolder(folder3);

            user = new UserViewModel(dummyUser);
        }
        return user;

    }
}
