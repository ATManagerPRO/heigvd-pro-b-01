package com.heig.atmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.taskLists.TaskList;

import java.util.ArrayList;

/**
 * Author : Stéphane Bottin
 * Date   : 12.04.2020
 *
 * Adapter to display the tasklists and (collapsing) folders in the drawer menu
 */
public class FolderDrawerListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Folder> folders;

    public FolderDrawerListAdapter(Context context, ArrayList<Folder> folders) {
        this.context = context;
        this.folders = folders;
    }

    @Override
    public int getGroupCount() {
        return folders.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return folders.get(i).getTaskLists().size();
    }

    @Override
    public Object getGroup(int i) {
        return folders.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return folders.get(i).getTaskLists().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String folderName = ((Folder) getGroup(i)).getName();

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.drawer_list_group, null);

        TextView title = (TextView) view.findViewById(R.id.folderTitle);
        title.setText(folderName);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String taskListName = ((TaskList) getChild(i, i1)).getName();

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, null);

        TextView title = (TextView) view.findViewById(R.id.taskListTitle);
        title.setText(taskListName);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
