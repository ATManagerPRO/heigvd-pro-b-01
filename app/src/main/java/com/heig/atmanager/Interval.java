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
    HOUR  (Calendar.HOUR_OF_DAY, "minute"),
    DAY   (Calendar.DAY_OF_YEAR, "hour"),
    WEEK  (Calendar.WEEK_OF_YEAR, "day"),
    MONTH (Calendar.MONTH, "week"),
    YEAR  (Calendar.YEAR, "month");

    private int calendarInterval;
    private String timerValue;

    Interval(int calendarInterval, String timerValue) {
        this.calendarInterval = calendarInterval;
        this.timerValue       = timerValue;
    }

    public int getCalendarInterval() {
        return calendarInterval;
    }

    public String getTimerValue() {
        return timerValue;
    }
}
