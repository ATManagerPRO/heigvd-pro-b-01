package com.heig.atmanager.taskLists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heig.atmanager.R;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder> {

    private ArrayList<TaskList> lists;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View v) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.listTitle);
        }
    }

    // Pass in the contact array into the constructor
    public TaskListAdapter(ArrayList<TaskList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate th public Ve custom layout
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_button, parent, false);
        // Return a new holder instance
        com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder viewHolder = new com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder holder, int position) {
        TaskList list = lists.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(list.getName());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}