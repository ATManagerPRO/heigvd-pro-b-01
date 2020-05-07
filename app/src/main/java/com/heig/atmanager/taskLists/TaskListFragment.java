package com.heig.atmanager.taskLists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.tasks.TaskFeedAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Author : Stephane
 * Date   : 12.04.2020
 * <p>
 * This class better be good.
 */
public class TaskListFragment extends Fragment {

    private TextView title;
    private RecyclerView tasksRv;
    private RecyclerView.Adapter tasksAdapter;
    private ArrayList<Task> tasks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tasks, container, false);

        // XML
        title = (TextView) v.findViewById(R.id.tasklist_title);
        tasksRv = (RecyclerView) v.findViewById(R.id.tasks_rv);

        // Opened tasklist
        TaskList taskList = ((TaskList) getArguments().getSerializable(TaskList.SERIAL_TASK_LIST_KEY));

        // Setup the data into the XML (new thread ?)
        title.setText(taskList.getName());

        tasks = new ArrayList<Task>();

        for (Task task : (((MainActivity) getContext()).getUser().getTasks())) {
            if (task.getTasklist().equals(taskList)) {
                tasks.add(task);
            }
        }

        // Setup feed and link it to the main for searches
        tasksRv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(v.getContext());
        tasksRv.setLayoutManager(manager);
        tasksAdapter = new TaskFeedAdapter(tasks);
        tasksRv.setAdapter(tasksAdapter);
        // Set adapter for searches
        ((MainActivity) getContext()).setContentAdapter(tasksAdapter);

        return v;
    }
}