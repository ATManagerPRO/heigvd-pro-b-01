package com.heig.atmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 12.03.2020
 *
 * Goal feed adapter for the home view
 */
public class GoalFeedAdapter extends RecyclerView.Adapter<GoalFeedAdapter.MyViewHolder> {
private ArrayList<Goal> goals;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class MyViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    private TextView unit;
    private TextView totalValue;
    private TextView currentValue;
    private ProgressBar progress;

    public MyViewHolder(View v) {
        super(v);
        unit         = (TextView) v.findViewById(R.id.unit_text);
        currentValue = (TextView) v.findViewById(R.id.current_value_text);
        totalValue   = (TextView) v.findViewById(R.id.total_value_text);
        progress     = (ProgressBar) v.findViewById(R.id.goal_progress);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public GoalFeedAdapter(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GoalFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal, parent, false);

        GoalFeedAdapter.MyViewHolder vh = new GoalFeedAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GoalFeedAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.unit.setText(goals.get(position).getUnit());
        holder.currentValue.setText(goals.get(position).getStringCurrent());
        holder.totalValue.setText("/" + goals.get(position).getStringTotal());
        holder.progress.setProgress(goals.get(position).getPercentage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return goals.size();
    }
}
