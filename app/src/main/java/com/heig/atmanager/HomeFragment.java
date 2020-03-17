package com.heig.atmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    ArrayList<GoalTodo> goals; // user data
    private RecyclerView goalsRecyclerView;
    private RecyclerView.Adapter goalsAdapter;
    private RecyclerView.LayoutManager goalslayoutManager;

    // Task feed
    ArrayList<Todo> todos; // user data
    private RecyclerView tasksRecyclerView;
    private RecyclerView.Adapter tasksAdapter;
    private RecyclerView.LayoutManager taskslayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // ----------- TEMP -----------
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date dueDateGoal1 = calendar.getTime();
        goals = new ArrayList<>();
        // SQUATS EVERY DAY FOR 5 DAY
        Goal goal = new Goal("SQUATS", 100, Interval.DAY, dueDateGoal1);
        goals = goal.generateTodaysTodos();
        todos = new ArrayList<>();
        todos.add(new Todo("Task1", "This is a really useful task."));
        todos.add(new Todo("Task2", "Rendre labo 1 :\n> Fiche technique\n> Rapport (10 pages)\n> Code source (C++)"));
        todos.add(new Todo("Task3", "..."));
        todos.add(new Todo("Task4", "..."));
        // --------- END TEMP ---------

        // Greeting
        greetingText = (TextView) v.findViewById(R.id.greeting_text);
        greetingText.setText(getGreetings());

        // Task feed
        tasksRecyclerView = (RecyclerView) v.findViewById(R.id.tasks_rv);
        Utils.setupTodosFeed(v, tasksRecyclerView, todos);

        // Goal feed
        goalsRecyclerView = (RecyclerView) v.findViewById(R.id.goals_rv);
        Utils.setupGoalsFeed(v, goalsRecyclerView, goals);

        return v;
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
        if(currentHour < 11) {
            greeting = "Good morning ";
        } else if (currentHour < 13) {
            greeting = "Hello ";
        } else if (currentHour < 18) {
            greeting = "Good afternoon ";
        } else {
            greeting = "Good evening ";
        }

        // Select user info sentence (total tasks/goals for the day)
        if(todos.size() == 0 && goals.size() == 0) {
            user_info = "relax! You have nothing to do today.";
        } else if (todos.size() != 0 && goals.size() == 0) {
            user_info = getSingleUserInfoGreeting(todos.size()) + " for today.";
        } else if (todos.size() == 0) { // goals != 0 always true
            user_info = getSingleUserInfoGreeting(goals.size()) + " for today.";
        } else {
            user_info = getSingleUserInfoGreeting(todos.size()) + " and "
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
