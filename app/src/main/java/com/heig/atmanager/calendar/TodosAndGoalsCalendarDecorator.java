package com.heig.atmanager.calendar;

import android.content.Context;

import com.heig.atmanager.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import androidx.core.content.ContextCompat;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Decorator to give the user an overiew on how much there is goals and todos on a day
 * without opening the days' content
 */
public class TodosAndGoalsCalendarDecorator implements DayViewDecorator {

    private static final String TAG = "TodosAndGoalsCalendarDe";
    
    private Context context;
    private int color;

    public TodosAndGoalsCalendarDecorator(Context context, int color) {
        this.context = context;
        this.color = color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // return getTotalTodosAndGoals(day) != 0;
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(ContextCompat.getDrawable(context, R.drawable.calendar_selection));
        view.addSpan(new CalendarDayNotification(color));
    }
}
