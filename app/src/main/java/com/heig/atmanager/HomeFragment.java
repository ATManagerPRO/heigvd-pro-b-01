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

    // Goal feed
    ArrayList<Goal> goals; // user data
    private RecyclerView goalsRecyclerView;
    private RecyclerView.Adapter goalsAdapter;
    private RecyclerView.LayoutManager goalslayoutManager;

    // Task feed
    ArrayList<Task> tasks; // user data
    private RecyclerView tasksRecyclerView;
    private RecyclerView.Adapter tasksAdapter;
    private RecyclerView.LayoutManager taskslayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // ----------- TEMP -----------
        goals = new ArrayList<>();
        goals.add(new Goal("SQUATS", 100, 70));
        goals.add(new Goal("KMS", 8, 3));
        tasks = new ArrayList<>();
        tasks.add(new Task("Task1"));
        tasks.add(new Task("Task2"));
        tasks.add(new Task("Task3"));
        tasks.add(new Task("Task4"));
        // --------- END TEMP ---------

        // Greeting
        greetingText = (TextView) v.findViewById(R.id.greeting_text);
        greetingText.setText(getGreetings());

        // Task feed
        setupTasksRecyclerView(v, tasks);

        // Goal feed
        setupGoalsRecyclerView(v, goals);

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

    private void setupGoalsRecyclerView(View v, ArrayList<Goal> goals) {

        goalsRecyclerView = (RecyclerView) v.findViewById(R.id.goals_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        goalsRecyclerView.setHasFixedSize(true);

        // use a (horizontal) linear layout manager
        goalslayoutManager = new LinearLayoutManager(v.getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        goalsRecyclerView.setLayoutManager(goalslayoutManager);

        // specify an adapter (see also next example)
        goalsAdapter = new GoalFeedAdapter(goals);
        goalsRecyclerView.setAdapter(goalsAdapter);
    }

    /**
     * Get the welcoming sentence (dynamic with user's data)
     * @return the proper greetings
     */
    private String getGreetings() {
        Calendar calendar = Calendar.getInstance();
        String greeting = "";
        String user_info = "";
        String user = "User123";

        // Hour (0 - 23)
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

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
        } else if (tasks.size() == 0) { // goals != 0 always true
            user_info = getSingleUserInfoGreeting(goals.size()) + " for today.";
        } else {
            user_info = getSingleUserInfoGreeting(tasks.size()) + " and "
                    + goals.size() + " goal" + (goals.size() > 1 ? "s" : "") + " for today.";
        }

        greeting += user + ",\n" + user_info;

        return greeting;
    }

    /**
     * Get the total of a value in a sentence
     * @param value : the value to display
     * @return the formatted sentence
     */
    private String getSingleUserInfoGreeting(int value) {
        return "You " + (value < 5 ? "just " : "") + "have " +
                value + " task" + (value > 1 ? "s" : "");
    }
}
