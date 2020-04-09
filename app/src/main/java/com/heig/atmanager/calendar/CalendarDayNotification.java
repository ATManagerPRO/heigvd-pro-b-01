package com.heig.atmanager.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.LineBackgroundSpan;

import com.heig.atmanager.MainActivity;

import java.util.Calendar;
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
    private Context context;

    public CalendarDayNotification(Context context, int color) {
        this.color = color;
        this.context = context;

        // TODO : Should be computed once outside...
        this.maxTodosAndGoals   = ((MainActivity) context).dummyUser.getMaxActivityPerDay();
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
        int totalForDay = ((MainActivity) context).dummyUser.getTotalActivityForDay(calendar.getTime());
        paint.setAlpha(getDensityAlpha(totalForDay));

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
