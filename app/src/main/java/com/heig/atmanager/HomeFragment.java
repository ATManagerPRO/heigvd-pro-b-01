package com.heig.atmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Fragment for the Home view (User's Tasks and Goals of the day)
 */
public class HomeFragment extends Fragment {
    // Greeting message
    TextView greetingText;

    // Task home feed
    ArrayList<Task> tasks; // user data
    ArrayList<Task> goals; // user data
    private RecyclerView tasksRecyclerView;
    private RecyclerView.Adapter tasksAdapter;
    private RecyclerView.LayoutManager taskslayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // ----------- TEMP -----------
        tasks = new ArrayList<>();
        goals = new ArrayList<>();
        tasks.add(new Task("Task1"));
        tasks.add(new Task("Task2"));
        tasks.add(new Task("Task3"));
        tasks.add(new Task("Task4"));
        tasks.add(new Task("Task5"));
        // --------- END TEMP ---------

        // Greeting
        greetingText = (TextView) v.findViewById(R.id.greeting_text);
        greetingText.setText(getGreeting());

        // Task feed
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

    private String getGreeting() {
        String greeting = "";
        String user_info = "";
        String user = "User123";
        Calendar rightNow = Calendar.getInstance();

        // Hour (0 - 23)
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);

        // Select proper greeting
        if(currentHour < 10) {
            greeting = "Good morning ";
        } else if (currentHour < 12) {
            greeting = "Hello ";
        } else if (currentHour < 17) {
            greeting = "Good afternoon ";
        } else {
            greeting = "Good evening ";
        }

        // Select user info sentence (total tasks/goals for the day)
        if(tasks.size() == 0 && goals.size() == 0) {
            user_info = "relax! You have nothing to do today.";
        } else if (tasks.size() != 0 && goals.size() == 0) {
            user_info = getSingleUserInfoGreeting(tasks.size()) + " for today.";
        } else if (tasks.size() == 0 && goals.size() != 0) {
            user_info = getSingleUserInfoGreeting(goals.size()) + " for today.";
        } else {
            user_info = getSingleUserInfoGreeting(tasks.size()) + " and "
                    + goals.size() + " goal" + (goals.size() > 1 ? "s" : "") + " for today.";
        }

        greeting += user + ",\n" + user_info;

        return greeting;
    }

    private String getSingleUserInfoGreeting(int value) {
        return "You " + (value < 5 ? "just " : "") + "have " +
                value + " task" + (value > 1 ? "s" : "");
    }
}
