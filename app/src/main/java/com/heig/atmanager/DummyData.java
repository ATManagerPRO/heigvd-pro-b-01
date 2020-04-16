package com.heig.atmanager;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.taskLists.TaskList;
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

    private static com.heig.atmanager.User dummyUser = new com.heig.atmanager.User("Joe", "GoogleToken");

    private static UserViewModel user;

    public static UserViewModel getUser() {
        if (user == null){
            // Goals
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
            // Folders
            Folder f1 = new Folder("HEIG-VD");
            Folder f2 = new Folder("Home stuff");
            // TaskLists
            TaskList tl1 = new TaskList("SIO");
            TaskList tl2 = new TaskList("GEN");
            TaskList tl3 = new TaskList("RES");
            TaskList tl4 = new TaskList("Chores");
            TaskList tl5 = new TaskList("Groceries");
            f1.addList(tl1);
            f1.addList(tl2);
            f1.addList(tl3);
            f2.addList(tl4);
            f2.addList(tl5);
            // Tasks
            TaskList.defaultList.addTask(new Task("Task1", "This is a really useful task.", true));
            TaskList.defaultList.addTask(new Task("Task2", "Rendre labo 1 :\n> Fiche technique\n> Rapport (10 pages)\n> Code source (C++)"));
            TaskList.defaultList.addTask(new Task("Task3", "..."));
            TaskList.defaultList.addTask(new Task("Task4", "..."));
            tl1.addTask(new Task("Send report X", "Must DO!!!"));
            tl1.addTask(new Task("Task test", "this is a test"));
            tl2.addTask(new Task("Task test", "this is a test"));
            tl2.addTask(new Task("Task test", "this is a test"));
            tl3.addTask(new Task("Task test", "this is a test"));
            tl3.addTask(new Task("Task test", "this is a test"));
            tl3.addTask(new Task("Task test", "this is a test"));
            tl4.addTask(new Task("Task test", "this is a test"));
            tl5.addTask(new Task("Task test", "this is a test"));
            tl5.addTask(new Task("Task test", "this is a test"));

            // Tags
            dummyUser.setTags(new ArrayList<>(Arrays.asList("Urgent", "Normal")));

            // Add the data to the user from the folders (folders, tasklists and tasks)
            dummyUser.addAllFromFolder(f1);
            dummyUser.addAllFromFolder(f2);

            user  = new UserViewModel(dummyUser);
        }
        return user;

    }
}
