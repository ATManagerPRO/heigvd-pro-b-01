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

    private static int CORNER_RADIUS = 10;
    private static int LINE_HEIGHT = 10;
    private static int VERTICAL_OFFSET = 20;
    private static int HORIZONTAL_PADDING = 40;

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
        // Draw the top part that has rounded corners with twice the height of the radius
        canvas.drawRoundRect(left + HORIZONTAL_PADDING, bottom + VERTICAL_OFFSET, right - HORIZONTAL_PADDING, bottom + VERTICAL_OFFSET + LINE_HEIGHT, CORNER_RADIUS, CORNER_RADIUS, paint);

        // Set color to white for the text that will be drawn next
        paint.setColor(Color.WHITE);
    }
}
