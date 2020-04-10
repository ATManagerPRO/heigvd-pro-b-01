package com.heig.atmanager.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heig.atmanager.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author : Stephane
 * Date   : 10.04.2020
 * <p>
 * This class better be good.
 */
public class GoalsTodoFragment extends Fragment {

    // Header
    private TextView interval;
    private TextView title;
    private TextView date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals_todo, container, false);

        // Header setup
        interval = (TextView) v.findViewById(R.id.title_interval);
        interval.setText(getArguments().getString("goal_interval"));
        title = (TextView) v.findViewById(R.id.title);
        title.setText(getArguments().getString("goal_title"));
        date = (TextView) v.findViewById(R.id.title_date);
        date.setText(getArguments().getString("goal_date"));

        return v;
    }
}
