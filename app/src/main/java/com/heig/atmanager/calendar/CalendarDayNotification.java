package com.heig.atmanager.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;

import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Calendar Day Notification graphics
 */
public class CalendarDayNotification implements LineBackgroundSpan {

    private static final String TAG = "CalendarDayNotification";

    private static final int HEIGHT        = 50;
    private static final int PADDING       = 10;
    private static final int CORNER_RADIUS = 20;

    private int color;
    private Context context;

    public CalendarDayNotification(Context context, int color) {
        this.color   = color;
        this.context = context;
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

        canvas.drawRoundRect(left + PADDING, top - HEIGHT + PADDING,
                right - PADDING, bottom + HEIGHT - PADDING, CORNER_RADIUS,
                CORNER_RADIUS, paint);

        // Bold and old color font for days
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
