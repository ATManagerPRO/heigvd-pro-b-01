package com.heig.atmanager.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heig.atmanager.Interval;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;
import com.heig.atmanager.UserController;
import com.heig.atmanager.Utils;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        UserController user = ((UserController) getArguments().getSerializable(UserController.SERIAL_USER_KEY));

        goals = user != null ? user.getViewModel().getGoals() :
                new ArrayList<Goal>();

        // Displaying the generating GoalTodo from the goals by intervals
        ArrayList<Goal> dailyGoals   = new ArrayList<>();
        ArrayList<Goal> weeklyGoals  = new ArrayList<>();
        ArrayList<Goal> monthlyGoals = new ArrayList<>();
        ArrayList<Goal> yearlyGoals  = new ArrayList<>();
        for(Goal goal : goals) {
            switch(goal.getInterval()) {
                case DAY:
                    dailyGoals.add(goal);
                    break;
                case WEEK:
                    weeklyGoals.add(goal);
                    break;
                case MONTH:
                    monthlyGoals.add(goal);
                    break;
                case YEAR:
                    yearlyGoals.add(goal);
                    break;
            }
        }

        // Goals feeds setup
        goalsTodayRecyclerView = (RecyclerView) v.findViewById(R.id.goals_today_rv);
        Utils.setupGoalsFeed(v, goalsTodayRecyclerView, dailyGoals);
        goalsWeekRecyclerView = (RecyclerView) v.findViewById(R.id.goals_week_rv);
        Utils.setupGoalsFeed(v, goalsWeekRecyclerView, weeklyGoals);
        goalsMonthRecyclerView = (RecyclerView) v.findViewById(R.id.goals_month_rv);
        Utils.setupGoalsFeed(v, goalsMonthRecyclerView, monthlyGoals);
        goalsYearRecyclerView = (RecyclerView) v.findViewById(R.id.goals_year_rv);
        Utils.setupGoalsFeed(v, goalsYearRecyclerView, yearlyGoals);
        return v;
    }

}
