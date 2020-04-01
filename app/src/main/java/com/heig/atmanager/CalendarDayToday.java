package com.heig.atmanager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import androidx.annotation.NonNull;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Graphic for today's calendar notification
 */
public class CalendarDayToday  implements LineBackgroundSpan {

    private static int DOT_RADIUS = 50;

    int color;

    public CalendarDayToday(int color) {
        this.color = color;
    }

    @Override
    public void drawBackground(
            @NonNull Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            @NonNull CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        paint.setColor(color);
        canvas.drawCircle((left + right) / 2, (top + bottom) / 2, DOT_RADIUS, paint);

        // Set color to white for the text that will be drawn next
        paint.setColor(Color.WHITE);

    }
}
