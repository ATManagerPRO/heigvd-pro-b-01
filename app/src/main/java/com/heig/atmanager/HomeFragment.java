package com.heig.atmanager;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.core.text.HtmlCompat;
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
    private RecyclerView tasksRecyclerView;
    private RecyclerView.Adapter tasksAdapter;
    private RecyclerView.LayoutManager taskslayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // ----------- TEMP -----------
        tasks = new ArrayList<>();
        tasks.add(new Task("Task1"));
        tasks.add(new Task("Task2"));
        tasks.add(new Task("Task3"));
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
        String user = "User123";
        Calendar rightNow = Calendar.getInstance();
        // Hour (0 - 23)
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);

        if(currentHour < 10) {
            greeting = "Good morning ";
        } else if (currentHour < 12) {
            greeting = "Hello ";
        } else if (currentHour < 17) {
            greeting = "Good afternoon ";
        } else {
            greeting = "Good evening ";
        }

        // TODO : add goals and make text more dynamic (if 0 tasks says you have nothing, etc...)
        greeting += user + ",\nyou have " + tasks.size() + " tasks left for today.";

        return greeting;
    }
}
