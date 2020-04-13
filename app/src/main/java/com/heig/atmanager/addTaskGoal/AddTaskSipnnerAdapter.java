package com.heig.atmanager.addTaskGoal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.heig.atmanager.taskLists.TaskList;

import java.util.List;

/**
 * Author : Chau Ying Kot
 * Date   : 09.04.2020
 *
 * Custom Adapter containing a list of TaskList
 **/
public class AddTaskSipnnerAdapter extends ArrayAdapter<TaskList> {

    public AddTaskSipnnerAdapter(@NonNull Context context, int resource, @NonNull List<TaskList> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        // Print the list name
        label.setText(this.getItem(position).getName());
        return label;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView label = (TextView) super.getView(i, view, viewGroup);

        // Print the list name
        label.setText(this.getItem(i).getName());
        return label;
    }
}