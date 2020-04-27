package com.heig.atmanager.calendar;

import android.content.Context;

import com.heig.atmanager.MainActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Decorator to give the user an overiew on how much there is goals and tasks on a day
 * without opening the days' content
 */
public class TasksAndGoalsCalendarDecorator implements DayViewDecorator {

    private Context context;
    private int color;

    public TasksAndGoalsCalendarDecorator(Context context, int color) {
        this.context = context;
        this.color = color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return ((MainActivity) context).getUser().getTotalTasksForDay(convertCalendarDayToDate(day)) != 0;
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Daily notifications
        view.addSpan(new CalendarDayNotification(context, color));
    }

    private Date convertCalendarDayToDate(CalendarDay day) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, day.getYear());
        calendar.set(Calendar.MONTH, day.getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day.getDay());

        return calendar.getTime();
    }
}
