package com.heig.atmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.heig.atmanager.dialog.ShareTaskListDiag;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.taskLists.TaskList;

import java.util.ArrayList;

/**
 * Author : Stéphane Bottin
 * Date   : 12.04.2020
 * <p>
 * Adapter to display the tasklists and (collapsing) folders in the drawer menu
 */
public class DrawerListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<DrawerObject> objects;
    private Activity activity;

    public DrawerListAdapter(Context context, ArrayList<TaskList> taskLists,
                             ArrayList<Folder> folders, Activity activity) {
        this.context = context;
        this.objects = new ArrayList<>();
        this.objects.addAll(taskLists);
        this.objects.addAll(folders);
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return objects.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return objects.get(i).isFolder() ? ((Folder) objects.get(i)).getTaskLists().size() : 0;
    }

    @Override
    public Object getGroup(int i) {
        return objects.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return objects.get(i).isFolder() ? ((Folder) objects.get(i)).getTaskLists().get(i1) : null;
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

        String name = ((DrawerObject) getGroup(i)).getName();

        if (view == null)
            view = LayoutInflater.from(context).inflate(
                    ((DrawerObject) getGroup(i)).isFolder() ?
                            R.layout.drawer_list_group : R.layout.drawer_list_item
                    , null);

        TextView title = (TextView) view.findViewById(R.id.drawer_object_title);
        title.setText(name);
        Button shareBtn = view.findViewById(R.id.share);

        if(!((DrawerObject) getGroup(i)).isFolder()){
            shareBtn.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String taskListName = ((TaskList) getChild(i, i1)).getName();

        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, null);

        TextView title = (TextView) view.findViewById(R.id.drawer_object_title);
        title.setText(taskListName);

        Button shareBtn = view.findViewById(R.id.share);


        // Set false to separate the click on the button and the list
        shareBtn.setFocusable(false);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Replace with the real id


                ShareTaskListDiag diag = new ShareTaskListDiag();
                // Get task id
                diag.addTaskListId(1);
                ((MainActivity) activity).showShareTaskDialog();

            }
        });


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
