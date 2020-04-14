package com.heig.atmanager;

import java.util.Calendar;
import java.util.Date;

/**
 * Author : Stéphane Bottin
 * Date   : 16.03.2020
 *
 * Enum to work with intervals for goals or tasks
 */
public enum Interval {
    HOUR  (Calendar.HOUR_OF_DAY, "HOURLY", "HOUR"),
    DAY   (Calendar.DAY_OF_YEAR, "DAILY", "DAY"),
    WEEK  (Calendar.WEEK_OF_YEAR, "WEEKLY", "WEEK"),
    MONTH (Calendar.MONTH, "MONTHLY", "MONTH"),
    YEAR  (Calendar.YEAR, "YEARLY", "YEAR");

    private int calendarInterval;
    private String adverb;
    private String noun;

    Interval(int calendarInterval, String adverb, String noun) {
        this.calendarInterval = calendarInterval;
        this.adverb = adverb;
        this.noun = noun;
    }

    public int getCalendarInterval() {
        return calendarInterval;
    }

    public String getAdverb() {return adverb;}
    public String getNoun() {return noun;}

}
