package com.heig.atmanager;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Decorator to give the user an overiew on how much there is goals and todos on a day
 * without opening the days' content
 */
public class TodosAndGoalsCalendarDecorator implements DayViewDecorator {

    private static final int notificationColor = Color.rgb(0, 0, 0);

    public TodosAndGoalsCalendarDecorator() {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // We decorate all dates with notifications
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new CalendarDayNotification(5, notificationColor));
    }
}
