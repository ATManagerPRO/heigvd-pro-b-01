package com.heig.atmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Author : Stéphane Bottin
 * Date   : 12.04.2020
 *
 * Adapter to display the tasklists and (collapsing) folders in the drawer menu
 */
public class DrawerListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<DrawerObject> objects;

    public DrawerListAdapter(Context context, ArrayList<TaskList> taskLists,
                             ArrayList<Folder> folders) {
        this.context = context;
        this.objects = new ArrayList<>();
        this.objects.addAll(taskLists);
        this.objects.addAll(folders);
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

        if(view == null)
            view = LayoutInflater.from(context).inflate(
                    ((DrawerObject) getGroup(i)).isFolder() ?
                            R.layout.drawer_list_group : R.layout.drawer_list_item
                    , null);

        TextView title = (TextView) view.findViewById(R.id.drawer_object_title);
        title.setText(name);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String taskListName = ((TaskList) getChild(i, i1)).getName();

        if(view == null)
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

                onShareClicked(1, ((MainActivity) view.getContext()).user.getUserName(), view);
            }
        });


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    /**
     * Action done when we click on share
     * @param id Task id
     * @param userName sender user name
     * @param v on which view
     */
    private void onShareClicked(int id, String userName, View v) {

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("atmanager.com")
                //Need to add the real id
                .appendQueryParameter("taskId", String.valueOf(id))
                .appendQueryParameter("userName", userName);


        Log.d(TAG, "onShareClicked : " + builder.build().toString());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, builder.build().toString());
        v.getContext().startActivity(Intent.createChooser(intent, "Share Task"));
    }
}
