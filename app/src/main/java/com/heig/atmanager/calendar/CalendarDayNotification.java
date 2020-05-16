package com.heig.atmanager.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.LineBackgroundSpan;

import androidx.annotation.NonNull;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Calendar Day Notification graphics
 */
public class CalendarDayNotification implements LineBackgroundSpan {

    private static final int HEIGHT        = 50;
    private static final int PADDING       = 10;
    private static final int CORNER_RADIUS = 20;

    private int color;

    public CalendarDayNotification(int color) {
        this.color   = color;
    }

    @Override
    public void drawBackground(
            @NonNull Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            @NonNull CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        paint.setColor(color);

        canvas.drawRoundRect(left + PADDING, top - HEIGHT + PADDING,
                right - PADDING, bottom + HEIGHT - PADDING, CORNER_RADIUS,
                CORNER_RADIUS, paint);

        // Bold and old color font for days
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
