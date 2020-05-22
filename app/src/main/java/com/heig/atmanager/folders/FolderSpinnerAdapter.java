package com.heig.atmanager.folders;

import android.widget.ArrayAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


/**
 * Author : Chau Ying Kot
 * Date   : 29.04.2020
 *
 * This class custom the basic arrayAdapter to show the folder name in the spinner
 **/
public class FolderSpinnerAdapter extends ArrayAdapter<Folder> {

    public FolderSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Folder> objects) {
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