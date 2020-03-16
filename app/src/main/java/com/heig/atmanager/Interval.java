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
    HOUR {
        @Override
        public Date getNextDate(Calendar c) {
            c.add(Calendar.HOUR_OF_DAY, 1);
            return c.getTime();
        }
    },
    DAY {
        @Override
        public Date getNextDate(Calendar c) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            return c.getTime();
        }
    },
    WEEK {
        @Override
        public Date getNextDate(Calendar c) {
            c.add(Calendar.WEEK_OF_MONTH, 1);
            return c.getTime();
        }
    };
    // TODO :
    //MONTH,
    //YEAR;

    public abstract Date getNextDate(Calendar c);
}
