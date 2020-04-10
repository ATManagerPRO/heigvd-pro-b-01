package com.heig.atmanager.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heig.atmanager.R;
import com.heig.atmanager.Utils;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 09.04.2020
 *
 * Recycler View adapter for the goals line by line
 */
public class GoalLineFeedAdapter extends RecyclerView.Adapter<GoalLineFeedAdapter.MyViewHolder> {

    private ArrayList<Goal> goals;
    private FragmentActivity fa;

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        private TextView title;
        private TextView date;
        private TextView percentage;
        private ProgressBar progress;
        private LinearLayout container;

        public MyViewHolder(View v) {
            super(v);
            title      = (TextView) v.findViewById(R.id.goal_title);
            date       = (TextView) v.findViewById(R.id.goal_date);
            percentage = (TextView) v.findViewById(R.id.goal_percentage);
            progress   = (ProgressBar) v.findViewById(R.id.goal_progress);
            container  = (LinearLayout) v.findViewById(R.id.goal_container);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GoalLineFeedAdapter(FragmentActivity fa, ArrayList<Goal> goals) {
        this.goals = goals;
        this.fa = fa;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GoalLineFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_line, parent, false);

        GoalLineFeedAdapter.MyViewHolder vh = new GoalLineFeedAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final GoalLineFeedAdapter.MyViewHolder holder, final int position) {

        final String interval = goals.get(position).getIntervalNumber() == 1 ?
                goals.get(position).getInterval().getAdverb() + " GOAL" :
                "EVERY " + goals.get(position).getIntervalNumber() + " " + goals.get(position).getInterval().getNoun() + " GOAL";
        final String title    = goals.get(position).getQuantity() + " " + goals.get(position).getUnit();
        final String date     = goals.get(position).getDueDate() == null ?
                "↻ Till hell freezes over" :
                "→ " + Utils.dateToString(goals.get(position).getDueDate());

        holder.title.setText(title);
        holder.date.setText(date);
        holder.percentage.setText((int) goals.get(position).getOverallPercentage() + "%");
        holder.progress.setProgress((int) goals.get(position).getOverallPercentage());

        // Add a quantity to a GoalTodo
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Data to pass in the fragment
                Bundle bundle = new Bundle();
                bundle.putString(Goal.INTERVAL_KEY, interval);
                bundle.putString(Goal.TITLE_KEY, title);
                bundle.putString(Goal.DATE_KEY, date);
                bundle.putSerializable(Goal.SERIAL_GOAL_KEY, goals.get(position));
                GoalsTodoFragment goalsTodoFragment = new GoalsTodoFragment();
                goalsTodoFragment.setArguments(bundle);

                // Load goalsTodos fragment
                fa.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, goalsTodoFragment)
                        .commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return goals.size();
    }
}
