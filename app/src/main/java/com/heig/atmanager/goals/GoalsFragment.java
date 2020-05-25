package com.heig.atmanager.goals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Date   : 15.03.2020
 *
 * User's goals depending on the interval of it.
 * Rem : if a goal is spread in a year for every day, it will show up only once in the today
 *       category. Same goes for weeks and months. This is to avoid unhelpful duplicates.
 */
public class GoalsFragment extends Fragment {

    public static final String FRAG_GOALS_ID = "Goals_Fragment";

    // Today's goal feed
    ArrayList<Goal> goals; // user data
    private RecyclerView goalsTodayRecyclerView;

    // This week's goal feed
    private RecyclerView goalsWeekRecyclerView;

    // This month's goal feed
    private RecyclerView goalsMonthRecyclerView;

    // This year's goal feed
    private RecyclerView goalsYearRecyclerView;

    private static final String TAG = "GoalsFragment";
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        goals = MainActivity.getUser().getGoals();

        // Displaying the generating GoalTodo from the goals by intervals
        ArrayList<Goal> todayGoals = new ArrayList<>();
        ArrayList<Goal> weekGoals  = new ArrayList<>();
        ArrayList<Goal> monthGoals = new ArrayList<>();
        ArrayList<Goal> yearGoals  = new ArrayList<>();
        for(Goal goal : goals) {
            Log.d(TAG, "onCreateView: " + goal.getInterval());
            Log.d(TAG, "onCreateView: " + goal.getUnit());
            switch(goal.getInterval()) {
                case DAY:
                    todayGoals.add(goal);
                    break;
                case WEEK:
                    weekGoals.add(goal);
                    break;
                case MONTH:
                    monthGoals.add(goal);
                    break;
                case YEAR:
                    yearGoals.add(goal);
                    break;
            }
        }
        // Goals feeds setup
        goalsTodayRecyclerView = (RecyclerView) v.findViewById(R.id.goals_today_rv);
        Utils.setupGoalsFeed(getActivity(), v, goalsTodayRecyclerView, todayGoals);

        goalsWeekRecyclerView = (RecyclerView) v.findViewById(R.id.goals_week_rv);
        Utils.setupGoalsFeed(getActivity(), v, goalsWeekRecyclerView, weekGoals);

        goalsMonthRecyclerView = (RecyclerView) v.findViewById(R.id.goals_month_rv);
        Utils.setupGoalsFeed(getActivity(), v, goalsMonthRecyclerView, monthGoals);

        goalsYearRecyclerView = (RecyclerView) v.findViewById(R.id.goals_year_rv);
        Utils.setupGoalsFeed(getActivity(), v, goalsYearRecyclerView, yearGoals);
        return v;
    }

}
