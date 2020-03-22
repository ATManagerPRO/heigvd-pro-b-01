package com.heig.atmanager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Author : Stephane
 * Date   : 22.03.2020
 *
 * Calendar Day Notification graphics (dot)
 */
public class CalendarDayNotification implements LineBackgroundSpan {

    private static final int DOT_RADIUS    = 12;
    private static final int DOT_PADDING_H = 40;
    private static final int DOT_PADDING_V = 20;

    private int totalTodosAndGoals;
    private int color;

    public CalendarDayNotification(int totalTodosAndGoals, int color) {
        this.totalTodosAndGoals = totalTodosAndGoals;
        this.color = color;
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

        canvas.drawCircle(right - DOT_PADDING_H, top - DOT_PADDING_V, DOT_RADIUS, paint);
        paint.setColor(oldColor);
    }
}
