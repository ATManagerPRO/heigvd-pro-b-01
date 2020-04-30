package com.heig.atmanager.folders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heig.atmanager.R;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.taskLists.TaskListAdapter;

import java.util.ArrayList;

/**
 *  Author : Teo Ferrari
 *  Date   : 06.04.2020
 *
 *  Folder adapter for recycler view
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private ArrayList<Folder> folders;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        private Button expandBtn;
        private Button retractBtn;
        private LinearLayout expandedView;
        private RecyclerView tasklists;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View v) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(v);

            nameTextView = v.findViewById(R.id.listTitle);
            expandBtn    = v.findViewById(R.id.expand_button);
            retractBtn   = v.findViewById(R.id.retract_button);
            expandedView = v.findViewById(R.id.task_expanded_view);
            tasklists = itemView.findViewById(R.id.list_recycler_view);

        }
    }

    // Pass in the contact array into the constructor
    public FolderAdapter(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate th public Ve custom layout
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_button, parent, false);
        // Return a new holder instance
        FolderAdapter.ViewHolder viewHolder = new FolderAdapter.ViewHolder(v);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Folder folder = folders.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(folder.getName());

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.tasklists.setLayoutManager(linearLayoutManager);
        holder.tasklists.setAdapter(new TaskListAdapter(folders.get(position).getTaskLists()));

    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public  void  setFolders(ArrayList<Folder> folders){
        this.folders = folders;
        notifyDataSetChanged();
    }
}