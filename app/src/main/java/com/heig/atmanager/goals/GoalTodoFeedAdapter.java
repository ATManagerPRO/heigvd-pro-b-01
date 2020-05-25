package com.heig.atmanager.goals;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.heig.atmanager.R;
import com.heig.atmanager.Utils;
import com.heig.atmanager.userData.PatchRequests;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 10.04.2020
 *
 * Adapter for goalsTodo bubbled or line by line
 */
public class GoalTodoFeedAdapter extends RecyclerView.Adapter<GoalTodoFeedAdapter.MyViewHolder> {
    private ArrayList<GoalTodo> goals;
    private boolean bubbled;
    private Context context;

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        private TextView title;
        private TextView doneDate;
        private TextView percentage;
        private TextView unit;
        private TextView totalValue;
        private TextView currentValue;
        private TextView timerValue;
        private ProgressBar progress;
        private ToggleButton addBtn;
        private EditText addNumValue;
        private LinearLayout background;
        private ImageView progressBackground;

        public MyViewHolder(View v) {
            super(v);

            // Line design specifics
            title              = (TextView) v.findViewById(R.id.goal_title);
            doneDate           = (TextView) v.findViewById(R.id.goal_date);
            percentage         = (TextView) v.findViewById(R.id.goal_percentage);
            progressBackground = (ImageView) v.findViewById(R.id.progress_background);

            // Bubbled design specifics
            unit         = (TextView) v.findViewById(R.id.unit_text);
            currentValue = (TextView) v.findViewById(R.id.current_value_text);
            totalValue   = (TextView) v.findViewById(R.id.total_value_text);
            timerValue   = (TextView) v.findViewById(R.id.goal_timer);
            background   = (LinearLayout) v.findViewById(R.id.goal_background);

            // Shared views
            progress     = (ProgressBar) v.findViewById(R.id.goal_progress);
            addBtn       = (ToggleButton) v.findViewById(R.id.goal_add_button);
            addNumValue  = (EditText) v.findViewById(R.id.goal_value_input);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GoalTodoFeedAdapter(boolean bubbled, ArrayList<GoalTodo> goals, Context context) {
        this.context = context;
        this.bubbled = bubbled;
        this.goals   = goals;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GoalTodoFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(bubbled ? R.layout.goal_todo_bubble : R.layout.goal_todo_line, parent, false);

        GoalTodoFeedAdapter.MyViewHolder vh = new GoalTodoFeedAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final GoalTodoFeedAdapter.MyViewHolder holder, final int position) {

        final String unit    = goals.get(position).getUnit();
        final String doneQt  = Utils.formatNumber(goals.get(position).getQuantityDone());
        final String totalQt = Utils.formatNumber(goals.get(position).getTotalQuantity());

        if(bubbled) {
            holder.unit.setText(unit);
            holder.currentValue.setText(doneQt);
            holder.totalValue.setText("/" + totalQt);
            if(goals.get(position).getQuantityDone() >= goals.get(position).getTotalQuantity()) {
                holder.timerValue.setVisibility(View.VISIBLE);
            } else {
                holder.timerValue.setVisibility(View.GONE);
            }
        } else {
            holder.title.setText(doneQt + "/" + totalQt + " " + unit);
            holder.doneDate.setText(Utils.dateToString(goals.get(position).getDueDate()));
            holder.percentage.setText(goals.get(position).getPercentage() + "%");
        }

        holder.progress.setProgress(goals.get(position).getPercentage());
        if(goals.get(position).getPercentage() >= 100) {
            holder.progressBackground.setBackground(
                    ContextCompat.getDrawable(holder.itemView.getContext(),
                            R.drawable.goal_timer_background_completed));
        }

        // Add a quantity to a GoalTodo
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Rotation animation (add button)
                RotateAnimation rotation =
                        (RotateAnimation) AnimationUtils.loadAnimation(view.getContext(),
                                holder.addBtn.isChecked() ? R.anim.rotation_45_anticlockwise :
                                        R.anim.rotation_45_clockwise);
                rotation.setFillAfter(true);
                view.startAnimation(rotation);

                // Translation animation (input)
                AnimationSet scale =
                        (AnimationSet) AnimationUtils.loadAnimation(view.getContext(),
                                holder.addBtn.isChecked() ? R.anim.translation_open_rtl :
                                        R.anim.translation_close_rtl);
                scale.setFillAfter(true);
                holder.addNumValue.setAnimation(scale);

                // Show input display
                holder.addNumValue.setVisibility(holder.addBtn.isChecked() ? View.VISIBLE : View.GONE);
                if(holder.addBtn.isChecked())
                    holder.addNumValue.requestFocus();
            }
        });

        // Set the quantity value to add in a GoalTodo
        holder.addNumValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO : hide keyboard on enter

                    holder.addBtn.setVisibility(View.VISIBLE);
                    holder.addNumValue.setVisibility(View.GONE);

                    int addedValue = 0;

                    if(!TextUtils.isEmpty(holder.addNumValue.getText())) {
                        addedValue = Integer.parseInt(holder.addNumValue.getText().toString());
                    }

                    goals.get(position).addQuantity(addedValue);

                    // Goal completed
                    if(goals.get(position).getQuantityDone() >= goals.get(position).getTotalQuantity()) {
                        if(bubbled) {
                            holder.background.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.goal_background_completed));
                            holder.currentValue.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
                            holder.totalValue.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white_50));
                            holder.unit.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white_50));
                            holder.timerValue.setVisibility(View.VISIBLE);
                        } else {
                            holder.progressBackground.setBackground(
                                        ContextCompat.getDrawable(holder.itemView.getContext(),
                                                R.drawable.goal_timer_background_completed));
                            holder.percentage.setTextSize(12);
                            holder.percentage.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
                        }
                        holder.progress.setVisibility(View.GONE);
                        holder.addBtn.setVisibility(View.GONE);
                        PatchRequests.patchGoalTodoDoneDate(goals.get(position),context);
                    }

                    // Update values
                    if(bubbled) {
                        holder.currentValue.setText(Utils.formatNumber(goals.get(position).getQuantityDone()));
                    } else {
                        holder.title.setText(Utils.formatNumber(goals.get(position).getQuantityDone())
                                + "/" + totalQt + " " + unit);
                        holder.percentage.setText(goals.get(position).getPercentage() + "%");
                    }
                    holder.progress.setProgress(goals.get(position).getPercentage());

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

    public void setGoals(ArrayList<GoalTodo> goals){
        this.goals = goals;
        notifyDataSetChanged();
    }
}