package com.heig.atmanager;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;

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


    public static void setupTodosFeed(View v, RecyclerView rv, ArrayList<Todo> todos) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        rv.setLayoutManager(manager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter adapter = new TodoFeedAdapter(todos);
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
