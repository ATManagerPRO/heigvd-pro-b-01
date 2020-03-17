package com.heig.atmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    // Today's goal feed
    ArrayList<Goal> goals; // user data
    private RecyclerView goalsTodayRecyclerView;

    // This week's goal feed
    private RecyclerView goalsWeekRecyclerView;

    // This month's goal feed
    private RecyclerView goalsMonthRecyclerView;

    // This year's goal feed
    private RecyclerView goalsYearRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        // ----------- TEMP -----------
        goals = new ArrayList<>();
        // GOAL : 20 squats every day for 5 days
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date dueDateGoal1 = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dueDateGoal2 = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_MONTH, 4);
        Date dueDateGoal3 = calendar.getTime();

        Goal goal1 = new Goal("SQUATS", 20, Interval.DAY, dueDateGoal1);
        Goal goal2 = new Goal("BREAK", 1, Interval.HOUR, dueDateGoal2);
        Goal goal3 = new Goal("KMS", 4, Interval.WEEK, dueDateGoal3);
        goals.add(goal1);
        goals.add(goal2);
        goals.add(goal3);
        // --------- END TEMP ---------

        // Calendar instances (for readability)
        Date today = Calendar.getInstance().getTime();

        // Today's goals setup
        ArrayList<GoalTodo> todayGoals = new ArrayList<>();
        ArrayList<GoalTodo> weekGoals  = new ArrayList<>();
        for(Goal goal : goals) {
            if(goal.getInterval() == Interval.DAY) {
                todayGoals.addAll(goal.getGoalsTodoForDay(today));
            } else if (goal.getInterval() == Interval.WEEK) {
                weekGoals.addAll(goal.getGoalsTodoForDay(today));
            }
        }

        goalsTodayRecyclerView = (RecyclerView) v.findViewById(R.id.goals_today_rv);
        Utils.setupGoalsFeed(v, goalsTodayRecyclerView, todayGoals);

        return v;
    }

}
