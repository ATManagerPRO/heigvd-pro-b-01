package com.heig.atmanager.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heig.atmanager.MainActivity;
import com.heig.atmanager.goals.GoalsFragment;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 15.03.2020
 *
 * Calendar view of todos and goals
 */
public class CalendarFragment extends Fragment {

    public static final String FRAG_CALENDAR_ID = "Calendar_Fragment";

    // Calendar
    private MaterialCalendarView calendarView;
    private int notificationDotColor = Color.rgb(255, 0, 0);

    // Goal feed
    private ArrayList<GoalTodo> goals; // user data
    private RecyclerView goalsRecyclerView;

    // Task feed
    private ArrayList<Task> tasks; // user data
    private RecyclerView tasksRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = v.findViewById(R.id.calendar_view);
        tasks = new ArrayList<>();
        goals        = new ArrayList<>();

        // Calendar setup
        Calendar calendar = Calendar.getInstance();
        CalendarDay today = CalendarDay.from(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, // Month 0 to 11 in Calendar
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        // Notification decorator for todos and goals
        calendarView.addDecorator(new TasksAndGoalsCalendarDecorator(v.getContext(),
                v.getResources().getColor(R.color.colorAccent, null)));
        // Today's date decorator
        calendarView.addDecorator(new TodayCalendarDecorator(today, notificationDotColor));

        // Task feed
        tasksRecyclerView = (RecyclerView) v.findViewById(R.id.tasks_rv);
        Utils.setupTasksFeed(v, tasksRecyclerView, tasks);

        // Goal feed
        //goalsRecyclerView = (RecyclerView) v.findViewById(R.id.goals_rv);
        //Utils.setupGoalsFeed(v, goalsRecyclerView, goals);

        return v;
    }
}
