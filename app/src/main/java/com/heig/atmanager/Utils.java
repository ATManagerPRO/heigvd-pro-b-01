package com.heig.atmanager;

import android.view.View;

import com.heig.atmanager.goals.GoalFeedAdapter;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.tasks.TaskFeedAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 16.03.2020
 *
 * Bag for utils functions, can be refactored / separated later on...
 */
public class Utils {

    public static void setupGoalsFeed(View v, RecyclerView rv, ArrayList<GoalTodo> goals) {

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a (horizontal) linear layout manager
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(manager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter adapter = new GoalFeedAdapter(goals);
        rv.setAdapter(adapter);
    }

    public static void setupTodosFeed(View v, RecyclerView rv, ArrayList<Task> tasks) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        rv.setLayoutManager(manager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter adapter = new TaskFeedAdapter(tasks);
        rv.setAdapter(adapter);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }
}
