package com.heig.atmanager.goals;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 10.04.2020
 */
public class GoalsTodoFragment extends Fragment {

    private static final String TAG = "GoalsTodoFragment";
    public static final String FRAG_GOALS_TODO_ID = "GoalsTodo_Fragment";

    // Header
    private TextView interval;
    private TextView title;
    private TextView date;

    // GoalTodos
    private ArrayList<GoalTodo> goalTodos;
    private RecyclerView goalsTodoRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals_todo, container, false);

        // Change top button as back button
        MainActivity.previousFragment = GoalsFragment.FRAG_GOALS_ID;
        ((MainActivity) getActivity()).enableBackButton(true);

        // Header setup
        interval = (TextView) v.findViewById(R.id.title_interval);
        interval.setText(getArguments().getString(Goal.INTERVAL_KEY));
        title = (TextView) v.findViewById(R.id.title);
        title.setText(getArguments().getString(Goal.TITLE_KEY));
        date = (TextView) v.findViewById(R.id.title_date);
        date.setText(getArguments().getString(Goal.DATE_KEY));

        // GoalTodos of the goal send in th bundle
        goalTodos = ((Goal) getArguments().getSerializable(Goal.SERIAL_GOAL_KEY)).getGoalTodos();
        goalsTodoRecyclerView = (RecyclerView) v.findViewById(R.id.goalstodo_rv);
        Utils.setupGoalTodosFeedLined(v, goalsTodoRecyclerView, goalTodos);

        return v;
    }

}
