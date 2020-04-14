package com.heig.atmanager;

import android.view.View;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.folders.FolderAdapter;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalLineFeedAdapter;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.goals.GoalTodoFeedAdapter;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.taskLists.TaskListAdapter;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.tasks.TaskFeedAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 16.03.2020
 *
 * Bag for utils functions, can be refactored / separated later on...
 */
public class Utils {

    public static void setupGoalTodosFeedBubbled(View v, RecyclerView rv, ArrayList<GoalTodo> goals) {

        rv.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new GoalTodoFeedAdapter(true, goals);
        rv.setAdapter(adapter);
    }

    public static void setupGoalTodosFeedLined(View v, RecyclerView rv, ArrayList<GoalTodo> goals) {

        rv.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new GoalTodoFeedAdapter(false, goals);
        rv.setAdapter(adapter);
    }

    public static void setupGoalsFeed(FragmentActivity fa, View v, RecyclerView rv, ArrayList<Goal> goals) {

        rv.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new GoalLineFeedAdapter(fa, goals);
        rv.setAdapter(adapter);
    }

    public static void setupFoldersFeed(View v, RecyclerView rv, ArrayList<Folder> folders) {

        rv.setHasFixedSize(false);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new FolderAdapter(folders);
        rv.setAdapter(adapter);
    }

    public static void setupTaskListFeed(View v, RecyclerView rv, ArrayList<TaskList> taskLists) {

        rv.setHasFixedSize(false);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new TaskListAdapter(taskLists);
        rv.setAdapter(adapter);
    }


    public static void setupTasksFeed(View v, RecyclerView rv, ArrayList<Task> tasks) {
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        rv.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new TaskFeedAdapter(tasks);
        rv.setAdapter(adapter);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String dateToString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return formatNumber(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                formatNumber(cal.get(Calendar.MONTH)) + "/" +
                formatNumber(cal.get(Calendar.YEAR));
    }

    public static String formatNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }
}
