package com.heig.atmanager.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.heig.atmanager.MainActivity;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
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
 * <p>
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

        tasksRecyclerView = v.findViewById(R.id.tasks_rv);
        calendarView = v.findViewById(R.id.calendar_view);
        tasks = new ArrayList<>();

        // Calendar setup
        final Calendar calendar = Calendar.getInstance();
        CalendarDay today = CalendarDay.from(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, // Month 0 to 11 in Calendar
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        tasksRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //DOWN
                    if (calendarView.getCalendarMode().equals(CalendarMode.MONTHS)) {
                        calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
                    }
                }
            }
        });

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
                tasks = ((MainActivity) getContext()).getUser().getTasksForDay(dateCalendar.getTime());
                Log.d(TAG, "onDateSelected: tasks total for " + date + " : " + tasks.size());
                Utils.setupTasksFeed(getView(), tasksRecyclerView, tasks);
            }
        });

        calendarView.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getCalendarMode().equals(CalendarMode.WEEKS)) {
                    calendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
                }
            }
        });

        // Task feed
        tasksRecyclerView = (RecyclerView) v.findViewById(R.id.tasks_rv);
        Utils.setupTasksFeed(v, tasksRecyclerView, tasks);

        return v;
    }
}
