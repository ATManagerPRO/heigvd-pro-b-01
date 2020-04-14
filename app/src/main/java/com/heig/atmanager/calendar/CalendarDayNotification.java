package com.heig.atmanager.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.LineBackgroundSpan;

import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;
import com.heig.atmanager.UserController;

import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Calendar Day Notification graphics (dot)
 */
public class CalendarDayNotification implements LineBackgroundSpan {

    private static final String TAG = "CalendarDayNotification";

    private static final int HEIGHT        = 50;
    private static final int PADDING       = 10;
    private static final int CORNER_RADIUS = 20;

    private static final int ALPHA_MIN     = 50;
    private static final int ALPHA_MAX     = 255;

    private int maxTasks;
    private int color;
    private UserController user;

    public CalendarDayNotification(int color, UserController user, int maxTasks) {
        this.color   = color;
        this.user = user;
        this.maxTasks = maxTasks;
    }

    @Override
    public void drawBackground(
            @NonNull Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            @NonNull CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        paint.setColor(color);

        // Setting the proper shade of color depending on the user's activity (total tasks/goals)
        // rem : charSequence = day of the month
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(charSequence.toString()));
        paint.setAlpha(getDensityAlpha(user.getViewModel().getTotalTasksForDay(calendar.getTime())));

        canvas.drawRoundRect(left + PADDING, top - HEIGHT + PADDING,
                right - PADDING, bottom + HEIGHT - PADDING, CORNER_RADIUS,
                CORNER_RADIUS, paint);

        // Bold and old color font for days
        paint.setColor(oldColor);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /**
     * Get the density of activity depending on the total of tasks and goals
     * @return an alpha value from the alphaDensity array
     */
    private int getDensityAlpha(int totalTasks) {
        return Math.max(ALPHA_MAX * totalTasks / maxTasks, ALPHA_MIN);
    }
}
