package com.heig.atmanager.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.heig.atmanager.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import androidx.core.content.ContextCompat;

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
        // TODO: return getTotalTodosAndGoals(day) != 0;
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Selection outline when pressing on a day
        Drawable selection_outline = ContextCompat.getDrawable(context, R.drawable.calendar_selection);
        if(selection_outline != null)
            view.setSelectionDrawable(selection_outline);

        // Daily notifications
        view.addSpan(new CalendarDayNotification(color));
    }
}
