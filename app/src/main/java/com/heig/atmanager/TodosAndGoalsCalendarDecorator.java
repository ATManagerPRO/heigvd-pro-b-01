package com.heig.atmanager;

import android.content.Context;
import android.graphics.Color;

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

    private Context context;
    private int color;

    public TodosAndGoalsCalendarDecorator(Context context, int color) {
        this.context = context;
        this.color = color;
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // We decorate all dates with notifications
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.calendar_selection));
        view.addSpan(new CalendarDayNotification(5, color));
    }
}
