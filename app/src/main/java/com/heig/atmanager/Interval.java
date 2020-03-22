package com.heig.atmanager;

import java.util.Calendar;
import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 16.03.2020
 *
 * Enum to work with intervals for goals or todos
 */
public enum Interval {
    HOUR  (Calendar.HOUR_OF_DAY),
    DAY   (Calendar.DAY_OF_YEAR),
    WEEK  (Calendar.WEEK_OF_YEAR),
    MONTH (Calendar.MONTH),
    YEAR  (Calendar.YEAR);

    private int calendarInterval;

    Interval(int calendarInterval) {
        this.calendarInterval = calendarInterval;
    }

    public int getCalendarInterval() {
        return calendarInterval;
    }

}
