package com.heig.atmanager.goals;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.heig.atmanager.R;
import com.heig.atmanager.Utils;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stephane
 * Date   : 09.04.2020
 * <p>
 * This class better be good.
 */
public class GoalLineFeedAdapter extends RecyclerView.Adapter<GoalLineFeedAdapter.MyViewHolder> {
    private ArrayList<Goal> goals;

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
    public GoalLineFeedAdapter(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GoalLineFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_bubble, parent, false);

        GoalLineFeedAdapter.MyViewHolder vh = new GoalLineFeedAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final GoalLineFeedAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(goals.get(position).getQuantity() + " " + goals.get(position).getUnit());
        holder.date.setText(goals.get(position).getDueDate().toString());
        holder.progress.setProgress((int) goals.get(position).getOverallPercentage());

        // Add a quantity to a GoalTodo
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : opens goals todos view
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return goals.size();
    }
}
