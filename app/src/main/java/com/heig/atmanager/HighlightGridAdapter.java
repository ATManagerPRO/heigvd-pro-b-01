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

    public HighlightGridAdapter(Context context, GridView highlightsGrid) {
        this.context = context;
        this.highlightsGrid = highlightsGrid;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ConstraintLayout notificationLayout;
        if(view == null) {

            view = layoutInflater.inflate(R.layout.calendar_day, null);

            //view.setTag(imageView);

        } else {
            //imageView = (ImageView) view.getTag();
        }

        notificationLayout =  (ConstraintLayout) view.findViewById(R.id.day_background);

        //imageView.setImageResource(R.drawable.calendar_day_background);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        notificationLayout.setMinHeight(highlightsGrid.getMeasuredHeight() / MAX_WEEKS_PER_MONTH);

        return view;
    }
}
