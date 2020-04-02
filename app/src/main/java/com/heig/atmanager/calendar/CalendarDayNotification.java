package com.heig.atmanager.calendar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.LineBackgroundSpan;

import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Calendar Day Notification graphics (dot)
 */
public class CalendarDayNotification implements LineBackgroundSpan {

    private static final int HEIGHT        = 50;
    private static final int PADDING       = 10;
    private static final int CORNER_RADIUS = 20;

    private static final int[] alphaDensity = new int[] {0, 50, 100, 150, 230};

    private int totalTodosAndGoals;
    private int maxTodosAndGoals;
    private int color;

    public CalendarDayNotification(int color) {
        this.color = color;

        // maxTodos = user.getMaxTodosPerDay()...
        this.maxTodosAndGoals   = 40;
    }

    @Override
    public void drawBackground(
            @NonNull Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            @NonNull CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        Random rand = new Random(); // random for now
        paint.setColor(color);
        paint.setAlpha(getDensityAlpha(rand.nextInt(maxTodosAndGoals)));

        // rem : charSequence = day of the month
        // user.getTodosAndGoalsForDay(Integer.parseInt(charSequence))
        //paint.setAlpha((255 * Integer.parseInt(charSequence.toString())) / maxTodosAndGoals);

        canvas.drawRoundRect(left + PADDING, top - HEIGHT + PADDING,
                right - PADDING, bottom + HEIGHT - PADDING, CORNER_RADIUS,
                CORNER_RADIUS, paint);

        // Bold and old color font for days
        paint.setColor(oldColor);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /**
     * Get the density of activity depending on the total of tasks and goals
     * @param totalTodosAndGoals : total activity
     * @return an alpha value from the alphaDensity array
     */
    private int getDensityAlpha(int totalTodosAndGoals) {
        int densityColor = 0;

        for(int i = 0; i < alphaDensity.length; ++i) {
            if (totalTodosAndGoals > i * (maxTodosAndGoals / alphaDensity.length)) {
                densityColor = alphaDensity[i];
            }
        }

        return densityColor;
    }

}
