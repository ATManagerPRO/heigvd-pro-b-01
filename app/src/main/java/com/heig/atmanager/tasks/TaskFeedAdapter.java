package com.heig.atmanager.tasks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.heig.atmanager.R;
import com.heig.atmanager.Utils;
import com.heig.atmanager.taskLists.TaskList;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 11.03.2020
 *
 * Task adapter for the task Recycler view
 */
public class TaskFeedAdapter extends RecyclerView.Adapter<TaskFeedAdapter.MyViewHolder> implements Filterable {
    private static final String TAG = "TaskFeedAdapter";

    private ArrayList<Task> tasks;
    private ArrayList<Task> tasksFull;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView title;
        private TextView description;
        private TextView timeHourText;
        private TextView timeMeridiemText;
        private TextView taskListText;
        private TextView taskTags;
        private Button expandBtn;
        private Button retractBtn;
        private LinearLayout expandedView;
        private ImageView favoriteIcon;
        private ToggleButton checkButton;

        public MyViewHolder(View v) {
            super(v);
            title        = v.findViewById(R.id.task_title);
            description  = v.findViewById(R.id.task_description);
            timeHourText     = v.findViewById(R.id.task_time);
            timeMeridiemText     = v.findViewById(R.id.task_time_meridiem);
            taskListText = v.findViewById(R.id.task_list);
            taskTags     = v.findViewById(R.id.task_tags);
            expandBtn    = v.findViewById(R.id.expand_button);
            retractBtn   = v.findViewById(R.id.retract_button);
            expandedView = v.findViewById(R.id.task_expanded_view);
            favoriteIcon = v.findViewById(R.id.favorite_icon);
            checkButton  = v.findViewById(R.id.check_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskFeedAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.tasksFull = new ArrayList<>(tasks);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(tasks.get(position).getTitle());
        holder.description.setText(tasks.get(position).getDescription());
        // Tasklist
        if(tasks.get(position).getTasklist() != null) {
            holder.taskListText.setText(tasks.get(position).getTasklist().getName());
        } else {
            holder.taskListText.setText(TaskList.defaultList.getName());
        }
        // Time
        String hours    = "";
        String minutes  = "";
        String meridiem = "";
        if(tasks.get(position).getDueDate() != null) {
            Calendar dueDateCalendar = Calendar.getInstance();
            dueDateCalendar.setTime(tasks.get(position).getDueDate());
            hours    = Utils.formatNumber(dueDateCalendar.get(Calendar.HOUR_OF_DAY) % 12) + ":";
            minutes  = Utils.formatNumber(dueDateCalendar.get(Calendar.MINUTE));
            meridiem = dueDateCalendar.get(Calendar.HOUR_OF_DAY) < 12 ? "AM" : "PM";
        }
        holder.timeHourText.setText(hours + minutes);
        holder.timeMeridiemText.setText(meridiem);

        // Tags
        String tagsString = "";
        for(String tag : tasks.get(position).getTags())
            tagsString += "#" + tag + " ";
        holder.taskTags.setText(tagsString);

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
        holder.favoriteIcon.setVisibility(tasks.get(position).isFavorite() ? View.VISIBLE : View.GONE);

        // Checkbox
        holder.checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.get(position).setDone(holder.checkButton.isChecked());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public  void  setTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return taskFilter;
    }

    private Filter taskFilter = new Filter() {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Task> filteredTasks = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0) {
                filteredTasks.addAll(tasksFull);
            } else {
                String filter = charSequence.toString().toLowerCase().trim();
                for(Task task : tasksFull) {
                    if(task.getTitle().toLowerCase().trim().contains(filter)) {
                        filteredTasks.add(task);
                    }
                }

            }

            Filter.FilterResults results = new Filter.FilterResults();
            results.values = filteredTasks;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults
        filterResults) {
            tasks.clear();
            tasks.addAll((ArrayList<Task>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
