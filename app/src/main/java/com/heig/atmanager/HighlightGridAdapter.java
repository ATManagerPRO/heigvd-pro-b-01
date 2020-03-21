package com.heig.atmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

/**
 * Author : Stéphane Bottin
 * Date   : 20.03.2020
 *
 * Highlight Grid adapter for the calendar's heatmap
 */
public class HighlightGridAdapter extends BaseAdapter {

    private static final String TAG = "HighlightGridAdapter";
    
    private static final int DAYS_PER_WEEK       = 7;
    private static final int MAX_WEEKS_PER_MONTH = 5;

    Context context;
    LayoutInflater layoutInflater;
    GridView highlightsGrid;
    Calendar calendar;
    boolean clearNotifications;

    public HighlightGridAdapter(Context context, GridView highlightsGrid, Calendar calendar) {
        this.context = context;
        this.highlightsGrid = highlightsGrid;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.calendar = calendar;
    }

    @Override
    public int getCount() {
        return MAX_WEEKS_PER_MONTH * DAYS_PER_WEEK;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = layoutInflater.inflate(R.layout.calendar_day, null);

        ConstraintLayout notificationLayout = (ConstraintLayout) view.findViewById(R.id.day_background);
        notificationLayout.setMinHeight(highlightsGrid.getMeasuredHeight() / MAX_WEEKS_PER_MONTH);

        if(i < calendar.getFirstDayOfWeek())
            notificationLayout.setVisibility(View.INVISIBLE);

        return view;
    }

    public void refreshNewCalendar(Calendar calendar) {
        // Change the calendar (the month)
        this.calendar = calendar;

        // Refresh the grid's layout
        highlightsGrid.invalidateViews();
    }

    public void goToNextMonth() {
        Log.d(TAG, "goToNextMonth: Going to next month...");
        // Clear all notification of this month
        clearNotifications = true;
        highlightsGrid.invalidateViews();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        this.calendar = calendar;

        // Refresh with next month's calendar
        //clearNotifications = false;
        //refreshNewCalendar(calendar);
    }
}
