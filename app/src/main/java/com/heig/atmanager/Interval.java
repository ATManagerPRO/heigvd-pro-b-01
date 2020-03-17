package com.heig.atmanager;

import java.util.Calendar;
import java.util.Date;

/**
 * Author : Stephane
 * Date   : 16.03.2020
 * <p>
 * This class better be good.
 */
public enum Interval {
    HOUR (Calendar.HOUR_OF_DAY),
    DAY (Calendar.DAY_OF_YEAR),
    WEEK (Calendar.WEEK_OF_YEAR),
    MONTH (Calendar.MONTH),
    YEAR (Calendar.YEAR);

    private int calendarInterval;

    Interval(int calendarInterval) {
        this.calendarInterval = calendarInterval;
    }

    /*public Date getDate(Calendar c) {
        //c.add(calendarInterval, 1);
        return c.getTime();
    };*/

    public int getCalendarInterval() {
        return calendarInterval;
    }
}
