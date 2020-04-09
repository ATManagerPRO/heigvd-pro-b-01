package com.heig.atmanager.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heig.atmanager.MainActivity;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 15.03.2020
 *
 * Calendar view of todos and goals
 */
public class CalendarFragment extends Fragment {

    private static final String TAG = "CalendarFragment";
    
    // Calendar
    private MaterialCalendarView calendarView;

    // Task feed
    private ArrayList<Task> tasks; // user data
    private RecyclerView tasksRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = v.findViewById(R.id.calendar_view);
        tasks        = new ArrayList<>();

        // Calendar setup
        Calendar calendar = Calendar.getInstance();
        CalendarDay today = CalendarDay.from(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, // Month 0 to 11 in Calendar
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Notification decorator for todos and goals
        calendarView.addDecorator(new TasksAndGoalsCalendarDecorator(v.getContext(),
                v.getResources().getColor(R.color.day_background_notification, null)));

        // Today's date decorator
        calendarView.addDecorator(new TodayCalendarDecorator(today,
                v.getResources().getColor(R.color.outline_today, null)));

        // Date selection
        calendarView.addDecorator(new SelectionCalendarDecorator(v.getContext()));
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.set(Calendar.DAY_OF_MONTH, date.getDay());
                dateCalendar.set(Calendar.MONTH, date.getMonth() - 1);
                dateCalendar.set(Calendar.YEAR, date.getYear());
                tasks = ((MainActivity) getContext()).dummyUser.getTasksForDay(dateCalendar.getTime());
                Log.d(TAG, "onDateSelected: tasks total for "+ date + " : " + tasks.size());
                Utils.setupTodosFeed(getView(), tasksRecyclerView, tasks);
            }
        });

        // Task feed
        tasksRecyclerView = (RecyclerView) v.findViewById(R.id.tasks_rv);
        Utils.setupTodosFeed(v, tasksRecyclerView, tasks);

        return v;
    }
}
