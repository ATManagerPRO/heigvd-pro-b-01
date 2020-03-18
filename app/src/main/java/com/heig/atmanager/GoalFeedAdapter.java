package com.heig.atmanager;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
private ArrayList<GoalTodo> goals;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class MyViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    private TextView unit;
    private TextView totalValue;
    private TextView currentValue;
    private TextView timerValue;
    private ProgressBar progress;
    private Button addBtn;
    private EditText addNumValue;

    public MyViewHolder(View v) {
        super(v);
        unit         = (TextView) v.findViewById(R.id.unit_text);
        currentValue = (TextView) v.findViewById(R.id.current_value_text);
        totalValue   = (TextView) v.findViewById(R.id.total_value_text);
        timerValue   = (TextView) v.findViewById(R.id.goal_timer);
        progress     = (ProgressBar) v.findViewById(R.id.goal_progress);
        addBtn       = (Button) v.findViewById(R.id.goal_add_button);
        addNumValue  = (EditText) v.findViewById(R.id.goal_value_input);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public GoalFeedAdapter(ArrayList<GoalTodo> goals) {
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
    public void onBindViewHolder(final GoalFeedAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.unit.setText(goals.get(position).getUnit());
        holder.currentValue.setText(goals.get(position).getStringCurrent());
        holder.totalValue.setText("/" + goals.get(position).getStringTotal());
        holder.timerValue.setText(goals.get(position).getTimerValue());
        holder.progress.setProgress(goals.get(position).getPercentage());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addBtn.setVisibility(View.GONE);
                holder.addNumValue.setVisibility(View.VISIBLE);
            }
        });

        holder.addNumValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // TODO : hide keyboard on enter

                    holder.addBtn.setVisibility(View.VISIBLE);
                    holder.addNumValue.setVisibility(View.GONE);

                    int addedValue = 0;

                    if(holder.addNumValue.getText().toString() != "" &&
                            holder.addNumValue.getText().toString() != "##") {
                        addedValue = Integer.parseInt(holder.addNumValue.getText().toString());
                    }

                    goals.get(position).addQuantity(addedValue);

                    // Update text display
                    holder.currentValue.setText(goals.get(position).getStringCurrent());
                    return true;
                }
                return false;
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return goals.size();
    }
}
