package com.heig.atmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.GridView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stephane
 * Date   : 15.03.2020
 * <p>
 * This class better be good.
 */
public class CalendarFragment extends Fragment {

    private static final String TAG = "CalendarFragment";
    
    // Calendar
    CalendarView calendarView;
    GridView highlights;

    // Goal feed
    ArrayList<GoalTodo> goals; // user data
    private RecyclerView goalsRecyclerView;

    // Task feed
    ArrayList<Todo> todos; // user data
    private RecyclerView tasksRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (CalendarView) v.findViewById(R.id.calendar_view);
        highlights = (GridView) v.findViewById(R.id.highlights_grid);
        todos = new ArrayList<>();
        goals = new ArrayList<>();

        // TODO : Highlight days with todos or goals
        HighlightGridAdapter gridAdapter = new HighlightGridAdapter(v.getContext(), highlights);
        highlights.setAdapter(gridAdapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // i = year, i1 = month, i2 = day
                // TODO: Load todos and goals depending on the calendar selected day
                //       (with API's connection)
            }
        });
        

        // Task feed
        tasksRecyclerView = (RecyclerView) v.findViewById(R.id.tasks_rv);
        Utils.setupTodosFeed(v, tasksRecyclerView, todos);

        // Goal feed
        goalsRecyclerView = (RecyclerView) v.findViewById(R.id.goals_rv);
        Utils.setupGoalsFeed(v, goalsRecyclerView, goals);

        return v;
    }
}
