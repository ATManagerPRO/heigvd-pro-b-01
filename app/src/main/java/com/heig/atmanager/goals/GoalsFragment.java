package com.heig.atmanager.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heig.atmanager.Interval;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;

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
        calendar.add(Calendar.MONTH, 3);
        Date dueDateGoal4 = calendar.getTime();


        Goal daily_goal1 = new Goal("SQUATS", 20, 1, Interval.DAY, dueDateGoal1);
        Goal daily_goal2 = new Goal("FRUITS", 5, 1,Interval.DAY, dueDateGoal2);
        Goal weekly_goal3 = new Goal("KMS", 4, 1,Interval.WEEK, dueDateGoal3);
        Goal monthly_goal4 = new Goal("GIT PUSH", 4, 1,Interval.MONTH, dueDateGoal4);
        goals.add(daily_goal1);
        goals.add(daily_goal2);
        goals.add(weekly_goal3);
        goals.add(monthly_goal4);
        // --------- END TEMP ---------

        // Calendar instances (for readability)
        Date today = Calendar.getInstance().getTime();

        // Displaying the generating GoalTodo from the goals by intervals
        ArrayList<GoalTodo> todayGoals = new ArrayList<>();
        ArrayList<GoalTodo> weekGoals  = new ArrayList<>();
        ArrayList<GoalTodo> monthGoals = new ArrayList<>();
        for(Goal goal : goals) {
            switch(goal.getInterval()) {
                case DAY:
                    todayGoals.add(goal.getGoalTodos().get(0));
                    break;
                case WEEK:
                    weekGoals.add(goal.getGoalTodos().get(0));
                    break;
                case MONTH:
                    monthGoals.add(goal.getGoalTodos().get(0));
                    break;
            }
        }

        // Goals feeds setup
        goalsTodayRecyclerView = (RecyclerView) v.findViewById(R.id.goals_today_rv);
        Utils.setupGoalsFeed(v, goalsTodayRecyclerView, todayGoals);

        goalsWeekRecyclerView = (RecyclerView) v.findViewById(R.id.goals_week_rv);
        Utils.setupGoalsFeed(v, goalsWeekRecyclerView, weekGoals);

        goalsMonthRecyclerView = (RecyclerView) v.findViewById(R.id.goals_month_rv);
        Utils.setupGoalsFeed(v, goalsMonthRecyclerView, monthGoals);
        return v;
    }

}
