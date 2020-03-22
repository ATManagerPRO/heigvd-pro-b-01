package com.heig.atmanager;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Author : Stéphane Bottin
 * Date   : 22.03.2020
 *
 * Graphic for today's calendar notification
 */
public class CalendarDayToday  implements LineBackgroundSpan {

    private static int OUTLINE_WIDTH = 6;

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
        int oldColor = paint.getColor();
        paint.setColor(color);
        paint.setStrokeWidth(OUTLINE_WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((left + right) / 2, (top + bottom) / 2, 50, paint);

        paint.setColor(oldColor);
        paint.setStyle(Paint.Style.FILL);

    }
}
