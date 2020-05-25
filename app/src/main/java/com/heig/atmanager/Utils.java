package com.heig.atmanager;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(false);

        // use a (horizontal) linear layout manager
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter adapter = new FolderAdapter(folders);
        rv.setAdapter(adapter);
    }

    public static void setupTaskListFeed(View v, RecyclerView rv, ArrayList<TaskList> taskLists) {

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(false);

        // use a (horizontal) linear layout manager
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter adapter = new TaskListAdapter(taskLists);
        rv.setAdapter(adapter);
    }


    public static void setupTasksFeed(View v, RecyclerView rv, ArrayList<Task> tasks, Context context) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        rv.setLayoutManager(manager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter adapter = new TaskFeedAdapter(tasks, context);
        rv.setAdapter(adapter);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int[] getDayWeekMonthYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new int[]{cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.WEEK_OF_MONTH),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.YEAR)};
    }

    public static int[] getWeekMonthYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new int[]{cal.get(Calendar.WEEK_OF_MONTH),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.YEAR)};
    }

    public static int[] getMonthYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new int[]{cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)};
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static String dateToString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return formatNumber(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                formatNumber(cal.get(Calendar.MONTH) + 1) + "/" +
                formatNumber(cal.get(Calendar.YEAR));
    }

    public static String formatNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }

    public static String firstLetterCapped(String s){
        if(s == null)
            return null;
        else if(s.length() == 1)
            return s.toUpperCase();
        else
            return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }


}
