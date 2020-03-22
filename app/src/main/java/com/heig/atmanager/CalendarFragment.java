package com.heig.atmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.GridView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
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
    private MaterialCalendarView calendarView;

    // Goal feed
    private ArrayList<GoalTodo> goals; // user data
    private RecyclerView goalsRecyclerView;

    // Task feed
    private ArrayList<Todo> todos; // user data
    private RecyclerView tasksRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = v.findViewById(R.id.calendar_view);
        todos        = new ArrayList<>();
        goals        = new ArrayList<>();

        // Calendar setup
        Calendar calendar = Calendar.getInstance();
        CalendarDay today = CalendarDay.from(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, // Month 0 to 11 in Calendar
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        // Notification decorator for todos and goals
        calendarView.addDecorator(new TodosAndGoalsCalendarDecorator());
        // Today's date decorator
        calendarView.addDecorator(new TodayCalendarDecorator(today, v.getResources().getColor(R.color.colorAccent, null)));

        // Task feed
        tasksRecyclerView = (RecyclerView) v.findViewById(R.id.tasks_rv);
        Utils.setupTodosFeed(v, tasksRecyclerView, todos);

        // Goal feed
        goalsRecyclerView = (RecyclerView) v.findViewById(R.id.goals_rv);
        Utils.setupGoalsFeed(v, goalsRecyclerView, goals);

        return v;
    }
}
