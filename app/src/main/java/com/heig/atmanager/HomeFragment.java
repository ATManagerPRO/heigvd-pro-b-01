package com.heig.atmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stephane
 * Date   : 11.03.2020
 *
 * Fragment for the Home view (User's Tasks and Goals of the day)
 */
public class HomeFragment extends Fragment {

    private RecyclerView tasksRecyclerView;
    private RecyclerView.Adapter tasksAdapter;
    private RecyclerView.LayoutManager taskslayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // TEMP
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task1"));
        tasks.add(new Task("Task2"));
        tasks.add(new Task("Task3"));
        // END TEMP

        setupTasksRecyclerView(v, tasks);

        return v;
    }

    private void setupTasksRecyclerView(View v, ArrayList<Task> tasks) {

        tasksRecyclerView = (RecyclerView) v.findViewById(R.id.tasks_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        tasksRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        taskslayoutManager = new LinearLayoutManager(v.getContext());
        tasksRecyclerView.setLayoutManager(taskslayoutManager);

        // specify an adapter (see also next example)
        tasksAdapter = new TaskFeedAdapter(tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
    }
}
