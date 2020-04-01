package com.heig.atmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private ArrayList<Folder> folders;

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

            nameTextView = (TextView) v.findViewById(R.id.folderTitle);
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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = folders.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(folder.getName());
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }
}