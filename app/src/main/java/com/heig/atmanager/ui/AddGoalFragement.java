package com.heig.atmanager.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.heig.atmanager.GoalTask;
import com.heig.atmanager.Interval;
import com.heig.atmanager.R;
import com.heig.atmanager.ui.adapter.AddTaskInGoalAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddGoalFragement extends Fragment {

    private TextInputEditText goalTitle;
    private DatePickerDialog picker;
    private final Calendar calendar = Calendar.getInstance();
    private int mDay;
    private int mMonth;
    private int mYear;

    private RecyclerView goalTasksRV;

    private TextView dueDate;

    private ArrayList<GoalTask> goalTasks = new ArrayList<>();

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    public AddGoalFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_goal, container, false);

        goalTitle = view.findViewById(R.id.frag_add_goal_title);

        dueDate = view.findViewById(R.id.frag_add_goal_due_date);

        goalTasksRV = view.findViewById(R.id.frag_goa_add_goal_task_rv);

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dueDate.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, mYear, mMonth, mDay);
                picker.show();
            }
        });

        goalTasksRV.setHasFixedSize(true);

        manager = new LinearLayoutManager(view.getContext());
        goalTasksRV.setLayoutManager(manager);

        //TEmp
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date dueDateGoal1 = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dueDateGoal2 = calendar.getTime();

        GoalTask goal1 = new GoalTask("SQUATS", 20, 2, Interval.DAY, dueDateGoal1);
        GoalTask goal2 = new GoalTask("BREAK", 1, 3, Interval.HOUR, dueDateGoal2);

        goalTasks.add(goal1);
        goalTasks.add(goal2);

        adapter = new AddTaskInGoalAdapter(goalTasks);
        goalTasksRV.setAdapter(adapter);

        return view;
    }
}
