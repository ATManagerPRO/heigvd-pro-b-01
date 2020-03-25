package com.heig.atmanager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task adapter for the task Recycler view
 */
public class TodoFeedAdapter extends RecyclerView.Adapter<TodoFeedAdapter.MyViewHolder> {
    private static final String TAG = "TodoFeedAdapter";
    
    private ArrayList<Todo> todos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView title;
        private TextView description;
        private Button expandBtn;
        private Button retractBtn;
        private LinearLayout expandedView;
        private ImageView favoriteIcon;
        private ToggleButton checkButton;

        public MyViewHolder(View v) {
            super(v);
            title        = v.findViewById(R.id.task_title);
            description  = v.findViewById(R.id.task_description);
            expandBtn    = v.findViewById(R.id.expand_button);
            retractBtn   = v.findViewById(R.id.retract_button);
            expandedView = v.findViewById(R.id.task_expanded_view);
            favoriteIcon = v.findViewById(R.id.favorite_icon);
            checkButton  = v.findViewById(R.id.check_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TodoFeedAdapter(ArrayList<Todo> todos) {
        this.todos = todos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TodoFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(todos.get(position).getTitle());
        holder.description.setText(todos.get(position).getDescription());
        // Expand / retract details
        holder.expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandedView.setVisibility(View.VISIBLE);
                holder.retractBtn.setVisibility(View.VISIBLE);
                holder.expandBtn.setVisibility(View.GONE);
            }
        });

        holder.retractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandedView.setVisibility(View.GONE);
                holder.retractBtn.setVisibility(View.GONE);
                holder.expandBtn.setVisibility(View.VISIBLE);
            }
        });

        // Favorite
        holder.favoriteIcon.setVisibility(todos.get(position).isFavorite() ? View.VISIBLE : View.GONE);

        // Checkbox
        holder.checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: pressed");
                //holder.checkButton.setChecked(!holder.checkButton.isChecked());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return todos.size();
    }
}
