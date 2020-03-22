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
 * <p>
 * This class better be good.
 */
public class CalendarDayNotification implements LineBackgroundSpan {

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
        // TODO: instead of random, get total goals and todos from that day
        //paint.setAlpha(totalTodosAndGoals);
        Random rand = new Random();
        paint.setAlpha(rand.nextInt(70));

        canvas.drawCircle((left + right) / 2, (top + bottom) / 2, 50, paint);
        paint.setColor(oldColor);
    }
}
