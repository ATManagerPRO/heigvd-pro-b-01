package com.heig.atmanager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Author : Stephane
 * Date   : 22.03.2020
 *
 * Calendar Day Notification graphics (dot)
 */
public class CalendarDayNotification implements LineBackgroundSpan {

    private static final String TAG = "CalendarDayNotification";

    private static final int DOT_RADIUS    = 12;
    private static final int DOT_PADDING_H = 30;
    private static final int DOT_PADDING_V = 20;

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
        paint.setColor(color);
        // rem : charSequence = day of the month
        // user.getTodosAndGoalsForDay(Integer.parseInt(charSequence))
        paint.setAlpha((255 * Integer.parseInt(charSequence.toString())) / maxTodosAndGoals);

        //canvas.drawText("99", (left + right) / 2, (top + bottom) / 2, paint);
        canvas.drawRoundRect(left, top, right, bottom, 10, 10, paint);
        //canvas.drawCircle(right - DOT_PADDING_H, top - DOT_PADDING_V, DOT_RADIUS, paint);

        // Bold and old color font for days
        paint.setColor(oldColor);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

}
