package com.heig.atmanager.tasks;

import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.anychart.scales.Linear;
import com.heig.atmanager.HomeFragment;
import com.heig.atmanager.MainActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.heig.atmanager.R;
import com.heig.atmanager.Utils;
import com.heig.atmanager.taskLists.TaskList;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private Map<LocalDate, Boolean> dateTitles;

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
        private ImageButton removeBtn;
        private ImageButton favoriteBtn;
        private LinearLayout expandedView;
        private ImageView favoriteIcon;
        private ToggleButton checkButton;
        private TextView dateTitle;
        private LinearLayout timeContainer;

        public MyViewHolder(View v) {
            super(v);
            title            = v.findViewById(R.id.task_title);
            description      = v.findViewById(R.id.task_description);
            timeHourText     = v.findViewById(R.id.task_time);
            timeMeridiemText = v.findViewById(R.id.task_time_meridiem);
            taskListText     = v.findViewById(R.id.task_list);
            taskTags         = v.findViewById(R.id.task_tags);
            expandBtn        = v.findViewById(R.id.expand_button);
            retractBtn       = v.findViewById(R.id.retract_button);
            expandedView     = v.findViewById(R.id.task_expanded_view);
            favoriteIcon     = v.findViewById(R.id.favorite_icon);
            checkButton      = v.findViewById(R.id.check_button);
            removeBtn        = v.findViewById(R.id.remove_button);
            favoriteBtn      = v.findViewById(R.id.favorite_button);
            timeContainer    = v.findViewById(R.id.time_container);
            dateTitle        = v.findViewById(R.id.date_title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskFeedAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.tasksFull = new ArrayList<>(tasks);

        // Orders the tasks by date and favorites
        orderTasks();

        dateTitles = new HashMap<>();
        resetDateHashMap();
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
        Calendar dueDateCalendar = Calendar.getInstance();
        if(tasks.get(position).getDueDate() != null){
            dueDateCalendar.setTime(tasks.get(position).getDueDate());
            LocalDate localDueDate = convertToLocalDateViaInstant(tasks.get(position).getDueDate());

            // Date title when it hasn't been shown or is a favorite
            Boolean hasDate = dateTitles.get(localDueDate);
            if((hasDate != null && !hasDate) || tasks.get(position).isFavorite()) {
                holder.dateTitle.setVisibility(View.VISIBLE);
                SimpleDateFormat sdf  = new SimpleDateFormat("dd MMM YYYY");
                holder.dateTitle.setText(sdf.format(dueDateCalendar.getTime()).toUpperCase());
                // Set the shown date as true so the next tasks from the same date don't show it
                // rem : only set it if it's not a favorite since the favorite are on top
                if(!tasks.get(position).isFavorite())
                    dateTitles.put(localDueDate, true);
            } else {
                holder.dateTitle.setVisibility(View.GONE);
            }
        } else {
            holder.dateTitle.setVisibility(View.GONE);
        }
        // Title and description
        holder.title.setText(tasks.get(position).getTitle());
        holder.description.setText(tasks.get(position).getDescription());

        // Tasklist
        if(tasks.get(position).getTasklist() != null) {
            holder.taskListText.setText(tasks.get(position).getTasklist().getName());
        } else {
            holder.taskListText.setText(TaskList.defaultList.getName());
        }

        ViewGroup.LayoutParams params = holder.timeContainer.getLayoutParams();
        if(tasks.get(position).getDueDate() != null) {
            // Time
            SimpleDateFormat time     = new SimpleDateFormat("hh.mm");
            SimpleDateFormat meridiem = new SimpleDateFormat("aa");
            holder.timeHourText.setVisibility(View.VISIBLE);
            holder.timeMeridiemText.setVisibility(View.VISIBLE);
            holder.timeHourText.setText(time.format(dueDateCalendar.getTime()));
            holder.timeMeridiemText.setText(meridiem.format(dueDateCalendar.getTime()));
            params.width = 200;
        } else {
            holder.timeHourText.setVisibility(View.GONE);
            holder.timeMeridiemText.setVisibility(View.GONE);
            params.width = 40;
        }
        holder.timeContainer.setLayoutParams(params);

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
                if(holder.checkButton.isChecked()) {
                    tasks.get(position).setDoneDate(new Date());
                    tasks.get(position).setDone(true);
                } else {
                    tasks.get(position).setDoneDate(null);
                    tasks.get(position).setDone(false);
                }
            }
        });

        // Remove
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getUser().removeTask(tasks.get(position));
                tasks.remove(tasks.get(position));
                notifyItemRemoved(position); // notify the adapter about the removed item
            }
        });

        // Favorite status
        holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.get(position).setFavorite(!tasks.get(position).isFavorite());
                // Reorder and refresh the layout so that the favorite appears on top
                orderTasks();

                // Reset hashmap values
                resetDateHashMap();
                notifyDataSetChanged();
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
                    // Tag match
                    boolean tagMatch = false;
                    for(String tag : task.getTags())
                        if(tag.toLowerCase().trim().contains(filter))
                            tagMatch = true;

                    // Title match
                    if(task.getTitle().toLowerCase().trim().contains(filter) || tagMatch) {
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

    private void orderTasks() {
        ArrayList<Task> favorites = new ArrayList<>();
        ArrayList<Task> others    = new ArrayList<>();

        for(Task task : tasksFull) {
            if(task.isFavorite()) {
                favorites.add(task);
            } else {
                others.add(task);
            }
        }

        // Order by date
        Collections.sort(favorites);
        Collections.sort(others);

        tasksFull = favorites;
        tasksFull.addAll(others);
        tasks = tasksFull;
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void resetDateHashMap() {
        for(Task task : tasks) {
            if(task.getDueDate() != null){
                dateTitles.put(convertToLocalDateViaInstant(task.getDueDate()), false);
            }
        }
    }
}
