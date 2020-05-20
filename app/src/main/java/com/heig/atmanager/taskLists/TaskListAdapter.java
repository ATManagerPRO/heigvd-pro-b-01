package com.heig.atmanager.taskLists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heig.atmanager.R;

import java.util.ArrayList;

/**
 *  Author : Teo Ferrari
 *  Date   : 06.04.2020
 *
 *  tasklist adapter for recycler view
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private ArrayList<TaskList> lists;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Member variables
        public TextView nameTextView;
        private LinearLayout button;


        public ViewHolder(View v) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.listTitle);
            button = v.findViewById(R.id.list);
        }
    }

    public TaskListAdapter(ArrayList<TaskList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_button, parent, false);
        // Returns a new holder instance
        TaskListAdapter.ViewHolder viewHolder = new com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final com.heig.atmanager.taskLists.TaskListAdapter.ViewHolder holder, int position) {
        TaskList list = lists.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(list.getName());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Action on click
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public  void  setTasks(ArrayList<TaskList> taskLists){
        this.lists = taskLists;
        notifyDataSetChanged();
    }
}